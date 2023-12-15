package com.ark.nikestore.feature.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ark.nikestore.R
import com.ark.nikestore.data.Comment
import com.ark.nikestore.databinding.ItemCommentBinding

class CommentAdapter(val showAll: Boolean = false): RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var comments = ArrayList<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(comment: Comment){
            binding.comment = comment
            binding.executePendingBindings() //to finalize changes in view's binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_comment, parent, false))

    override fun getItemCount() = if (comments.size > 5 && !showAll) 5 else comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }
}