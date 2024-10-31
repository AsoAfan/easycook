package views

import app.ValidationHandler
import app.controllers.UserController
import app.middlewares.Guest
import utills.getDependency
import validations.Validator

class SignupView(private val validator: Validator) : Renderable, Validatable {
    private lateinit var username: String;
    private lateinit var email: String;
    private lateinit var password: String;
    private lateinit var passwordConfirmation: String;
    private lateinit var role: String;

    override fun middleware() {
        Guest().handle()
    }

    override fun render(props: Any?) {
        UI.pageHeader("Signup")
        UI.pageDescription("We glad to see you here\nkindly fill-up following questions: ")
        showSignupInputs()


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
        val validated = validate()

        if (validated is Map) {
            UserController().signup(validated)
        }
    }

    override fun validate(): Map<Any, Any>? {
        val formInputs: Map<Any, Any> = mapOf(
            "username" to this.username,
            "email" to this.email,
            "password" to this.password,
            "passwordConfirmation" to this.passwordConfirmation,
            "role" to this.role
        )
        val result = getDependency<ValidationHandler>("validation")
            .validate(
                validator.rules(),
                formInputs as Map<String, String>,
            )
        if (!result) {
            showSignupInputs()
            return null
        }
        return formInputs
    }


}