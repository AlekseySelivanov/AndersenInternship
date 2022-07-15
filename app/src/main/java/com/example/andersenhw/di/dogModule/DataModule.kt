package com.example.andersenhw.di.dogModule

import com.example.andersenhw.data.repository.DogsRepository
import com.example.andersenhw.data.repository.DogsRepositoryData
import com.example.andersenhw.data.remote.DogsDataSource
import com.example.andersenhw.data.remote.DogsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun provideDogsRepository(repository: DogsRepositoryData): DogsRepository


    @Binds
    abstract fun provideDogsDataSource(datasource: DogsRemoteDataSource): DogsDataSource
}