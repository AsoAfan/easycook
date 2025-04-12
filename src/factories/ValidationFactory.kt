package factories

import app.ValidationInterface
import app.validators.ConfirmValidation


object ValidationFactory {

    fun generateValidator(rule: String): Pair<ValidationInterface, List<String>> {

        val parts = rule.split(':')
        val validator: String = parts[0]
        val params = if (parts.size > 1) parts[1].split(",") else emptyList()

        return when (validator) {
            "confirm" -> ConfirmValidation() to params
            else -> throw IllegalArgumentException()
        }
    }
}