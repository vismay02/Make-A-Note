package com.vismay.makeanote.ui.shownotes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vismay.makeanote.R
import com.vismay.makeanote.di.component.FragmentComponent
import com.vismay.makeanote.ui.base.BaseFragment
import com.vismay.makeanote.ui.shownotes.note.NoteAdapter
import kotlinx.android.synthetic.main.show_notes_fragment.*
import javax.inject.Inject

class ShowNotesFragment : BaseFragment<ShowNotesViewModel>() {

    companion object {
        const val TAG = "ShowNotesFragment"
        fun newInstance(): ShowNotesFragment {
            val args = Bundle()
            val fragment = ShowNotesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var noteAdapter: NoteAdapter

    override fun provideLayoutId(): Int = R.layout.show_notes_fragment

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupView(view: View) {
        notesRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = noteAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    Log.d(TAG, "onScrolled: ")
                }
            })

        }
    }

    override fun setupObserver() {
        viewModel.notesList.observe(this, {
            noteAdapter.appendData(it)
        })
    }

}