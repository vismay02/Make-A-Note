package com.vismay.makeanote.ui.shownotes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.data.repository.NoteRepository
import com.vismay.makeanote.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowNotesViewModel(private val noteRepository: NoteRepository) : BaseViewModel() {

     val notesList = MutableLiveData<List<NoteEntity>>()
    override fun onCreate() {
        Log.d(ShowNotesFragment.TAG,"ShowNotesViewModel onCreate: ")
        getNotes()
    }

    private fun getNotes() {
        Log.d(ShowNotesFragment.TAG,"getNotes: ")
        viewModelScope.launch(Dispatchers.Main) {
            val notes = withContext(Dispatchers.IO) {
                noteRepository.getNotes()
            }
            notesList.postValue(notes)
        }
    }

}