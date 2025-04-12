package views

import app.Application
import app.DataSource
import app.Routes
import app.controllers.UserController
import app.interfaces.Renderable
import app.middlewares.Auth
import utills.UI
import utills.getDependency

class HomeView : Renderable {

    override fun middleware() {
        Auth().handle()

    }

    override fun render(props: Any?) {
        val authUser = DataSource.session["user"]

        UI.pageHeader("Home")
        UI.pageDescription("Welcome ${authUser?.get("username")}")
        UI.separator(10)
        showOptions()

    }

    private fun showOptions() {
        val authUser = DataSource.session["user"]

        println("1. show categories")
        println("2. show ingredients")
        println("3. show posts")
        println("4. show user data")
        println("5. logout")
        if (authUser?.get("role") == "admin") {
            println("---")
            println("6. sync data to files")
        }

        val option = UI.getUserOption()
        handleOptions(option)
    }

    private fun handleOptions(option: Int) {
        val authUser = DataSource.session["user"]

        when (option) {
            1 -> {
                Routes.navigate("categories")
            }

            2 -> {
                Routes.navigate("ingredients")
            }

            3 -> {
                Routes.navigate("posts")
            }


            4 -> {
                Routes.navigate("profile")
            }

            5 -> {
                UserController().logout()
            }

            6 -> {
                if (authUser?.get("role") != "admin") {
                    wrongOption()
                    return
                }
                val app = getDependency<Application>("app")
                val resp = app.sendRequest("POST", "/sync")
                println(resp)
            }

            else -> {
                wrongOption()
            }
        }
    }

    private fun wrongOption() {
        println("invalid option, try again")
        handleOptions(UI.getUserOption())
    }


}