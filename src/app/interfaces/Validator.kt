package app.interfaces

interface Validator {
    fun rules(): Map<String, String>
}