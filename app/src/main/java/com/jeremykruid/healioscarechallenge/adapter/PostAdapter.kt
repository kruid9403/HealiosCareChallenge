package com.jeremykruid.healioscarechallenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jeremykruid.healioscarechallenge.R
import com.jeremykruid.healioscarechallenge.model.PostsItem

class PostAdapter(
    val context: Context,
    private val postList: MutableList<PostsItem>,
    private var action: PostClicked):
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(post: PostsItem){
            itemView.findViewById<TextView>(R.id.row_post_title).text = post.title
            itemView.findViewById<TextView>(R.id.row_post_body).text = post.body
            itemView.setOnClickListener {
                action.postClicked(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_list, parent, false))

    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post = postList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    interface PostClicked {
        fun postClicked(post: PostsItem)
    }
}