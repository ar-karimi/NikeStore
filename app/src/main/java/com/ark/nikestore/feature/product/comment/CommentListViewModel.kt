package com.ark.nikestore.feature.product.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.Comment
import com.ark.nikestore.data.repo.CommentRepository

class CommentListViewModel(productId: Int, commentRepository: CommentRepository) : BaseViewModel() {

    private val commentListLiveData = MutableLiveData<List<Comment>>()
    init {
        progressBarLiveData.value = true
        commentRepository.getAll(productId)
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    commentListLiveData.value = t
                }
            })
    }

    fun getCommentListLiveData() : LiveData<List<Comment>> = commentListLiveData
}