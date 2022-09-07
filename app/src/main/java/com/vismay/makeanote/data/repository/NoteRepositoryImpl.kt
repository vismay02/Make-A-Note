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
}