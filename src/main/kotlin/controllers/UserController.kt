package controllers

import models.DataSource
import models.User
import serializeToMap
import setSessionUser

class UserController {

    fun signup(formInput: Any): String {

        val data = serializeToMap(formInput as String)

        val duplicatedUser = DataSource.users.find { user -> user.username == data["username"] }

        if (duplicatedUser != null) {
            return mapOf(
                "error" to "${duplicatedUser.username} already exists.",
            ).toString()
        }

        val user = User(
            username = data["username"] as String,
            email = data["email"] as String,
            password = data["password"] as String,
            role = data["role"] as String,
        )

        DataSource.users.add(user)


        setSessionUser(user)

        return mapOf(
            "message" to "User ${user.username} created successfully.",
            "id" to user.id,
            "username" to user.username,
            "role" to user.role,
        ).toString()
    }

    fun login(formInput: Any): String {
        val data = serializeToMap(formInput as String)
        val user = DataSource.users.find {
            it.username == data["username"] && it.password == data["password"]
        }
        if (user == null) {
            return mapOf("error" to "invalid credentials").toString()
        }

        setSessionUser(user)

        return mapOf(
            "success" to "login success",
            "id" to user.id,
            "username" to user.username,
            "role" to user.role
        ).toString()
    }

    fun logout(body: Any): String {
        DataSource.session["user"] = null
        return mapOf(
            "success" to "logout success"
        ).toString()
    }

    fun sync(body: Any): String {
        DataSource.syncUsersToFile()
        return "sync success"
    }

    fun info(s: String): String {
        return DataSource.session["user"].toString()
    }


    fun destroy(body: Any): String {

        val data = serializeToMap(body as String)

        val user = DataSource.users.find { user -> user.id.toString() == data["id"] }
        if (user != null) {
            DataSource.users.remove(user)
            DataSource.session.remove("user")
            DataSource.syncUsersToFile()
            return mapOf(
                "success" to "${user.username} deleted successfully",
            ).toString()
        }

        return mapOf("error" to "user not found").toString()

    }

    fun update(formInput: Any): String {


        val data = serializeToMap(formInput as String)

        val duplicatedUser = DataSource.users.find { user -> user.username == data["username"] }

        if (duplicatedUser != null) {
            return mapOf(
                "error" to "${duplicatedUser.username} already exists.",
            ).toString()
        }
        val user = DataSource.users.find { user -> user.id == DataSource.session["user"]?.get("id") }
        if (user == null) {
            return mapOf("error" to "user not found").toString()
        }
        user.let {
            it.username = data["username"] as String
            it.email = data["email"] as String
            it.password = data["password"] as String
            it.role = data["role"] as String
        }
        setSessionUser(user)
        DataSource.syncUsersToFile()
        return mapOf(
            "success" to "updated successfully",
            "id" to user.id,
            "username" to user.username,
            "role" to user.role,
        ).toString()

    }

}