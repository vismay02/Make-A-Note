package com.vismay.makeanote.ui.shownote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.vismay.makeanote.R
import com.vismay.makeanote.di.component.ActivityComponent
import com.vismay.makeanote.ui.addnote.AddNoteActivity
import com.vismay.makeanote.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class NotesActivity : BaseActivity<NotesActivityViewsModel>() {

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun setUpView(savedInstanceState: Bundle?) {
        buttonSave?.setOnClickListener {
            viewModel.addNote()
        }
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupObservers() {
        viewModel.launchAddNotes.observe(this, {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, AddNoteActivity::class.java))
                finish()
            }
        })
    }


}