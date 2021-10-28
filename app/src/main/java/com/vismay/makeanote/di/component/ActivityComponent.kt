package com.vismay.makeanote.di.component

import com.vismay.makeanote.di.ActivityScope
import com.vismay.makeanote.di.module.ActivityModule
import com.vismay.makeanote.ui.addnote.AddNoteActivity
import com.vismay.makeanote.ui.shownotes.NotesActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {
    fun inject(activity: NotesActivity)
    fun inject(activity: AddNoteActivity)
}