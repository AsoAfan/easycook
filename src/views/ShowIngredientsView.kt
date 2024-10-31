package views

import app.DataSource
import app.Routes
import app.controllers.IngredientsController

class ShowIngredientsView : Renderable {


    override fun render(props: Any?) {
        UI.pageHeader("Ingredients")
        val ingredients = IngredientsController().index()

        var i = 1
        for (ingredient in ingredients) {
            println("${i++}. $ingredient")

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
            for (n in 0..i) {
                if (option == n)
                    Routes.navigate("ingredient", n)
            }
        }
    }
}

