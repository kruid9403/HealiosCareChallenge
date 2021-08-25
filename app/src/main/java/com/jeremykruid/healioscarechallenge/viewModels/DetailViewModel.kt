package com.jeremykruid.healioscarechallenge.viewModels

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeremykruid.healioscarechallenge.model.Comment
import com.jeremykruid.healioscarechallenge.model.Posts
import com.jeremykruid.healioscarechallenge.model.PostsItem
import com.jeremykruid.healioscarechallenge.model.Users
import okhttp3.*
import java.io.IOException

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val post by lazy { MutableLiveData<PostsItem>() }
    val comments by lazy { MutableLiveData<Comment>() }
    val users by lazy { MutableLiveData<Users>() }

    private val sharedPrefs = application.getSharedPreferences("Helios", Context.MODE_PRIVATE)

    fun getArgs(arguments: Bundle?){
        if (arguments != null){

            val newPost = arguments?.get("postItem") as PostsItem
            post.postValue(newPost)
        }
    }

    fun getComments(){
        val oldComments: Comment? = getCommentsPrefs()
        if(oldComments != null){
            comments.postValue(oldComments)
        }else {
            val client = OkHttpClient()

            val url = "https://jsonplaceholder.typicode.com/comments"

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("error", e.localizedMessage)
                }

                override fun onResponse(call: Call, response: Response) {

                    val result = Gson().fromJson(response.body?.string(), Comment::class.java)

                    comments.postValue(result)
                    setCommentsPrefs(result!!)
                }

            })
        }
    }

    fun getUsers() {
        val savedUsers: Users? = getUsersPrefs()
        if (savedUsers != null){
            users.postValue(savedUsers)
        }else {
            val client = OkHttpClient()

            val url = "https://jsonplaceholder.typicode.com/users"

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("error", e.localizedMessage)
                }

                override fun onResponse(call: Call, response: Response) {

                    val result = Gson().fromJson(response.body?.string(), Users::class.java)

                    users.postValue(result)
                    setUsersPrefs(result)
                }

            })
        }
    }

    private fun setCommentsPrefs(result: Comment) {
        val gson = Gson()
        val json = gson.toJson(result)

        sharedPrefs.edit().putString("comments", json).apply()
    }

    private fun getCommentsPrefs(): Comment?{
        var savedPosts: Comment?
        val gson = Gson()
        val json = sharedPrefs.getString("comments", "")

        savedPosts = if (json!!.isNotEmpty()){
            val type = object : TypeToken<Comment>(){}.type
            gson.fromJson(json, type)
        }else{
            null
        }

        return savedPosts
    }

    private fun setUsersPrefs(result: Users) {
        val gson = Gson()
        val json = gson.toJson(result)

        sharedPrefs.edit().putString("users", json).apply()
    }

    private fun getUsersPrefs(): Users?{
        var savedUsers: Users?
        val gson = Gson()
        val json = sharedPrefs.getString("users", "")

        savedUsers = if (json!!.isNotEmpty()){
            val type = object : TypeToken<Users>(){}.type
            gson.fromJson(json, type)
        }else{
            null
        }

        return savedUsers
    }
}