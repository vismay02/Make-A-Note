package com.vismay.makeanote.di.module

import android.content.Context
import androidx.room.Room
import com.vismay.makeanote.data.local.db.DatabaseService
import com.vismay.makeanote.data.local.db.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideNoteDao(databaseService: DatabaseService): NoteDao = databaseService.NoteDao()

    @Provides
    @Singleton
    fun provideDatabaseService(@ApplicationContext applicationContext: Context): DatabaseService =
        Room.databaseBuilder(
            applicationContext, DatabaseService::class.java, "Note"
        ).build()

}