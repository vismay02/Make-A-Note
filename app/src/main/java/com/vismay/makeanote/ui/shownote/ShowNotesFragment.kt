package com.vismay.makeanote.ui.shownote

import android.view.View
import androidx.lifecycle.Observer
import com.vismay.makeanote.R
import com.vismay.makeanote.di.component.FragmentComponent
import com.vismay.makeanote.ui.base.BaseFragment

class ShowNotesFragment : BaseFragment<ShowNotesViewModel>() {

    companion object {
        fun newInstance() = ShowNotesFragment()
    }


    override fun provideLayoutId(): Int = R.layout.show_notes_fragment

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupView(view: View) {
        viewModel.notesList.observe(this, {

        })
    }

}