import models.DataSource
import models.User

fun serializeToMap(str: String): Map<String, String> {

    val map = mutableMapOf<String, String>()
    val body = str.substring(1, str.length - 1).split(", ")
    for (data in body) {
        val split = data.split("=")
        val key = split.first()
        val value = split.last()
        map[key] = value
        // {name=aso, age=21}
    }

    return map
}

fun setSessionUser(user: User) {
    DataSource.session["user"] = user.toMap()
}