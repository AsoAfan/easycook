package views

import app.DataSource
import app.Routes

class ShowPostView : Renderable {


    override fun render(props: Any?) {
        UI.pageHeader("posts")
        val posts = DataSource.posts

        var i = 1
        for (pos in posts) {
            println("${i++}. ${pos.title}\n----------\n${pos.content}")

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
            for (n in 0..i) {
                if (option == n)
                    Routes.navigate("post", n)

            }
        }
    }

}

