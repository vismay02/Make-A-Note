package com.vismay.makeanote.data.repository

import com.vismay.makeanote.data.local.db.DatabaseService
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val databaseService: DatabaseService) {

    suspend fun saveNote(note: NoteEntity) {
        databaseService.NoteDao().insertNote(note)
    }

    suspend fun deleteNote(note: NoteEntity) {
        databaseService.NoteDao().deleteNote(note)
    }

    suspend fun updateNote(note: NoteEntity) {
        databaseService.NoteDao().updateNote(note)
    }

    suspend fun getNotes(): List<NoteEntity> = databaseService.NoteDao().getAllNotes()

}