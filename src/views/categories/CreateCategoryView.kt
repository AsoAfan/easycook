package views.categories

import app.Routes
import app.controllers.CategoryController
import app.interfaces.Renderable
import utills.UI
import utills.errorln

class CreateCategoryView : Renderable {

    private lateinit var name: String
    private lateinit var image: String

    override fun render(props: Any?) {
        UI.pageHeader("Create Category")
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
        name = UI.input("category name")
        image = UI.input("category image")
        submit()
    }

    private fun submit() {
        if (name.isEmpty() || image.isEmpty()) {
            errorln("invalid inputs")
        }

        CategoryController().store(
            mapOf("name" to name, "imageUrl" to image)
        )
    }
}