package app.core

import app.models.User

fun serializeToMap(str: String): Map<String, String> {

    val map = mutableMapOf<String, String>()
    val body = str.substring(1, str.length - 1).split(", ")
    for (data in body) {
        val split = data.split("=")
        val key = split.first()
        val value = split.last()
        map[key] = value
    }

    return map
}

fun serializeToList(str: String, isList: Boolean = true): List<Map<String, Any>> {

    if (str.length < 3) {
        return listOf()
    }

    val list = mutableListOf<Map<String, Any>>()
    val body = str.substring(1, str.length - if (isList) 2 else 1)
    val endOfClassNameIndex = body.indexOfFirst { c -> c == '(' }
    val data = body.split("), ")
    for (d in data) {
        val items = d.substring(endOfClassNameIndex + 1)
        val element = serializeToMap(items.split(", ").toString())
        list.add(element)
    }
    return list
}


fun setSessionUser(user: User) {
    DataSource.session["user"] = user.toMap()
}