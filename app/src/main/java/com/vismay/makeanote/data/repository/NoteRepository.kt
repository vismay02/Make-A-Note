package com.vismay.makeanote.data.repository

import com.vismay.makeanote.data.local.db.entity.LaunchWithMatchInfo
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import javax.inject.Singleton

@Singleton
interface NoteRepository {

    suspend fun getNotes(): List<NoteEntity>

    suspend fun saveNote(note: NoteEntity)

    suspend fun updateNote(note: NoteEntity)

    suspend fun deleteNote(note: NoteEntity)

    suspend fun search(query: String): List<NoteEntity>

    suspend fun searchWithMatchInfo(query: String): List<LaunchWithMatchInfo>

    suspend fun fetchNewNote(): NoteEntity

    suspend fun fetchUpdatedNote(id: Int): NoteEntity
}