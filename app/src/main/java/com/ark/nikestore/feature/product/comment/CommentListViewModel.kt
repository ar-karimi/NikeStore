package com.ark.nikestore.feature.product.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.Comment
import com.ark.nikestore.data.repo.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentListViewModel @Inject constructor(private val commentRepository: CommentRepository) :
    BaseViewModel() {

    private val commentListLiveData = MutableLiveData<List<Comment>>()

    fun getComments(productId: Int) {
        progressBarLiveData.value = true
        commentRepository.getAll(productId)
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentListLiveData.value = t
                }
            })
    }

    fun getCommentListLiveData(): LiveData<List<Comment>> = commentListLiveData
}