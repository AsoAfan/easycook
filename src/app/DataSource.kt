package app

import app.models.Category
import app.models.Ingredient
import app.models.Post
import app.models.User

object DataSource {

    val users: MutableList<User> = mutableListOf()
    val session: MutableMap<String, Map<String, Any?>?> = mutableMapOf()

    val categories: MutableList<Category> = mutableListOf()
    val ingredients: MutableList<Ingredient> = mutableListOf()
    val posts: MutableList<Post> = mutableListOf()

}