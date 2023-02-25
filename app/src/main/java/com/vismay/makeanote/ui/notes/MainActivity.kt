package com.vismay.makeanote.ui.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityMainBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.ui.createupdatenote.CreateUpdateNoteActivity
import com.vismay.makeanote.utils.Constants
import com.vismay.makeanote.utils.WrapContentLinearLayoutManager
import com.vismay.makeanote.utils.extensions.ActivityExtension.hideKeyboard
import com.vismay.makeanote.utils.extensions.ActivityExtension.showAlertDialog
import com.vismay.makeanote.utils.extensions.ViewExtensions.onDone
import com.vismay.makeanote.utils.extensions.ViewExtensions.visibleGone
import dagger.hilt.android.AndroidEntryPoint

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
        NotesAdapter(mutableListOf()) { noteClick ->
            hideKeyboard()
            mViewBinding.editTextSearchBoxNoteList.clearFocus()
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
        setupListeners()
        setupSearch()
        addObservers()
        mViewBinding.notesRecycler.layoutManager =
            WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mViewBinding.notesRecycler.adapter = notesAdapter

        viewModel.getAllNotes()
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
        mViewBinding.fab.setOnClickListener {
            showNoteInDetail()
        }

        mViewBinding.editTextSearchBoxNoteList.onDone {
            hideKeyboard()
            mViewBinding.editTextSearchBoxNoteList.clearFocus()
        }
    }

    private fun setupSearch() {
        mViewBinding.editTextSearchBoxNoteList.addTextChangedListener { query ->
            Log.d("TAG", "query: $query")
            viewModel.searchWithScore(query)
        }
    }

    private fun showNoteInDetail(note: NoteEntity? = null, position: Int = -1) {
        getResult.launch(Intent(this, CreateUpdateNoteActivity::class.java).apply {
            putExtra(Constants.KEY_NOTE_BUNDLE, note)
            putExtra(Constants.KEY_NOTE_POSITION, position)
        })
    }

    override fun getBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainActivityViewModel by viewModels()
}