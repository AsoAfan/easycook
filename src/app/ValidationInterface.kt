package app

interface ValidationInterface {

    fun validate(value: Any, params: List<String> = emptyList()): Boolean
    fun message(): String

}