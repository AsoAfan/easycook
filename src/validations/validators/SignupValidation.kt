package validations.validators

import validations.Validator

object SignupValidation : Validator {

    override fun rules(): Map<String, String> {
        return mapOf(
            "passwordConfirmation" to "confirm"
        )
    }

}