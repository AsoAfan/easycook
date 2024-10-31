package views

import app.DataSource
import app.Routes
import app.models.Ingredient
import app.models.User

class SingleIngredient : Renderable {


    private lateinit var name: String
    private lateinit var image: String

    var ingredient: Ingredient? = null
    override fun render(props: Any?) {
        ingredient = DataSource.ingredients.find {
            props == it.id
        }

        println(ingredient.toString())

        if (User.isAdmin()) {
            println("0. Delete ingredient")
            println("1. Update ingredient")
            val option = UI.getUserOption()
            if (option == 0) {
                println("Are you sure you want to delete this ingredient: ${ingredient?.name}")
                println("1. yes")
                println("2. no")
                val verify = UI.getUserOption()
                if (verify == 1)
//                    IngredientsController().delete(ingredient)
                else Routes.navigateBack()
            } else if (option == 1) {

            }
        }


    }

    private fun showInputs() {
        name = UI.input("category name")
        image = UI.input("category image")
        submit()
    }

    private fun submit() {
//        IngredientsController().update(
//            ingredient,
//            mapOf("name" to name, "image" to image)
//        )
    }

}