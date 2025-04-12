package views.ingredients

import app.Routes
import app.controllers.IngredientsController
import app.interfaces.Renderable
import app.models.User
import utills.UI

class SingleIngredientView : Renderable {


    private lateinit var name: String
    private lateinit var image: String

    var ingredient: Map<String, Any>? = null

    override fun render(props: Any?) {
        ingredient = IngredientsController().show(props!!)

        println(ingredient.toString())

        if (User.isAdmin()) {
            println("0. Delete ingredient")
            println("1. Update ingredient")
            val option = UI.getUserOption()
            if (option == 0) {
                println("Are you sure you want to delete this ingredient: ${ingredient?.get("name")}")
                println("1. yes")
                println("2. no")
                val verify = UI.getUserOption()
                if (verify == 1)
                    IngredientsController().delete(ingredient!!["id"].toString())
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
        IngredientsController().update(
            ingredient!!["id"].toString(),
            mapOf("name" to name, "imageUrl" to image)
        )
    }

}