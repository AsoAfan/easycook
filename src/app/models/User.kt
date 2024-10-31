package app.models

import app.DataSource

data class User(
    var username: String,
    var email: String,
    var password: String,
    var role: String = "user",
    var id: Int? = null,
) {

    init {
        this.id = ++_id
    }

    companion object {
        private var _id: Int = 0

        fun isAdmin(): Boolean = DataSource.session["user"]?.get("role") == "admin"
        fun getAuth(): User? = DataSource.users.find { it.id == DataSource.session["user"]?.get("id") }

    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to this.id,
            "username" to this.username,
            "email" to this.email,
            "password" to this.password,
            "role" to this.role,
        )
    }


}