package utills

import app.Application
import app.DataSource
import app.enums.COLORS
import kotlin.system.exitProcess

fun equals(str1: String, str2: String): Boolean {
    return str1 === str2
}

fun resetColor() {
    print(COLORS.RESET.value)
}

fun redColor() {
    print(COLORS.RED.value)
}

fun errorln(msg: String) {
    redColor()
    println(msg)
    resetColor()
}

fun <T> getDependency(name: String): T {
    return Application.container.resolve(name)
}

fun dd(values: Iterable<Any>) {
    for (v in values) {
        println(v)
    }
    exitProcess(1)
}

fun serializeToMap(str: String): Map<String, Any> {

    val map = mutableMapOf<String, Any>()
    val body = str.substring(1, str.length - 1).split(", ")
    for (data in body) {
        val split = data.split("=")
        val key = split.first()
        val value = split.last()
        map[key] = value
    }

    return map
}

fun serializeToList(str: String): List<Map<String, Any>> {

    val list = mutableListOf<Map<String, Any>>()
    val body = str.substring(1, str.length - 1)
    val endOfClassNameIndex = body.indexOfFirst { c -> c == '(' }
    val data = body.split("), ")
    for (d in data) {
        val items = d.substring(endOfClassNameIndex + 1)
        val element = serializeToMap(items.split(", ").toString())
        list.add(element)
    }
    return list
}

fun putToSession(key: String, value: Map<String, Any?>?) {
    DataSource.session[key] = value
}