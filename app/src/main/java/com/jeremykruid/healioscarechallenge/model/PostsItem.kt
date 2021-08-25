package com.jeremykruid.healioscarechallenge.model

import java.io.Serializable

class PostsItem: Serializable {
    var userId: Int? = null
    var id: Int? = null
    var title: String? = null
    var body: String? = null

    constructor()
    constructor(userId: Int?, id: Int?, title: String?, body: String?){
        this.userId = userId
        this.id = id
        this.title = title
        this.body = body
    }
}