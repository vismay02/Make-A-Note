package com.vismay.makeanote.ui.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityMainBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.ui.createupdatenote.CreateUpdateNoteActivity
import com.vismay.makeanote.ui.oauth.LoginActivity
import com.vismay.makeanote.utils.Constants
import com.vismay.makeanote.utils.WrapContentLinearLayoutManager
import com.vismay.makeanote.utils.extensions.ActivityExtension.hideKeyboard
import com.vismay.makeanote.utils.extensions.ActivityExtension.launchActivity
import com.vismay.makeanote.utils.extensions.ActivityExtension.showAlertDialog
import com.vismay.makeanote.utils.extensions.ViewExtensions.clicks
import com.vismay.makeanote.utils.extensions.ViewExtensions.onDone
import com.vismay.makeanote.utils.extensions.ViewExtensions.visibleGone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val position = it.data?.getIntExtra(Constants.KEY_NOTE_POSITION, -1)
                val note = it.data?.getParcelableExtra<NoteEntity?>(Constants.KEY_NOTE_BUNDLE)
                position?.let {
                    if (position != -1) {
                        note?.run {
                            viewModel.fetchUpdatedNote(id, position)
                        }
                    } else {
                        viewModel.fetchNewNote(position)
                    }
                }
            }
        }

    private val notesAdapter by lazy {
        NotesAdapter(this, mutableListOf()) { noteClick ->
            hideKeyboard()
            mViewBinding.editTextSearch.clearFocus()
            if (noteClick.second) {
                showAlertDialog(R.layout.dialog_alert) {
                    viewModel.deleteNote(noteClick.first, noteClick.third)
                }
            } else {
                showNoteInDetail(noteClick.first, noteClick.third)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(mViewBinding.includeToolbarMain.toolbar)
        fetchFirebaseData()
        setupListeners()
        setupSearch()
        addObservers()
        mViewBinding.notesRecycler.layoutManager =
            WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mViewBinding.notesRecycler.adapter = notesAdapter

        viewModel.getAllNotes()
    }

    private fun fetchFirebaseData() {
        val database = Firebase.database.reference
        val notesRef = database.child("notes")
        notesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (noteSnapshot in dataSnapshot.children) {
                    val note = noteSnapshot.getValue(NoteEntity::class.java)
                    Log.d("TAG", "note: $note")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG", databaseError.message)
            }
        })

    }

    private fun addObservers() {
        viewModel.getNotes.observe(this) { notes ->
            if (notes.isNotEmpty()) {
                notesAdapter.updateAdapter(notes)
                showNotes()
            }
        }
        viewModel.searchResults.observe(this) {
            if (it.isNullOrEmpty()) {
                mViewBinding.notesRecycler.visibleGone(isGone = true)
                mViewBinding.textViewNoResult.visibleGone(isGone = false)
            } else {
                mViewBinding.textViewNoResult.visibleGone(isGone = true)
                mViewBinding.notesRecycler.visibleGone(isGone = false)
                notesAdapter.updateAdapter(it)
            }
        }
        viewModel.getNewNote.observe(this) { dataPair ->
            notesAdapter.addUpdateNote(dataPair.first, dataPair.second) {
                showNotes()
            }
        }
        viewModel.getUpdatedNote.observe(this) { dataPair ->
            notesAdapter.addUpdateNote(dataPair.first, dataPair.second) {
            }
        }
        viewModel.deleteNote.observe(this) { deletedData ->
            notesAdapter.deleteNote(deletedData.first, deletedData.second) {
                mViewBinding.groupNoteList.visibleGone(isGone = true)
                mViewBinding.groupNoNote.visibleGone(isGone = false)
            }
        }
    }

    private fun showNotes() {
        mViewBinding.groupNoNote.visibleGone(isGone = true)
        mViewBinding.groupNoteList.visibleGone(isGone = false)
    }

    private fun setupListeners() {
        mViewBinding.fab.clicks()
            .onEach { showNoteInDetail() }
            .launchIn(lifecycleScope)

        mViewBinding.editTextSearch.onDone {
            hideKeyboard()
            mViewBinding.editTextSearch.clearFocus()
        }
    }

    private fun setupSearch() {
        mViewBinding.editTextSearch.addTextChangedListener { query ->
            viewModel.searchWithScore(query)
        }
    }

    private fun showNoteInDetail(note: NoteEntity? = null, position: Int = -1) {
        getResult.launch(Intent(this, CreateUpdateNoteActivity::class.java).apply {
            putExtra(Constants.KEY_NOTE_BUNDLE, note)
            putExtra(Constants.KEY_NOTE_POSITION, position)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.backup_notes -> {
                showSignInActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSignInActivity() {
        launchActivity<LoginActivity>()
    }

    override fun getBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainActivityViewModel by viewModels()

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}