package com.jeremykruid.healioscarechallenge.viewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeremykruid.healioscarechallenge.R
import com.jeremykruid.healioscarechallenge.model.Posts
import com.jeremykruid.healioscarechallenge.model.PostsItem
import okhttp3.*
import java.io.IOException

class ListViewModel(application: Application): AndroidViewModel(application) {
    val posts by lazy { MutableLiveData<Posts>() }
    private val sharedPrefs = application.getSharedPreferences("Helios", Context.MODE_PRIVATE)

    fun getPosts(){

        val oldPosts: Posts? = getSharedPrefs()

        if (oldPosts != null) {
            posts.postValue(oldPosts)
            Log.e("oldPosts", oldPosts[0].toString())
        }else{
            val client = OkHttpClient()

            val url = "https://jsonplaceholder.typicode.com/posts"

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("error", e.localizedMessage)
                }

                override fun onResponse(call: Call, response: Response) {

                    val result = Gson().fromJson(response.body?.string(), Posts::class.java)
                    posts.postValue(result)
                    Log.e("newPosts", result[0].toString())
                    setSharedPrefs(result)
                }

            })
        }
    }

    private fun setSharedPrefs(result: Posts) {
        val gson = Gson()
        val json = gson.toJson(result)

        sharedPrefs.edit().putString("posts", json).apply()
    }

    private fun getSharedPrefs(): Posts?{
        var savedPosts: Posts?
        val gson = Gson()
        val json = sharedPrefs.getString("posts", "")

        savedPosts = if (json!!.isNotEmpty()){
            val type = object : TypeToken<Posts>(){}.type
            gson.fromJson(json, type)
        }else{
            null
        }

        return savedPosts
    }

    fun postClicked(postsItem: PostsItem, listRecycler: RecyclerView){
        val bundle = bundleOf(Pair("postItem", postsItem))
        Navigation.findNavController(listRecycler).navigate(R.id.action_listFragment_to_detailFragment, bundle)
    }
}