package com.vismay.makeanote.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.vismay.makeanote.MakeANoteApplication
import com.vismay.makeanote.di.component.DaggerFragmentComponent
import com.vismay.makeanote.di.component.FragmentComponent
import com.vismay.makeanote.di.module.FragmentModule
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildFragmentComponent())
        super.onCreate(savedInstanceState)
        setupObserver()
        viewModel.onCreate()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(provideLayoutId(), container, false)

    private fun buildFragmentComponent() =
        DaggerFragmentComponent
            .builder()
            .applicationComponent((requireContext().applicationContext as MakeANoteApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()


    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun injectDependencies(fragmentComponent: FragmentComponent)

    protected abstract fun setupView(view: View)

    protected abstract fun setupObserver()
}