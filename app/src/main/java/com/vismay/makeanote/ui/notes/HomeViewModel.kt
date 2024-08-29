package com.vismay.makeanote.ui.notes

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vismay.makeanote.data.local.db.calculateScore
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _searchResults = MutableLiveData<List<NoteEntity>>()
    val searchResults = _searchResults as LiveData<List<NoteEntity>>

    /*Helps in hiding mutable types*/
    private val _getNotes = MutableLiveData<List<NoteEntity>>()
    val getNotes = _getNotes as LiveData<List<NoteEntity>>

    private val _getNewNote = MutableLiveData<Pair<NoteEntity, Int>>()
    val getNewNote = _getNewNote as LiveData<Pair<NoteEntity, Int>>

    private val _getUpdatedNote = MutableLiveData<Pair<NoteEntity, Int>>()
    val getUpdatedNote = _getUpdatedNote as LiveData<Pair<NoteEntity, Int>>

    private val _deleteNote = MutableLiveData<Pair<NoteEntity, Int>>()
    val deleteNote = _deleteNote as LiveData<Pair<NoteEntity, Int>>

    fun getAllNotes() {
        viewModelScope.launch {
            _getNotes.postValue(noteRepository.getNotes())
        }
    }

    fun deleteNote(note: NoteEntity, position: Int) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
            _deleteNote.postValue(Pair(note, position))
        }
    }

    fun fetchNewNote(position: Int) {
        viewModelScope.launch {
            _getNewNote.postValue(Pair(noteRepository.fetchNewNote(), position))
        }
    }

    fun fetchUpdatedNote(id: Int, position: Int) {
        viewModelScope.launch {
            _getUpdatedNote.postValue(Pair(noteRepository.fetchUpdatedNote(id), position))
        }
    }

    private fun sanitizeSearchQuery(query: Editable?): String {
        if (query == null) {
            return ""
        }
        val queryWithEscapedQuotes = query.replace(Regex.fromLiteral("\""), "\"\"")
        return "*\"$queryWithEscapedQuotes\"*"
    }

    fun searchWithScore(query: Editable?) {
        viewModelScope.launch {
            if (query.isNullOrBlank()) {
                noteRepository.getNotes().let { _searchResults.postValue(it) }
            } else {
                val sanitizedQuery = sanitizeSearchQuery(query)
                noteRepository.searchWithMatchInfo(sanitizedQuery).let { results ->
                    results.sortedByDescending { result -> calculateScore(result.matchInfo) }
                        .map { result -> result.note }
                        .let { _searchResults.postValue(it) }
                }
            }
        }
    }
}
