package com.vismay.makeanote.ui.shownotes

import androidx.lifecycle.MutableLiveData
import com.vismay.makeanote.ui.base.BaseViewModel
import com.vismay.makeanote.utils.Event

class NotesActivityViewModel : BaseViewModel() {

    var launchAddNotes: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    override fun onCreate() {
    }

    fun addNote() {
        launchAddNotes.postValue(Event(emptyMap()))
    }
}