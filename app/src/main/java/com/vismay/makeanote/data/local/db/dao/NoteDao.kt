package com.vismay.makeanote.data.local.db.dao

import androidx.room.*
import com.vismay.makeanote.data.local.db.entity.LaunchWithMatchInfo
import com.vismay.makeanote.data.local.db.entity.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
     fun getAllNotes(): List<NoteEntity>

    @Insert
     fun insertNote(note: NoteEntity)

    @Delete
     fun deleteNote(note: NoteEntity)

    @Update
     fun updateNote(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE id is :id")
     fun fetchUpdatedNote(id: Int): NoteEntity

    @Query("SELECT * FROM notes ORDER BY id DESC LIMIT 1")
     fun fetchNewNote(): NoteEntity

    @Query(
        """
  SELECT *
  FROM notes
  JOIN notes_fts ON notes.note = notes_fts.note
  WHERE notes_fts MATCH :query
  """
    )
     fun search(query: String): List<NoteEntity>

    @Query(
        """
    SELECT *, matchinfo(notes_fts) as matchInfo
    FROM notes
    JOIN notes_fts ON notes.note = notes_fts.note
    WHERE notes_fts MATCH :query
  """
    )
     fun searchWithMatchInfo(query: String): List<LaunchWithMatchInfo>
}