package app

import factories.ValidationFactory
import utills.errorln

class ValidationHandler {

    fun validate(rules: Map<String, String>, inputList: Map<String, Any>): Boolean {

//        val errors: MutableMap<String, String> = mutableMapOf()

        for ((fieldName, ruleList) in rules) {
            val value = inputList[fieldName] ?: ""
            val rulesList = ruleList.split("|")

            for (rule in rulesList) {

                val confirmValue = if (rule.startsWith("confirm")) {
                    val compareFieldKey = fieldName.replace("Confirmation", "")
                    listOf(inputList[compareFieldKey]?.toString() ?: "", value)
                } else {
                    value
                }

                val (validator, params) = ValidationFactory.generateValidator(rule)
                if (!validator.validate(confirmValue, params)) {
                    errorln(validator.message().replace(":attribute", fieldName))
                    return false

                }
            }
        }

        return true;

    }
}