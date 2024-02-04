package com.codecamp.php.di

import android.content.Context
import androidx.room.Room
import com.codecamp.php.data.local.BeerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : BeerDatabase {
        return Room.databaseBuilder(
            context,
            BeerDatabase::class.java,
            "beer-database"
        ).build()
    }
}