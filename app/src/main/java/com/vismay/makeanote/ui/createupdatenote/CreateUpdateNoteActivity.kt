package com.vismay.makeanote.ui.createupdatenote

import android.os.Bundle
import androidx.activity.viewModels
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityCreateUpdateNoteBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.utils.Constants.KEY_NOTE_BUNDLE
import com.vismay.makeanote.utils.Constants.SPECIAL_CHAR
import com.vismay.makeanote.utils.extensions.DateExtensions.getCurrentDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUpdateNoteActivity :
    BaseActivity<ActivityCreateUpdateNoteBinding, CreateUpdateViewModel>() {

    override val viewModel: CreateUpdateViewModel by viewModels()
    private var noteExtra: NoteEntity? = null
    private var noteText: String? = null

    override fun getBinding(): ActivityCreateUpdateNoteBinding =
        ActivityCreateUpdateNoteBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteExtra = intent.getParcelableExtra(KEY_NOTE_BUNDLE) as NoteEntity?

        noteExtra?.let { note ->
            note.note?.let {
                noteText = it
                showNote(it)
            }
        }
    }

    private fun showNote(note: String) {
        val actualNote = note.replace(SPECIAL_CHAR, "\n")
        mViewBinding.edittextNote.setText(actualNote)
    }

    override fun onBackPressed() {
        val updatedString =
            mViewBinding.edittextNote.text.toString().trim().replace("\n", SPECIAL_CHAR)
        if (updatedString.isEmpty() || noteText == updatedString) {
            finish()
            return
        }
        noteExtra?.let { note ->
            viewModel.update(note.id, updatedString, getCurrentDate())
        } ?: viewModel.save(updatedString, getCurrentDate())

        finish()
    }
}