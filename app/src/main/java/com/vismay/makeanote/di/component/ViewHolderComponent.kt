package com.vismay.makeanote.di.component

import com.vismay.makeanote.di.ViewHolderScope
import com.vismay.makeanote.di.module.ViewHolderModule
import com.vismay.makeanote.ui.shownotes.note.NoteViewHolder
import dagger.Component

@ViewHolderScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {
    fun inject(viewHolder: NoteViewHolder)
}