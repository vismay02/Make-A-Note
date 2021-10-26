package com.vismay.makeanote.ui.addnote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.vismay.makeanote.R
import com.vismay.makeanote.di.component.ActivityComponent
import com.vismay.makeanote.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : BaseActivity<AddNoteViewModel>() {

    override fun provideLayoutId(): Int = R.layout.activity_add_note

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setUpView(savedInstanceState: Bundle?) {
        editTextTitle.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onTitleChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        editTextContent.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onContentChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        buttonCreateNote.setOnClickListener { viewModel.addNote() }
    }

    override fun setupObservers() {
        viewModel.titleField.observe(this, {
            if (editTextTitle.text.toString() != it) editTextTitle.setText(it)
        })

        viewModel.contentField.observe(this, {
            if (editTextContent.text.toString() != it) editTextContent.setText(it)
        })

        viewModel.addNote.observe(this, {

        })
    }

}