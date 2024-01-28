package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Comment
import com.ark.nikestore.data.repo.source.CommentDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(private val commentRemoteDataSource: CommentDataSource) :
    CommentRepository {
    override fun getAll(productId: Int): Single<List<Comment>> =
        commentRemoteDataSource.getAll(productId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}