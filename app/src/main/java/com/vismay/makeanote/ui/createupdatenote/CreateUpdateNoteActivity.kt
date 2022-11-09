package com.vismay.makeanote.ui.createupdatenote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.activity.viewModels
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityCreateUpdateNoteBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.utils.Constants.KEY_NOTE_BUNDLE
import com.vismay.makeanote.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUpdateNoteActivity :
    BaseActivity<ActivityCreateUpdateNoteBinding, CreateUpdateViewModel>() {

    private var pressedTime = 0L
    override val viewModel: CreateUpdateViewModel by viewModels()
    private var noteExtra: NoteEntity? = null
    private var noteString = ""
    private val builder by lazy {
        StringBuilder()
    }

    override fun getBinding(): ActivityCreateUpdateNoteBinding =
        ActivityCreateUpdateNoteBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteExtra = intent.getParcelableExtra(KEY_NOTE_BUNDLE) as NoteEntity?

        noteExtra?.let { note ->
            note.note?.let {
                noteString = it
                showNote(it)
            }
        }
        addTextWatcher()
        mViewBinding.edittextNote.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                Log.d("TAG", "Enter pressed!")
                noteString.trim()
                noteString = "$noteString %n"
                builder.append(" %n")
                Log.d("TAG", "builder: $builder")

            }
            false
        }
    }

    private fun showNote(note: String) {
        mViewBinding.edittextNote.setText(note)
    }

    private fun addTextWatcher() {
        mViewBinding.edittextNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                builder.clear()
                builder.append(s.toString())
                Log.d("TAG", "onTextChanged: $builder")
            }

            override fun afterTextChanged(editable: Editable?) {


            }
        })
    }

    override fun onBackPressed() {
        Log.d("TAG", "builder: $builder")
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()

            noteExtra?.let { note ->
                viewModel.update(note.id, noteString)
            } ?: viewModel.save(noteString)

            finish()
        } else {
            Utils.showShortToast(this, getString(R.string.save_and_back))
        }
        pressedTime = System.currentTimeMillis()
    }
}