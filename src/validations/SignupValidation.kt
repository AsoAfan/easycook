package validations

import app.interfaces.Validator

object SignupValidation : Validator {

    override fun rules(): Map<String, String> {
        return mapOf(
            "passwordConfirmation" to "confirm"
        )
    }

}