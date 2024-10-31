package exceptions

class UnresolvableDependency(msg: String = "Unresolvable object") : Exception(msg) {


    override fun printStackTrace() {
        println(message)
    }


}
