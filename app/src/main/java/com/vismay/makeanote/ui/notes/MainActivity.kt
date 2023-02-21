package com.vismay.makeanote.ui.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityMainBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.ui.createupdatenote.CreateUpdateNoteActivity
import com.vismay.makeanote.utils.Constants
import com.vismay.makeanote.utils.extensions.ActivityExtension.hideKeyboard
import com.vismay.makeanote.utils.extensions.ActivityExtension.showAlertDialog
import com.vismay.makeanote.utils.extensions.ViewExtensions.onDone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val position = it.data?.getIntExtra(Constants.KEY_NOTE_POSITION, -1)
                position?.let {
                    viewModel.fetchNewNote(position)
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
        mViewBinding.notesRecycler.adapter = notesAdapter

        viewModel.getNotes.observe(this) { notes ->
            notesAdapter.updateAdapter(notes)
        }
        viewModel.searchResults.observe(this) {
        }
        viewModel.getNewNote.observe(this) { dataPair ->
            notesAdapter.addNote(dataPair.first, dataPair.second)
        }
        viewModel.deleteNote.observe(this) { deletedData ->
            notesAdapter.deleteNote(deletedData.first, deletedData.second)
        }
        viewModel.getAllNotes()
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