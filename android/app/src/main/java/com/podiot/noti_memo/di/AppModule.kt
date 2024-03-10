package com.podiot.noti_memo.di

import android.content.Context
import androidx.room.Room
import com.podiot.noti_memo.data.dao.NoteDao
import com.podiot.noti_memo.data.datasource.NoteLocalDatabase
import com.podiot.noti_memo.data.repository.NoteRepositoryImpl
import com.podiot.noti_memo.domain.repository.NoteRepository
import com.podiot.noti_memo.domain.usecase.CreateNoteUsecase
import com.podiot.noti_memo.domain.utils.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

}