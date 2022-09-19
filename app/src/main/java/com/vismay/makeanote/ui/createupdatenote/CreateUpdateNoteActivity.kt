package com.vismay.makeanote.ui.createupdatenote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import com.vismay.makeanote.R
import com.vismay.makeanote.databinding.ActivityCreateUpdateNoteBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUpdateNoteActivity :
    BaseActivity<ActivityCreateUpdateNoteBinding, CreateUpdateViewModel>() {

    private var pressedTime = 0L
    override val viewModel: CreateUpdateViewModel by viewModels()
    private var title: String? = null
    private var description: String? = null

    override fun getBinding(): ActivityCreateUpdateNoteBinding =
        ActivityCreateUpdateNoteBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addTextWatcher()
    }

    private fun addTextWatcher() {
        mViewBinding.edittextNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val lineCount = mViewBinding.edittextNote.lineCount
                if (lineCount == 0) return

                if (lineCount == 1) {
                    title = s.toString().trim()
                } else {
                    description = s.toString().replace(title!!, "").trim()
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(string: Editable?) {
            }

        })
    }

    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            val title = title
            val description = description

            if (title != null && description != null) {
                viewModel.save(title = title, description = description)
            }
            finish()
        } else {
            Utils.showShortToast(this, R.string.save_and_back)
        }
        pressedTime = System.currentTimeMillis()
    }
}