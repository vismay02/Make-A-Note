package com.vismay.makeanote.ui.createupdatenote

import android.os.Bundle
import androidx.activity.viewModels
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityCreateUpdateNoteBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.utils.Constants.KEY_NOTE_BUNDLE
import com.vismay.makeanote.utils.Constants.SPECIAL_CHAR
import com.vismay.makeanote.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUpdateNoteActivity :
    BaseActivity<ActivityCreateUpdateNoteBinding, CreateUpdateViewModel>() {

    private var pressedTime = 0L
    override val viewModel: CreateUpdateViewModel by viewModels()
    private var noteExtra: NoteEntity? = null

    override fun getBinding(): ActivityCreateUpdateNoteBinding =
        ActivityCreateUpdateNoteBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteExtra = intent.getParcelableExtra(KEY_NOTE_BUNDLE) as NoteEntity?

        noteExtra?.let { note ->
            note.note?.let {
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

        if (updatedString.isNullOrEmpty()) {
            finish()
            return
        }

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()

            noteExtra?.let { note ->
                viewModel.update(note.id, updatedString)
            } ?: viewModel.save(updatedString)

            finish()
        } else {
            Utils.showShortToast(this, getString(R.string.save_and_back))
        }
        pressedTime = System.currentTimeMillis()
    }
}