package com.mirafra.demo.di

import com.mirafra.demo.data.repository.AuthRepository
import com.mirafra.demo.data.repository.AuthRepositoryImpl
import com.mirafra.demo.data.repository.ProjectRepository
import com.mirafra.demo.data.repository.ProjectRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProjectRepository(
        impl: ProjectRepositoryImpl
    ): ProjectRepository
}