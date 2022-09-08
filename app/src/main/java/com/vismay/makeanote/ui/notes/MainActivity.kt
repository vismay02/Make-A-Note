package com.vismay.makeanote.ui.notes

import android.os.Bundle
import androidx.activity.viewModels
import com.vismay.makeanote.databinding.ActivityMainBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.ui.createupdatenote.CreateUpdateNoteActivity
import com.vismay.makeanote.utils.extensions.ActivityExtension.launchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override fun getBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding.fab.setOnClickListener {
            launchActivity<CreateUpdateNoteActivity>()
        }

        viewModel.getAllNotes()

        viewModel.getNotes.observe(this) { notes ->
            //TODO: Add notes to recycler view
        }
    }


}