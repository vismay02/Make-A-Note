package com.vismay.makeanote.ui.shownotes.note

import android.view.View
import android.view.ViewGroup
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.di.component.ViewHolderComponent
import com.vismay.makeanote.ui.base.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_view_note.view.*

class NoteViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<NoteEntity, NoteViewModel>(R.layout.item_view_note, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) =
        viewHolderComponent.inject(this)

    override fun setupObservers() {

        viewModel.data.observe(this, {
            itemView.titleTextView.text = it.title
            itemView.contentTextView.text = it.description
        })
    }

    override fun setupView(view: View) {
    }
}