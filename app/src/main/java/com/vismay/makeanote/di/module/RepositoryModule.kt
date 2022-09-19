package com.vismay.makeanote.di.module

import com.vismay.makeanote.data.repository.NoteRepository
import com.vismay.makeanote.data.repository.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository
}