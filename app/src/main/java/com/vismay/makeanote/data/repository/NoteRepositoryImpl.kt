package com.vismay.makeanote.data.repository

import com.vismay.makeanote.data.local.db.dao.NoteDao
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {

    override suspend fun getNotes(): List<NoteEntity> =
        withContext(Dispatchers.IO) {
            noteDao.getAllNotes()
        }

    override suspend fun saveNote(note: NoteEntity) {
        withContext(Dispatchers.IO) {
            noteDao.insertNote(note = note)
        }
    }

    override suspend fun updateNote(note: NoteEntity) {
        withContext(Dispatchers.IO) {
            noteDao.updateNote(note = note)
        }
    }

    override suspend fun deleteNote(note: NoteEntity) {
        withContext(Dispatchers.IO){
            noteDao.deleteNote(note = note)
        }
    }


}