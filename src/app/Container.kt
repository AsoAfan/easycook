package app

import exceptions.UnresolvableDependency

class Container {

    private val bindings = mutableMapOf<String, Any>()


    fun bind(key: String, impl: () -> Any) {
        bindings[key] = impl()
    }

    fun singleton(key: String, impl: () -> Any) {

        if (!bindings.containsKey(key)) {
            bindings[key] = impl()
        }

    }

    fun <T> resolve(key: String): T {
        if (!bindings.containsKey(key)) {
            throw UnresolvableDependency()
        }
        val resolver = bindings[key]!!
        return resolver as T
    }

}