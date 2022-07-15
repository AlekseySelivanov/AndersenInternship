package com.example.data.local.db.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.db.ApplicationDatabase
import com.example.data.local.db.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase( context: Context): ApplicationDatabase = Room
        .databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            "database"
        )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideLoansDao(appDatabase: ApplicationDatabase): UserDao = appDatabase.userDao()
}