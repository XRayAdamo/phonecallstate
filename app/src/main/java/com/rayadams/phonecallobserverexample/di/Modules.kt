package com.rayadams.phonecallobserverexample.di

import com.rayadams.phonecallobserverexample.repositories.ChatRepository
import com.rayadams.phonecallobserverexample.repositories.ChatRepositoryImpl
import com.rayadams.phonecallobserverexample.repositories.PhoneCallStatusRepository
import com.rayadams.phonecallobserverexample.repositories.PhoneCallStatusRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhoneCallStatusRepositoryModule {
    @Singleton
    @Provides
    fun providePhoneCallStatusRepository(): PhoneCallStatusRepository =
        PhoneCallStatusRepositoryImpl()
}

@Module
@InstallIn(SingletonComponent::class)
object ChatRepositoryModule {
    @Singleton
    @Provides
    fun provideChatRepositoryRepository(phoneCallStatusRepository: PhoneCallStatusRepository): ChatRepository =
        ChatRepositoryImpl(phoneCallStatusRepository)
}
