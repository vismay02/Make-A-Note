package com.vismay.makeanote.ui.shownote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vismay.makeanote.ui.base.BaseViewModel
import com.vismay.makeanote.utils.Event

class NotesActivityViewsModel : BaseViewModel() {

    var launchAddNotes: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    override fun onCreate() {
    }

    fun addNote() {
        launchAddNotes.postValue(Event(emptyMap()))
    }
}