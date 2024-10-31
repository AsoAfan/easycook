package app.controllers

import app.Routes
import app.models.Category
import app.models.Ingredient
import app.models.Post

class PostController {
    fun store(data: Map<String, Any>) {
        val post = Post(
            title = data["title"] as String,
            content = data["description"] as String,
            ingredients = data["ingredients"] as MutableList<Ingredient>,
            categories = data["categories"] as MutableList<Category>,


            )
//        DataSource.posts.add(post)

        Routes.navigateBack()

    }

    fun delete(post: Post?) {
//        DataSource.posts.remove(post)
    }

    fun update(post: Post?, inputs: Map<String, Any>) {
        post?.let {
            it.title = inputs["title"] as String
            it.content = inputs["description"] as String
            it.categories = inputs["categories"] as MutableList<Category>
            it.ingredients = inputs["ingredients"] as MutableList<Ingredient>
        }

        Routes.navigateBack()
    }


}
