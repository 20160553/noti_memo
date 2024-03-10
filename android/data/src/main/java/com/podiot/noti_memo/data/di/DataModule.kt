package com.podiot.noti_memo.data.di

import android.content.Context
import androidx.room.Room
import com.podiot.noti_memo.data.dao.NoteDao
import com.podiot.noti_memo.data.datasource.NoteLocalDatabase
import com.podiot.noti_memo.data.repository.NoteRepositoryImpl
import com.podiot.noti_memo.domain.repository.NoteRepository
import com.podiot.noti_memo.domain.utils.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideNoteRepositoryImpl(noteDao: NoteDao): NoteRepository = NoteRepositoryImpl(noteDao)

    @Singleton
    @Provides
    fun provideNoteDao(noteLocalDatabase: NoteLocalDatabase): NoteDao = noteLocalDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteLocalDatabase =
        Room.databaseBuilder(
            context,
            NoteLocalDatabase::class.java,
            DB_NAME
        )
//            .addMigrations(MIGRATION_1_2) //TODO
            .build()
}