package views.categories

import app.Routes
import app.controllers.CategoryController
import app.interfaces.Renderable
import app.models.User
import utills.UI

class SingleCategoryView : Renderable {

    private lateinit var name: String
    private lateinit var image: String
    private var category: Map<String, Any>? = null;
    override fun render(props: Any?) {
        category = CategoryController().show(props!!)

        println(category)

        if (User.isAdmin()) {
            println("0. Delete category")
            println("1. update category")
            val option = UI.getUserOption()
            if (option == 0) {
                println("Are you sure you want to delete this category: ${category!!["name"]}")
                println("1. yes")
                println("2. no")
                val verify = UI.getUserOption()
                if (verify == 1)
                    CategoryController().delete(category!!["id"]!!)
                else Routes.navigateBack()
            } else if (option == 1) {
                showInputs()

            }
        }


    }

    private fun showInputs() {
        name = UI.input("category name")
        image = UI.input("category image")
        submit()
    }

    private fun submit() {
        CategoryController().update(
            category!!["id"].toString(),
            mapOf("name" to name, "imageUrl" to image)
        )
    }


}