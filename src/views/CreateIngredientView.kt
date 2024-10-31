package views

import app.Routes
import app.controllers.IngredientsController
import utills.errorln

class CreateIngredientView : Renderable {

    private lateinit var name: String
    private lateinit var image: String

    override fun render(props: Any?) {
        UI.pageHeader("Create Ingredient")
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
        name = UI.input("ingredient name")
        image = UI.input("ingredient image")
        submit()
    }

    private fun submit() {
        if (name.isEmpty() || image.isEmpty()) {
            errorln("invalid inputs")
        }

        IngredientsController().store(
            mapOf("name" to name, "image" to image)
        )
    }
}