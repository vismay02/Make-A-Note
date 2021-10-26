package com.vismay.makeanote.di.component

import android.app.Application
import android.content.Context
import com.vismay.makeanote.MakeANoteApplication
import com.vismay.makeanote.data.local.db.DatabaseService
import com.vismay.makeanote.data.repository.NoteRepository
import com.vismay.makeanote.di.ApplicationContext
import com.vismay.makeanote.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: MakeANoteApplication)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getDatabaseService(): DatabaseService

    fun noteRepository(): NoteRepository
}