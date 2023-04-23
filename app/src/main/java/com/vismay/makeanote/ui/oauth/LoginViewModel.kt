package com.vismay.makeanote.ui.oauth

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
class LoginViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : BaseViewModel() {
    private val _getNotes = MutableLiveData<List<NoteEntity>>()
    val getNotes = _getNotes as LiveData<List<NoteEntity>>

    fun getAllNotes() {
        viewModelScope.launch {
            _getNotes.postValue(noteRepository.getNotes())
        }
    }
}