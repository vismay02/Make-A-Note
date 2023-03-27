package com.vismay.makeanote.ui.createupdatenote

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

    fun save(note: String, date: String, color: Int) {
        viewModelScope.launch {
            noteRepository.saveNote(
                note =
                NoteEntity(note = note, color = color, date = date)
            )
        }
    }

    fun update(id: Int, note: String, date: String, color: Int) {
        viewModelScope.launch {
            noteRepository.updateNote(
                note =
                NoteEntity(id = id, note = note, color = color, date = date)
            )
        }
    }
}