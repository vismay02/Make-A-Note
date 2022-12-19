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

const val IDENTITY_CHAR = "(%@)"

@AndroidEntryPoint
class CreateUpdateNoteActivity :
    BaseActivity<ActivityCreateUpdateNoteBinding, CreateUpdateViewModel>() {

    private var pressedTime = 0L
    override val viewModel: CreateUpdateViewModel by viewModels()
    private var noteExtra: NoteEntity? = null
    private var noteString: StringBuilder = StringBuilder("")
    private var isEnterPressed = false


    override fun getBinding(): ActivityCreateUpdateNoteBinding =
        ActivityCreateUpdateNoteBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteExtra = intent.getParcelableExtra(KEY_NOTE_BUNDLE) as NoteEntity?

        noteExtra?.let { note ->
            note.note?.let {
                noteString.append(it)
                showNote(it)
            }
        }
        addTextWatcher()
        mViewBinding.edittextNote.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                Log.d("TAG", "Enter pressed!")
                isEnterPressed = true
                noteString.append(
                    "${
                        mViewBinding.edittextNote.text.toString().trim()
                    } $IDENTITY_CHAR "
                )
                Log.d("TAG", "noteString: $noteString")
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
                /*if (noteString.contains(IDENTITY_CHAR) && isEnterPressed) {
                    isEnterPressed = false
//                    Log.d("TAG", "contains enter: $noteString")
                    noteString.clear()
                    noteString.append("${s.toString()} $IDENTITY_CHAR")
                } else {
                    noteString.clear()
                    noteString.append(s.toString())
                }*/

//                Log.d("TAG", "onTextChanged: $noteString")
            }

            override fun afterTextChanged(editable: Editable?) {
//                Log.d("TAG", "afterTextChanged: ${editable.toString()}")
            }
        })
    }

    override fun onBackPressed() {
        Log.d("TAG", "onBackPressed noteString: $noteString")
        if (noteString.contains(IDENTITY_CHAR))
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()

            noteExtra?.let { note ->
                viewModel.update(note.id, noteString.toString())
            } ?: viewModel.save(noteString.toString())

            finish()
        } else {
            Utils.showShortToast(this, getString(R.string.save_and_back))
        }
        pressedTime = System.currentTimeMillis()
    }
}