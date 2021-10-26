package com.vismay.makeanote.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vismay.makeanote.data.repository.NoteRepository
import com.vismay.makeanote.ui.base.BaseFragment
import com.vismay.makeanote.ui.shownote.ShowNotesViewModel
import com.vismay.makeanote.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)

    @Provides
    fun provideShowNotesViewModel(
        noteRepository: NoteRepository
    ): ShowNotesViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(ShowNotesViewModel::class) {
            ShowNotesViewModel(noteRepository)
            //this lambda creates and return SplashViewModel
        }).get(ShowNotesViewModel::class.java)
}