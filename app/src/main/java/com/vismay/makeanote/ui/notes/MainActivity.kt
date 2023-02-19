package com.vismay.makeanote.ui.notes

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityMainBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.ui.createupdatenote.CreateUpdateNoteActivity
import com.vismay.makeanote.utils.Constants.KEY_NOTE_BUNDLE
import com.vismay.makeanote.utils.extensions.ActivityExtension.hideKeyboard
import com.vismay.makeanote.utils.extensions.ActivityExtension.launchActivity
import com.vismay.makeanote.utils.extensions.ActivityExtension.showAlertDialog
import com.vismay.makeanote.utils.extensions.ViewExtensions.onDone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    private val notesAdapter by lazy {
        NotesAdapter(mutableListOf()) { noteClick ->
            hideKeyboard()
            mViewBinding.editTextSearchBoxNoteList.clearFocus()
            mViewBinding.notesRecycler.recycledViewPool.clear()
            if (noteClick.second) {
                showAlertDialog(R.layout.dialog_alert) {
                    viewModel.deleteNote(noteClick.first)
                }
            } else {
                showNoteInDetail(noteClick.first)
            }
        }
    }

    override fun getBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainActivityViewModel by viewModels()

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
    }

    override fun onResume() {
        super.onResume()
        /*FIXME: Not an efficient way to get all notes everytime.*/
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

    private fun showNoteInDetail(note: NoteEntity? = null) {
        launchActivity<CreateUpdateNoteActivity> {
            putExtra(KEY_NOTE_BUNDLE, note)
        }
    }
}