package views.categories

import app.Routes
import app.controllers.CategoryController
import app.interfaces.Renderable
import app.models.User
import utills.UI

class ShowCategoryView : Renderable {


    override fun render(props: Any?) {
        UI.pageHeader("category")
        val categories = CategoryController().index()

        val ids = mutableListOf<String>()
        for (category in categories) {
            ids.add(category["id"].toString())
            println("${category["id"]}. $category")

        }

        val isAdmin = User.isAdmin()
        if (isAdmin) {
            println("0. create new category")
        }

        val option = UI.getUserOption()
        if (option == -1) {
            Routes.navigateBack()
        } else if (isAdmin && option <= 0) {
            if (option == 0)
                Routes.navigate("categories.create")
        } else {
            for (id in ids) {
                if (option.toString() == id) {
                    Routes.navigate("category", id)
                }
            }
        }
    }
}



