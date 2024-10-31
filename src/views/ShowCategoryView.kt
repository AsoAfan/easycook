package views

import app.Routes
import app.controllers.CategoryController
import app.models.User

class ShowCategoryView : Renderable {


    override fun render(props: Any?) {
        UI.pageHeader("category")
        val categories = CategoryController().index()
//        println(categories)

        var i = 1
        val ids = mutableListOf<String>()
        for (category in categories) {
            ids.add(category["id"].toString())
            println("${category["id"]}. $category")

        }

        val isAdmin = User.isAdmin()
        if (isAdmin) {
            println("-2. sync categories")
            println("0. create new category")
        }

        val option = UI.getUserOption()
        if (option == -1) {
            Routes.navigateBack()
        } else if (isAdmin && option <= 0) {
            if (option == 0)
                Routes.navigate("categories.create")
            else if (option == -2) {
                CategoryController().sync()
            }
        } else {
            for (id in ids) {
                println(id)
                if (option.toString() == id) {
                    println("hi $id")
                    Routes.navigate("category", id)
                }
            }
        }
    }
}



