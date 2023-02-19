package com.vismay.makeanote.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vismay.makeanote.data.local.db.dao.NoteDao
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.data.local.db.entity.NoteEntityFTS
import com.vismay.makeanote.data.local.db.typeconverter.DateTypeConverter
import javax.inject.Singleton

@Singleton
@Database(
    entities = [NoteEntity::class, NoteEntityFTS::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateTypeConverter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun NoteDao(): NoteDao
}