package app.controllers

import app.core.serializeToMap
import app.models.Post

class PostController {
    fun index(body: String): String {
        val posts = Post.findAll()

        return posts.toString()
    }

    fun show(body: String): String {
        val data = serializeToMap(body)
        val post = Post.findById(data["id"].toString().toInt())
            ?: return mapOf(
                "error" to "post not found"
            ).toString()

        return post.toString()

    }
    /*
        fun store(body: String): String {
            val data = serializeToMap(body)
            val ingredientsData = serializeToList(data["ingredients"])
        }
    */
}