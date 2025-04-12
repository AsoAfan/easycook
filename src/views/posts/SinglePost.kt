package views.posts

import app.DataSource
import app.Routes
import app.controllers.CategoryController
import app.controllers.IngredientsController
import app.controllers.PostController
import app.interfaces.Renderable
import utills.UI
import utills.errorln

class SinglePost : Renderable {


    private lateinit var title: String
    private lateinit var description: String

    //    private var ingredients: MutableList<Ingredient> = mutableListOf()
//    private var categories: MutableList<Category> = mutableListOf()
    val choseIngredientList: MutableList<Int> = mutableListOf()
    val choseCategoryList: MutableList<Int> = mutableListOf()
    var post: Map<String, Any>? = null
    override fun render(props: Any?) {
//        val likes = PostController().likes(props!!)
        post = PostController().show(props!!)
        println(post)


        if (post!!["userId"] == DataSource.session["user"]?.get("id")) {
            println("0. Delete post")
            println("1. update post")
            val option = UI.getUserOption()
            if (option == 0) {
                println("Are you sure you want to delete this post")
                println("1. yes")
                println("2. no")
                val verify = UI.getUserOption()
                if (verify == 1)
                    PostController().delete(post!!["id"].toString())
                else Routes.navigateBack()
            } else if (option == 1) {
                showInputs()
            }
        } else {
            println("-1. go back")
            UI.getUserOption()
        }
    }


    private fun showInputs() {
        title = UI.input("post title")
        description = UI.input("post description")
        val ingredients = IngredientsController().index()
        val categories = CategoryController().index()
        val totalIngredients = ingredients.size
        val totalCategories = categories.size

        var ingredient = 0
        while (ingredient != -1 && choseIngredientList.size < totalIngredients) {
            showIngredients()
            ingredient = UI.input<String>("choose ingredient: ", false).toInt()
            if (ingredient > totalIngredients) {
                errorln("invalid input")
            } else if (ingredient != -1) {
                chooseIngredient(ingredient)
            }
        }

        var category: Int = 0
        while (category != -1 && categories.size < totalCategories) {
            showCategories()
            category = UI.input("choose category")
            if (category > totalCategories) {
                errorln("invalid input")
            } else if (category != -1) {
                chooseCategory(category)
            }
        }

        submit()
    }

    private fun showIngredients() {
        val ingredients = IngredientsController().index()
        for (ingredient in ingredients) {
            if (!choseIngredientList.contains(ingredient["id"])) {
                println("${ingredient["id"]}. ${ingredient["name"]}")
            }
        }
    }

    private fun chooseIngredient(id: Int) {
        choseIngredientList.add(id)
    }

    private fun showCategories() {
        val categories = CategoryController().index()
        for (category in categories) {
            if (!choseCategoryList.contains(category["id"])) {
                println("${category["id"]}. ${category["name"]}")
            }
        }
    }

    private fun chooseCategory(id: Int) {
        choseCategoryList.add(id)
    }

    private fun submit() {
        if (title.isEmpty() || description.isEmpty()) {
            errorln("invalid inputs")
        }

        PostController().update(
            post!!["id"].toString(),
            mapOf(
                "title" to title,
                "description" to description,
                "ingredients" to choseIngredientList,
                "categories" to choseCategoryList
            )
        )
        /*
        */
    }


}