package com.vismay.makeanote.ui.notes

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vismay.makeanote.data.local.db.calculateScore
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.data.repository.NoteRepository
import com.vismay.makeanote.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : BaseViewModel() {

    private val _searchResults = MutableLiveData<List<NoteEntity>>()
    val searchResults = _searchResults as LiveData<List<NoteEntity>>

    /*Helps in hiding mutable types*/
    private val _getNotes = MutableLiveData<List<NoteEntity>>()
    val getNotes = _getNotes as LiveData<List<NoteEntity>>

    private val _getNewNote = MutableLiveData<Pair<NoteEntity, Int>>()
    val getNewNote = _getNewNote as LiveData<Pair<NoteEntity, Int>>

    fun getAllNotes() {
        viewModelScope.launch {
            _getNotes.postValue(noteRepository.getNotes())
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun fetchNewNote(position: Int) {
        viewModelScope.launch {
            _getNewNote.postValue(Pair(noteRepository.fetchNewNote(), position))
        }
    }

    private fun sanitizeSearchQuery(query: Editable?): String {
        if (query == null) {
            return "";
        }
        val queryWithEscapedQuotes = query.replace(Regex.fromLiteral("\""), "\"\"")
        return "*\"$queryWithEscapedQuotes\"*"
    }

    fun searchWithScore(query: Editable?) {
        // 1
        viewModelScope.launch {
            // 2
            if (query.isNullOrBlank()) {
                // 3
                noteRepository.getNotes().let { _searchResults.postValue(it) }
            } else {
                // 4
                val sanitizedQuery = sanitizeSearchQuery(query)
                noteRepository.searchWithMatchInfo(sanitizedQuery).let { results ->
                    // 5
                    results.sortedByDescending { result -> calculateScore(result.matchInfo) }
                        // 6
                        .map { result -> result.note }
                        // 7
                        .let { _searchResults.postValue(it) }
                }
            }
        }
    }

}