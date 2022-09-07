package com.vismay.makeanote.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var mViewBinding: B

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = getBinding()
        setContentView(mViewBinding.root)
    }

    abstract fun getBinding(): B
}