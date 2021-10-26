package com.vismay.makeanote.data.local.db.dao

import androidx.room.*
import com.vismay.makeanote.data.local.db.entity.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(users: NoteEntity)

    @Delete
    suspend fun deleteNote(user: NoteEntity)

    @Update
    suspend fun updateNote(user: NoteEntity)
}