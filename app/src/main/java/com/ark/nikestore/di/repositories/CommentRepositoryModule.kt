package com.ark.nikestore.di.repositories

import com.ark.nikestore.data.repo.CommentRepository
import com.ark.nikestore.data.repo.CommentRepositoryImpl
import com.ark.nikestore.data.repo.source.CommentDataSource
import com.ark.nikestore.data.repo.source.CommentRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CommentRepositoryModule {

    @Binds
    abstract fun provideCommentRepository(commentRepositoryImpl: CommentRepositoryImpl): CommentRepository

    @Binds
    abstract fun provideCommentRemoteDataSource(commentRemoteDataSource: CommentRemoteDataSource): CommentDataSource
}