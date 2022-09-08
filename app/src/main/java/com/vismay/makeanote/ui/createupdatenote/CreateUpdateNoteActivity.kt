package com.vismay.makeanote.ui.createupdatenote

import android.os.Bundle
import androidx.activity.viewModels
import com.vismay.makeanote.databinding.ActivityCreateUpdateNoteBinding
import com.vismay.makeanote.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUpdateNoteActivity :
    BaseActivity<ActivityCreateUpdateNoteBinding, CreateUpdateViewModel>() {

    override val viewModel: CreateUpdateViewModel by viewModels()

    override fun getBinding(): ActivityCreateUpdateNoteBinding =
        ActivityCreateUpdateNoteBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}