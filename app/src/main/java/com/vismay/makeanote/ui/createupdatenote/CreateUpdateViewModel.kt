package com.vismay.makeanote.ui.createupdatenote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.data.repository.NoteRepository
import com.vismay.makeanote.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateUpdateViewModel @Inject constructor(
    private var noteRepository: NoteRepository
) : BaseViewModel() {

    fun save(title: String, description: String) {
        viewModelScope.launch {
            noteRepository.saveNote(
                note =
                NoteEntity(
                    title = title, description = description, date = ""
                )
            )
        }
    }
}