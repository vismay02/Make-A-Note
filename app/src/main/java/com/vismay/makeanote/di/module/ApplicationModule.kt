package com.vismay.makeanote.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.vismay.makeanote.MakeANoteApplication
import com.vismay.makeanote.data.local.db.DatabaseService
import com.vismay.makeanote.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val makeANoteApplication: MakeANoteApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = makeANoteApplication

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(): Context = makeANoteApplication

    @Provides
    @Singleton
    fun provideDatabaseService(): DatabaseService =
        Room.databaseBuilder(
            makeANoteApplication,
            DatabaseService::class.java,
            "notes-db"
        ).build()
}
