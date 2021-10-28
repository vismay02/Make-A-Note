package com.vismay.makeanote.di.component

import com.vismay.makeanote.di.FragmentScope
import com.vismay.makeanote.di.module.FragmentModule
import com.vismay.makeanote.ui.shownotes.ShowNotesFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {
    fun inject(fragment: ShowNotesFragment)
}