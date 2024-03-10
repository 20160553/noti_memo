package com.podiot.noti_memo.domain.di

import com.podiot.noti_memo.domain.repository.NoteRepository
import com.podiot.noti_memo.domain.usecase.CreateNoteUsecase
import com.podiot.noti_memo.domain.usecase.DeleteNoteUsecase
import com.podiot.noti_memo.domain.usecase.GetNoteUsecase
import com.podiot.noti_memo.domain.usecase.UpdateNoteUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Singleton
    @Provides
    fun provideGetNoteUsecase(noteRepository: NoteRepository) = GetNoteUsecase(noteRepository)


    @Singleton
    @Provides
    fun provideCreateNoteUsecase(noteRepository: NoteRepository) = CreateNoteUsecase(noteRepository)

    @Singleton
    @Provides
    fun provideUpdateNoteUsecase(noteRepository: NoteRepository) = UpdateNoteUsecase(noteRepository)

    @Singleton
    @Provides
    fun provideDeleteNoteUsecase(noteRepository: NoteRepository) = DeleteNoteUsecase(noteRepository)


}