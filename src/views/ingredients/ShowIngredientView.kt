package views.ingredients

import app.DataSource
import app.Routes
import app.controllers.IngredientsController
import app.interfaces.Renderable
import utills.UI

class ShowIngredientView : Renderable {


    override fun render(props: Any?) {
        UI.pageHeader("Ingredients")
        val ingredients = IngredientsController().index()

        val ids = mutableListOf<String>()
        for (ingredient in ingredients) {
            ids.add(ingredient["id"].toString())
            println("${ingredient["id"]}. $ingredient")

        }

        val isAdmin = DataSource.session["user"]?.get("role") == "admin"
        println("-1. Back")
        if (isAdmin)
            println("0. create new ingredient")

        val option = UI.getUserOption()
        if (option == -1) {
            Routes.navigateBack()
        } else if (isAdmin && option == 0) {
            Routes.navigate("ingredients.create")
        } else {
            for (id in ids) {
                if (option.toString() == id) {
                    Routes.navigate("ingredient", id)
                }
            }
        }
    }
}

