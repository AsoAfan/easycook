package app.controllers

import app.core.DataSource
import app.core.serializeToMap
import app.core.setSessionUser
import app.models.User

class UserController {

    fun signup(formInput: Any): String {

        val data = serializeToMap(formInput as String)

        val duplicatedUser = User.findByUsername(data["username"] as String)
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

        if (!user.save()) {
            return mapOf("error" to "An error occurred ").toString()
        }

        DataSource.users.add(user)
        setSessionUser(user)

        return mapOf(
            "message" to "User ${user.username} created successfully.",
            "id" to user.id,
            "email" to user.email,
            "username" to user.username,
            "role" to user.role,
        ).toString()
    }

    fun login(formInput: Any): String {
        val data = serializeToMap(formInput as String)
        val user =
            User.findByUsername(data["username"] as String) ?: return mapOf("error" to "invalid credentials").toString()
        if (user.password != data["password"] as String) {
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
        return User.findByUsername(DataSource.session["user"]?.get("username") as String)?.toMap().toString()
    }


    fun destroy(body: Any): String {

        val data = serializeToMap(body as String)

        val user = User.findById(data["id"]?.toInt() ?: 0)
        if (user != null && user.delete()) {
            DataSource.users.remove(user)
            DataSource.session.remove("user")
            return mapOf(
                "success" to "${user.username} deleted successfully",
            ).toString()

        }

        return mapOf("error" to "user not found").toString()

    }

    fun update(formInput: Any): String {


        val data = serializeToMap(formInput as String)

        val duplicatedUser = User.findByUsername(data["username"] as String)

        if (duplicatedUser != null) {
            return mapOf(
                "error" to "${duplicatedUser.username} already exists.",
            ).toString()
        }
        val user = User.findById(DataSource.session["user"]?.get("id") as Int)
            ?: return mapOf("error" to "user not found").toString()

        user.let {
            it.username = data["username"] as String
            it.email = data["email"] as String
            it.password = data["password"] as String
            it.role = data["role"] as String
        }

        user.update()
        setSessionUser(user)
        return mapOf(
            "success" to "updated successfully",
            "id" to user.id,
            "email" to user.email,
            "username" to user.username,
            "role" to user.role,
        ).toString()

    }

}