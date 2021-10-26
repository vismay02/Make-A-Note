package com.vismay.makeanote.ui.addnote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.data.repository.NoteRepository
import com.vismay.makeanote.ui.base.BaseViewModel
import com.vismay.makeanote.utils.extensions.DateExtensions.getDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNoteViewModel(private val noteRepository: NoteRepository) : BaseViewModel() {

    val addNote = MutableLiveData<Any>()
    val titleField: MutableLiveData<String> = MutableLiveData()
    val contentField: MutableLiveData<String> = MutableLiveData()

    override fun onCreate() {
    }

    fun onTitleChange(title: String) = titleField.postValue(title)

    fun onContentChange(content: String) = contentField.postValue(content)


    fun addNote() {
        val title = titleField.value
        val content = contentField.value

        viewModelScope.launch(Dispatchers.Main) {

            val result = withContext(Dispatchers.IO) {
                noteRepository.getNotes()
            }
            val id = result.size + 1

            val note = NoteEntity(
                id = id,
                title = title,
                description = content,
                color = "#FFFFFF",
                date = getDate()
            )
            viewModelScope.launch(Dispatchers.Main) {

                notifyUser(withContext(Dispatchers.IO) {
                    noteRepository.saveNote(note)
                })
            }
        }
    }

    private fun notifyUser(result: Unit) {
        addNote.postValue(result)
    }
}