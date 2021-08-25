package com.jeremykruid.healioscarechallenge.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremykruid.healioscarechallenge.adapter.CommentAdapter
import com.jeremykruid.healioscarechallenge.databinding.FragmentDetailBinding
import com.jeremykruid.healioscarechallenge.model.Comment
import com.jeremykruid.healioscarechallenge.model.CommentItem
import com.jeremykruid.healioscarechallenge.model.PostsItem
import com.jeremykruid.healioscarechallenge.model.Users
import com.jeremykruid.healioscarechallenge.viewModels.DetailViewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var post: PostsItem

    private val postObserver = Observer<PostsItem>{ newPost->
        if (newPost != null){
            post = newPost
            binding.detailTitle.text = post.title
            binding.detailDescription.text = post.body

            viewModel.getComments()
            viewModel.getUsers()
        }
    }

    private val usersObserver = Observer<Users> { users ->
        users?.forEach { user->
            if (post.userId == user.id){
                binding.postUser.text = user.name
            }
        }
    }

    private val commentObserver = Observer<Comment> { commentList ->
        if (commentList != null){
            val postCommentList = ArrayList<CommentItem>()
            commentList.forEach {
                if (it.postId == post.id){
                    postCommentList.add(it)
                }
            }
            val adapter = CommentAdapter(requireContext(), postCommentList)
            binding.commentRecycler.layoutManager = LinearLayoutManager(requireContext())
            binding.commentRecycler.adapter = adapter
            adapter.notifyItemRangeInserted(0, postCommentList.size)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.post.observe(viewLifecycleOwner, postObserver)
        viewModel.comments.observe(viewLifecycleOwner, commentObserver)
        viewModel.users.observe(viewLifecycleOwner, usersObserver)

        viewModel.getArgs(arguments)

        return binding.root
    }

}