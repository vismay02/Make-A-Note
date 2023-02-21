package com.vismay.makeanote.data.repository

import com.vismay.makeanote.data.local.db.dao.NoteDao
import com.vismay.makeanote.data.local.db.entity.LaunchWithMatchInfo
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.di.module.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*Injecting Dispatcher will make this class more testable as we can pass a TestDispatcher while writing
*  unit and instrumentation testing.*/
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : NoteRepository {

    /*Suspend functions should be safe to call from the main thread.
    * Moving the execution off the main thread using withContext.
    * Now, any class calling getNotes() doesn't have to bother about being main-safe.*/
    override suspend fun getNotes(): List<NoteEntity> =
        withContext(dispatcher) {
            noteDao.getAllNotes()
        }

    override suspend fun saveNote(note: NoteEntity) {
        withContext(dispatcher) {
            noteDao.insertNote(note = note)
        }
    }

    override suspend fun updateNote(note: NoteEntity) {
        withContext(dispatcher) {
            noteDao.updateNote(note = note)
        }
    }

    override suspend fun deleteNote(note: NoteEntity) {
        withContext(dispatcher) {
            noteDao.deleteNote(note = note)
        }
    }

    override suspend fun search(query: String): List<NoteEntity> =
        withContext(dispatcher) {
            noteDao.search(query)
        }


    override suspend fun searchWithMatchInfo(query: String): List<LaunchWithMatchInfo> =
        withContext(dispatcher) {
            noteDao.searchWithMatchInfo(query)
        }

    override suspend fun fetchNewNote(): NoteEntity =
        withContext(dispatcher) {
            noteDao.fetchNewNote()
        }
}