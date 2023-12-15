package com.ark.nikestore.feature.product.comment

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.common.EXTRA_KEY_ID
import com.ark.nikestore.data.Comment
import com.ark.nikestore.databinding.ActivityCommentListBinding
import com.ark.nikestore.feature.product.CommentAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : BaseActivity() {

    private lateinit var binding: ActivityCommentListBinding
    private val viewModel: CommentListViewModel by viewModel { parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID)) }
    private val commentAdapter = CommentAdapter(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_list)
        binding.lifecycleOwner = this

        binding.commentsRv.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.commentsRv.adapter = commentAdapter

        viewModel.getCommentListLiveData().observe(this){
            commentAdapter.comments = it as ArrayList<Comment>
        }

        viewModel.progressBarLiveData.observe(this){
            showProgressBar(it)
        }

        binding.commentListToolbar.onBackBtnClickListener = View.OnClickListener {
            finish()
        }
    }
}