package com.vismay.makeanote.di.module

import androidx.lifecycle.LifecycleRegistry
import com.vismay.makeanote.di.ViewHolderScope
import com.vismay.makeanote.ui.base.BaseItemViewHolder
import dagger.Module
import dagger.Provides

@Module
class ViewHolderModule(private val viewHolder: BaseItemViewHolder<*, *>) {

    @Provides
    @ViewHolderScope
    fun provideLifecycleRegistry(): LifecycleRegistry =
        LifecycleRegistry(viewHolder)


}