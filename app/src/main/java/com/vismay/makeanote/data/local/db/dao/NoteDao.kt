package com.vismay.makeanote.data.local.db.dao

import androidx.room.*
import com.vismay.makeanote.data.local.db.entity.LaunchWithMatchInfo
import com.vismay.makeanote.data.local.db.entity.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<NoteEntity>

    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query(
        """
  SELECT *
  FROM notes
  JOIN notes_fts ON notes.note = notes_fts.note
  WHERE notes_fts MATCH :query
  """
    )
    suspend fun search(query: String): List<NoteEntity>

    @Query(
        """
    SELECT *, matchinfo(notes_fts) as matchInfo
    FROM notes
    JOIN notes_fts ON notes.note = notes_fts.note
    WHERE notes_fts MATCH :query
  """
    )
    suspend fun searchWithMatchInfo(query: String): List<LaunchWithMatchInfo>
}