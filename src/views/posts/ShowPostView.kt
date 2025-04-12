package views.posts

import app.DataSource
import app.Routes
import app.controllers.PostController
import app.interfaces.Renderable
import utills.UI

class ShowPostView : Renderable {


    override fun render(props: Any?) {
        UI.pageHeader("posts")
        val posts = PostController().index()


        val ids = mutableListOf<String>()
        for (pos in posts) {
            ids.add(pos["id"].toString())
            println("________________________")
            println(
                "${pos["id"]}. ${pos["title"]} by ${
                    PostController().getUser(
                        pos["id"].toString(),
                        pos["user_id"].toString()
                    )
                }" +
                        "\n----------\n${pos["description"]}"
            )

        }

        val isAuth = DataSource.session["user"] != null
        if (isAuth)
            println("0. create new post")

        val option = UI.getUserOption()
        if (option == -1) {
            Routes.navigateBack()
        } else if (isAuth && option == 0) {
            Routes.navigate("posts.create")
        } else {
            for (id in ids) {
                if (option.toString() == id)
                    Routes.navigate("post", id)

            }
        }
    }

}

