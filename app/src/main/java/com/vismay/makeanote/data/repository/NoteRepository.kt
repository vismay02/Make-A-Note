package com.vismay.makeanote.data.repository

import androidx.lifecycle.LiveData
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import javax.inject.Singleton

@Singleton
interface NoteRepository {

    suspend fun getNotes(): List<NoteEntity>

}