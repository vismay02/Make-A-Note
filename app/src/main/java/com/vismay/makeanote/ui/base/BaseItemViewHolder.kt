package com.vismay.makeanote.ui.base

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.vismay.makeanote.MakeANoteApplication
import com.vismay.makeanote.di.component.DaggerViewHolderComponent
import com.vismay.makeanote.di.component.ViewHolderComponent
import com.vismay.makeanote.di.module.ViewHolderModule
import com.vismay.makeanote.ui.shownotes.ShowNotesFragment
import javax.inject.Inject

abstract class BaseItemViewHolder<T : Any, VM : BaseItemViewModel<T>>(
    @LayoutRes layoutId: Int,
    parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)),
    LifecycleOwner {

    init {
        Log.d(ShowNotesFragment.TAG, "init BaseItemViewHolder:")
        onCreate()
    }

    @Inject
    lateinit var viewModel: VM

    @Inject
    lateinit var lifeCycleRegistry: LifecycleRegistry

    override fun getLifecycle(): Lifecycle = lifeCycleRegistry

    //    As this function can be overridden by subclasses to write their own logic, it needs to be open.
    open fun bind(data: T) {
        viewModel.updateData(data)
    }

    protected fun onCreate() {
        injectDependencies(buildViewHolderComponent())
        lifeCycleRegistry.currentState = Lifecycle.State.INITIALIZED
        lifeCycleRegistry.currentState = Lifecycle.State.CREATED
        setupObservers()
        setupView(itemView)
    }

    fun onStart() {
        lifeCycleRegistry.currentState = Lifecycle.State.STARTED
        lifeCycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    fun onStop() {
        lifeCycleRegistry.currentState = Lifecycle.State.STARTED
        lifeCycleRegistry.currentState = Lifecycle.State.CREATED
    }

    fun onDestroy() {
        lifeCycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    private fun buildViewHolderComponent() =
        DaggerViewHolderComponent
            .builder()
            .applicationComponent((itemView.context.applicationContext as MakeANoteApplication).applicationComponent)
            .viewHolderModule(ViewHolderModule(this))
            .build()

    protected abstract fun setupObservers()

    protected abstract fun injectDependencies(viewHolderComponent: ViewHolderComponent)

    protected abstract fun setupView(view: View)
}