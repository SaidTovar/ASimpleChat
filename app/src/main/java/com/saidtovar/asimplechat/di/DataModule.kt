package com.saidtovar.asimplechat.di

import android.content.ContentResolver
import android.content.Context
import com.saidtovar.asimplechat.data.repository.SmsRepositoryImpl
import com.saidtovar.asimplechat.domain.repository.SmsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideContentResolver(
        @ApplicationContext context: Context
    ): ContentResolver = context.contentResolver

}

@Module
@InstallIn(SingletonComponent::class)
interface DataModule2 {

    @Singleton
    @Binds
    fun bindsSmsRepository(
        smsRepository: SmsRepositoryImpl
    ): SmsRepository

}
