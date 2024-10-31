package views

import app.DataSource
import app.Routes
import app.controllers.PostController
import app.models.Category
import app.models.Ingredient
import app.models.Post
import utills.errorln

class SinglePost : Renderable {

    private var post: Post? = null

    private lateinit var title: String
    private lateinit var description: String
    private var ingredients: MutableList<Ingredient> = mutableListOf()
    private var categories: MutableList<Category> = mutableListOf()

    override fun render(props: Any?) {

        post = DataSource.posts.find {
            props == it.id
        }

        println(post.toString())


        if (post?.ownerId() == DataSource.session["user"]?.get("id")) {
            println("0. Delete post")
            println("1. update post")
            val option = UI.getUserOption()
            if (option == 0) {
                println("Are you sure you want to delete this post")
                println("1. yes")
                println("2. no")
                val verify = UI.getUserOption()
                if (verify == 1)
                    PostController().delete(post)
                else Routes.navigateBack()
            } else if (option == 1) {
                showInputs()
            }
        }
    }


    private fun showInputs() {
        title = UI.input("post title")
        description = UI.input("post description")
        var ingredient: String = ""
        val totalIngredients = DataSource.ingredients.size
        val totalCategories = DataSource.categories.size

        while (ingredient != "-1" && ingredients.size < totalIngredients) {
            showIngredients()
            ingredient = UI.input("choose ingredient")
            if (ingredient.toInt() > totalIngredients) {
                errorln("invalid input")
            } else if (ingredient != "-1") {
                chooseIngredient(ingredient.toInt())
            }
        }

        var category: String = ""
        while (category != "-1" && categories.size < totalCategories) {
            showCategories()
            category = UI.input("choose category")
            if (category.toInt() > totalCategories) {
                errorln("invalid input")
            } else if (category != "-1") {
                chooseCategory(category.toInt())
            }
        }

        submit()
    }

    private fun showIngredients() {
        for (ingredient in DataSource.ingredients) {
            println("${ingredient.id}. ${ingredient.name}")
        }
    }

    private fun chooseIngredient(id: Int) {
        ingredients.add(
            DataSource.ingredients.find { it.id == id }!!
        )
    }

    private fun showCategories() {
        for (category in DataSource.categories) {
            if (!categories.map { it.id }.contains(category.id)) {
                println("${category.id}. ${category.name}")
            }
        }
    }

    private fun chooseCategory(id: Int) {
        categories.add(
            DataSource.categories.find { it.id == id }!!
        )
    }

    private fun submit() {
        if (title.isEmpty() || description.isEmpty()) {
            errorln("invalid inputs")
        }

        PostController().update(
            post,
            mapOf(
                "title" to title,
                "description" to description,
                "ingredients" to ingredients,
                "categories" to categories
            )
        )
    }


}