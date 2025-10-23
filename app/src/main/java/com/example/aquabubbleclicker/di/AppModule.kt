package com.example.aquabubbleclicker.di

import com.example.aquabubbleclicker.shared.repository.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGameRepository(): GameRepository {
        return GameRepository()
    }
}