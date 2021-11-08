package com.vismay.makeanote.ui.shownotes

import android.content.Intent
import android.os.Bundle
import com.vismay.makeanote.R
import com.vismay.makeanote.di.component.ActivityComponent
import com.vismay.makeanote.ui.addnote.AddNoteActivity
import com.vismay.makeanote.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class NotesActivity : BaseActivity<NotesActivityViewModel>() {

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun setUpView(savedInstanceState: Bundle?) {
        buttonSave?.setOnClickListener {
            viewModel.addNote()
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment =
            supportFragmentManager.findFragmentByTag(ShowNotesFragment.TAG) as ShowNotesFragment?

        if (fragment == null) {
            fragment = ShowNotesFragment.newInstance()
            fragmentTransaction.disallowAddToBackStack()
            fragmentTransaction.add(R.id.containerFragment, fragment, ShowNotesFragment.TAG)
            fragmentTransaction.commit()
        }
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupObservers() {
        viewModel.launchAddNotes.observe(this, {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, AddNoteActivity::class.java))
            }
        })
    }


}