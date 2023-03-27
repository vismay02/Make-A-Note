package com.vismay.makeanote.ui.createupdatenote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityCreateUpdateNoteBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.utils.Constants.KEY_NOTE_BUNDLE
import com.vismay.makeanote.utils.Constants.KEY_NOTE_POSITION
import com.vismay.makeanote.utils.Constants.SPECIAL_CHAR
import com.vismay.makeanote.utils.Utils
import com.vismay.makeanote.utils.Utils.randomColorGenerator
import com.vismay.makeanote.utils.extensions.DateExtensions.getCurrentDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUpdateNoteActivity :
    BaseActivity<ActivityCreateUpdateNoteBinding, CreateUpdateViewModel>() {

    override val viewModel: CreateUpdateViewModel by viewModels()
    private var noteExtra: NoteEntity? = null
    private var noteText: String? = null
    private var posiiton: Int = -1
    private var pressedTime = 0L

    override fun getBinding(): ActivityCreateUpdateNoteBinding =
        ActivityCreateUpdateNoteBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteExtra = intent.getParcelableExtra(KEY_NOTE_BUNDLE) as NoteEntity?
        posiiton = intent.getIntExtra(KEY_NOTE_POSITION, -1)

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

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            noteExtra?.let { note ->
                viewModel.update(note.id, updatedString, getCurrentDate(), note.color)
            } ?: viewModel.save(updatedString, getCurrentDate(), randomColorGenerator())

            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(KEY_NOTE_BUNDLE, noteExtra)
                putExtra(KEY_NOTE_POSITION, posiiton)
            })
            finish()
        } else {
            Utils.showShortToast(this, getString(R.string.save_and_back))

        }
        pressedTime = System.currentTimeMillis()
    }
}