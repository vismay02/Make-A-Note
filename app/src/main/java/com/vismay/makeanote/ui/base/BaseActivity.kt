package com.vismay.makeanote.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.vismay.makeanote.MakeANoteApplication
import com.vismay.makeanote.di.component.ActivityComponent
import com.vismay.makeanote.di.component.DaggerActivityComponent
import com.vismay.makeanote.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        setupObservers()
        setUpView(savedInstanceState)
        viewModel.onCreate()
    }

    private fun buildActivityComponent() =
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MakeANoteApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    protected abstract fun setUpView(savedInstanceState: Bundle?)

    protected abstract fun setupObservers()
}