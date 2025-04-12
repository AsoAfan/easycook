package views.auth

import app.controllers.UserController
import app.interfaces.Renderable
import app.middlewares.Guest
import utills.UI

class LoginView : Renderable {

    private lateinit var username: String;
    private lateinit var password: String;

    override fun middleware() {
        Guest().handle()
    }

    override fun render(props: Any?) {
        UI.pageHeader("Login")
        UI.pageDescription("We glad see you here again")
        showLoginInputs()
    }

    private fun showLoginInputs() {
        username = UI.input("username")
        password = UI.input("password")
        submit()
    }

    private fun submit() {

        UserController().login(mapOf("username" to username, "password" to password))

    }

}