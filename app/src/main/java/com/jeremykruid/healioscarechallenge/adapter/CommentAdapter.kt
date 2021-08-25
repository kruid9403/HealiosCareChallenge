package com.jeremykruid.healioscarechallenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jeremykruid.healioscarechallenge.R
import com.jeremykruid.healioscarechallenge.model.CommentItem

class CommentAdapter(
    val context: Context,
    private val commentList: MutableList<CommentItem>):
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(comment: CommentItem){
            itemView.findViewById<TextView>(R.id.comment_name).text = comment.email
            itemView.findViewById<TextView>(R.id.comment_body).text = comment.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_comment, parent, false))

    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        val comment = commentList[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}
