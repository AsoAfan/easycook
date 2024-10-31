package app.models

import app.DataSource

data class Post(
    var title: String,
    var content: String,
    val user: Map<String, Any?>? = DataSource.session["user"],

    var ingredients: MutableList<Ingredient>,
    var categories: MutableList<Category>,

    val id: Int = ++_id
) {


    companion object {
        private var _id = 0;
    }

    fun ownerId() = user?.get("id")
}
