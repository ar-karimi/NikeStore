package com.ark.nikestore.data.repo.source

import com.ark.nikestore.data.Comment
import com.ark.nikestore.services.httpClient.ApiService
import io.reactivex.Single
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    CommentDataSource {
    override fun getAll(productId: Int): Single<List<Comment>> = apiService.getComments(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}