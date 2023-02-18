package com.vismay.makeanote.data.local.db

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.vismay.makeanote.data.local.db.dao.NoteDao
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.data.local.db.typeconverter.DateTypeConverter
import javax.inject.Singleton

@Singleton
@Database(
    entities = [NoteEntity::class],
    version = 3,
    exportSchema = true,
    autoMigrations =
    [AutoMigration(from = 2, to = 3, spec = DatabaseService.ColumDeleteAutoMigration::class)]
)
@TypeConverters(DateTypeConverter::class)
abstract class DatabaseService : RoomDatabase() {
    @DeleteColumn(columnName = "test", tableName = "notes")
    class ColumDeleteAutoMigration : AutoMigrationSpec

    abstract fun NoteDao(): NoteDao
}