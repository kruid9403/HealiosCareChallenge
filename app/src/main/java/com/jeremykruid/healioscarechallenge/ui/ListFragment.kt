package com.jeremykruid.healioscarechallenge.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremykruid.healioscarechallenge.adapter.PostAdapter
import com.jeremykruid.healioscarechallenge.databinding.FragmentListBinding
import com.jeremykruid.healioscarechallenge.model.Posts
import com.jeremykruid.healioscarechallenge.model.PostsItem
import com.jeremykruid.healioscarechallenge.viewModels.ListViewModel

class ListFragment : Fragment(), PostAdapter.PostClicked {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: FragmentListBinding

    private val postObserver = Observer<Posts>{ postList ->
        if (postList != null){
            val adapter = PostAdapter(requireContext(), postList, this)
            binding.listRecycler.layoutManager = LinearLayoutManager(requireContext())
            binding.listRecycler.adapter = adapter
            adapter.notifyItemRangeInserted(0, postList.size)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.posts.observe(viewLifecycleOwner, postObserver)

        viewModel.getPosts()


        return binding.root
    }

    override fun postClicked(post: PostsItem) {
        viewModel.postClicked(post, binding.listRecycler)
    }


}