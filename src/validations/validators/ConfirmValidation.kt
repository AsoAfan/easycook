package validations.validators

import app.ValidationInterface
import utills.errorln

class ConfirmValidation : ValidationInterface {


    override fun validate(value: Any, params: List<String>): Boolean {
        if (value is List<*>) {
            return value[0] == value[1]
        }
        errorln("invalid data provided")
        return false
    }

    override fun message(): String {
        return ":attribute does not match"
    }
}