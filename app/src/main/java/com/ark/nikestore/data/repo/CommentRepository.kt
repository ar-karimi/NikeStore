package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Comment
import io.reactivex.Single

interface CommentRepository {

    fun getAll(productId: Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}