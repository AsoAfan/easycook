package validations

interface Validator {
    fun rules(): Map<String, String>
}