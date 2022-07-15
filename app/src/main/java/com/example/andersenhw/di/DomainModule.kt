package com.example.andersenhw.di

import com.example.domain.repository.StringsRepository
import com.example.domain.repository.StringsRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Singleton
    @Binds
    fun bindStringRepository(stringsRepositoryImpl: StringsRepositoryImpl): StringsRepository
}