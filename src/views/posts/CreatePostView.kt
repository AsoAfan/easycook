package views.posts

import app.Routes
import app.controllers.CategoryController
import app.controllers.IngredientsController
import app.controllers.PostController
import app.interfaces.Renderable
import app.models.Category
import app.models.Ingredient
import utills.UI
import utills.errorln

class CreatePostView : Renderable {

    private lateinit var title: String
    private lateinit var description: String
    private var ingredients: MutableList<Ingredient> = mutableListOf()
    private var categories: MutableList<Category> = mutableListOf()
    val choseIngredientList: MutableList<Int> = mutableListOf()
    val choseCategoryList: MutableList<Int> = mutableListOf()


    override fun render(props: Any?) {
        UI.pageHeader("Create Post")
        showInputs()
        val option = UI.getUserOption()
        if (option == -1) {
            Routes.navigateBack()
        } else {
            errorln("Invalid Option")
            render()
        }
    }

    private fun showInputs() {
        title = UI.input("post title")
        description = UI.input("post description")
        val ingredients = IngredientsController().index()
        val categories = CategoryController().index()
        val totalIngredients = ingredients.size
        val totalCategories = categories.size
//        val choseCategoryList: MutableList<Int> = mutableListOf()

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
            /*
             */
        }

        submit()
    }

    private fun showIngredients() {
        val ingredients = IngredientsController().index()
        for (ingredient in ingredients) {
            println("${ingredient["id"]}. ${ingredient["name"]}")
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
//        categories.add(
//            DataSource.categories.find { it.id == id }!!
//        )
    }

    private fun submit() {
        if (title.isEmpty() || description.isEmpty()) {
            errorln("invalid inputs")
        }

        PostController().store(
            mapOf(
                "title" to title,
                "description" to description,
                "ingredients" to choseIngredientList,
                "categories" to choseCategoryList
            )
        )
    }
}