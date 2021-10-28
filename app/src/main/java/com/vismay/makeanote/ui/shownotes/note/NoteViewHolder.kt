package com.vismay.makeanote.ui.shownotes.note

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.di.component.ViewHolderComponent
import com.vismay.makeanote.ui.base.BaseItemViewHolder
import com.vismay.makeanote.ui.shownotes.ShowNotesFragment

class NoteViewHolder(parent: ViewGroup) : BaseItemViewHolder<NoteEntity, NoteViewModel>(R.layout.item_view_note, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) =
        viewHolderComponent.inject(this)

    override fun setupObservers() {
        Log.d(ShowNotesFragment.TAG, "NoteViewHolder setupObservers:")
        viewModel.data.observe(this, Observer {
            Log.d(ShowNotesFragment.TAG, "NoteViewHolder setupObservers: $it")
        })
    }

    override fun setupView(view: View) {
        Log.d(ShowNotesFragment.TAG, "setupView setupObservers:")
    }
}