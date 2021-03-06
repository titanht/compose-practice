package com.example.composeapplication.di

import android.app.Application
import androidx.room.Room
import com.example.composeapplication.feature_note.data.data_source.NoteDao
import com.example.composeapplication.feature_note.data.data_source.NoteDatabase
import com.example.composeapplication.feature_note.data.repository.NoteRepositoryImpl
import com.example.composeapplication.feature_note.domain.repository.NoteRepository
import com.example.composeapplication.feature_note.domain.use_case.AddNote
import com.example.composeapplication.feature_note.domain.use_case.DeleteNote
import com.example.composeapplication.feature_note.domain.use_case.GetNotes
import com.example.composeapplication.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase = Room.databaseBuilder(
        app,
        NoteDatabase::class.java, NoteDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository = NoteRepositoryImpl(db.noteDao)

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases = NoteUseCases(
        getNotes = GetNotes(repository),
        deleteNote = DeleteNote(repository),
        addNote = AddNote(repository)
    )
}