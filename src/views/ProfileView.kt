package views

import app.Routes
import app.controllers.UserController
import app.interfaces.Renderable
import app.middlewares.Auth
import utills.UI

class ProfileView : Renderable {

    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var passwordConfirmation: String
    private lateinit var role: String


    override fun middleware() {
        Auth().handle()
    }

    override fun render(props: Any?) {
        val userData = UserController().info()
        println(userData)
        println("1. update account")
        println("2. delete account")
        val option = UI.getUserOption()
        when (option) {
            1 -> {
                showSignupInputs()
            }

            2 -> {
                println("${userData["username"]} are you sure you want to delete? (1. yes, 2. no)")
                val verify = UI.getUserOption()
                if (verify == 1)
                    UserController().deleteAccount(mapOf("id" to userData["id"]))
                else
                    Routes.navigateBack()
            }

        }
    }

    private fun showSignupInputs() {
        username = UI.input("username")
        email = UI.input("email")
        password = UI.input("password")
        passwordConfirmation = UI.input("password_confirmation")
        role = UI.input("role")

        submit()
    }

    private fun submit() {

        UI.separator()
        UserController().update(
            mapOf(
                "username" to this.username,
                "email" to this.email,
                "password" to this.password,
                "passwordConfirmation" to this.passwordConfirmation,
                "role" to this.role
            )
        )

    }

}