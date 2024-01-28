package com.ark.nikestore.di.repositories.userRepository

import com.ark.nikestore.data.repo.UserRepository
import com.ark.nikestore.data.repo.UserRepositoryImpl
import com.ark.nikestore.data.repo.source.UserDataSource
import com.ark.nikestore.data.repo.source.UserLocalDataSource
import com.ark.nikestore.data.repo.source.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

    @Binds
    @Singleton
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl):UserRepository

    @UserLocalDataSourceQualifier
    @Binds
    @Singleton
    abstract fun provideUserLocalDatasource(userLocalDataSource: UserLocalDataSource):UserDataSource

    @UserRemoteDataSourceQualifier
    @Binds
    @Singleton
    abstract fun provideUserRemoteDatasource(userRemoteDataSource: UserRemoteDataSource):UserDataSource
}