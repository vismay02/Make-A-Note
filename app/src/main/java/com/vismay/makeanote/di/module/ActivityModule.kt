package com.vismay.makeanote.di.module

import androidx.lifecycle.ViewModelProviders
import com.vismay.makeanote.data.repository.NoteRepository
import com.vismay.makeanote.ui.addnote.AddNoteViewModel
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.ui.shownotes.NotesActivityViewModel
import com.vismay.makeanote.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun providesNotesActivityViewModel(): NotesActivityViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(NotesActivityViewModel::class) {
            NotesActivityViewModel()
            //this lambda creates and return SplashViewModel
        }).get(NotesActivityViewModel::class.java)

    @Provides
    fun provideAddNoteViewModel(
        noteRepository: NoteRepository
    ): AddNoteViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(AddNoteViewModel::class) {
            AddNoteViewModel(noteRepository)
            //this lambda creates and return SplashViewModel
        }).get(AddNoteViewModel::class.java)
}