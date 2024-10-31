package views

import app.Routes
import utills.errorln
import views.UI.getUserOption
import views.UI.pageHeader
import kotlin.system.exitProcess

class OnBoarding : Renderable {

    override fun render(props: Any?) {
        pageHeader("Welcome to EasyCook")
        println("0- Exit")
        println("1- Signup")
        println("2- Sign in")
        println("press -1 anytime you want to go back")
        val option = getUserOption()
        handleOptions(option)

    }

    private fun handleOptions(option: Int) {
        when (option) {
            0 -> run {
                println("Good Bye")
                exitProcess(1)
            }

            1 -> run {
                Routes.navigate("signup")
            }

            2 -> run {
                Routes.navigate("login")
            }


            else -> {
                errorln("Invalid option")
                render()
            }

        }
    }
}