package views

interface Validatable {

    fun validate(): Map<Any, Any>?

}