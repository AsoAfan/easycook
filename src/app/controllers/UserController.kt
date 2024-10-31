package app.controllers

import app.Application
import app.Routes
import utills.errorln
import utills.getDependency
import utills.putToSession
import utills.serializeToMap

class UserController {

    fun signup(formInput: Map<Any, Any>) {
        val application = getDependency<Application>("app")

        val resp = application.sendRequest("POST", "/signup", formInput)

        val data = serializeToMap(resp)

        if (data["error"] != null) {
            errorln(data["error"] as String)
            Routes.navigate("login")
        }

        putToSession(
            key = "user",
            value = mapOf(
                "username" to data["username"],
                "role" to data["role"]
            )
        )
        Routes.navigate("home")
    }

    fun login(formInput: Map<Any, Any>) {
        val application = getDependency<Application>("app")
        val resp = application.sendRequest("POST", "/login", formInput)
        val data = serializeToMap(resp)

        if (data.containsKey("success")) {
            putToSession(
                "user",
                mapOf(
                    "username" to data["username"],
                    "role" to data["role"],
                    "id" to data["id"],
                )
            )

            Routes.navigate("home")
        } else {
            errorln(data["error"].toString())
            Routes.popView()
            Routes.navigate("login")
        }
    }

    fun logout() {
        val application = getDependency<Application>("app")
        val resp = application.sendRequest("POST", "/logout")

        val data = serializeToMap(resp)
        println(data)

        if (data.containsKey("success")) {
            putToSession("user", null)
            println(data["success"])
            Routes.navigate("login")
        } else {
            errorln("an error occurred")
            Routes.navigate("home")
        }
    }

    fun info(): Map<String, Any> {
        val application = getDependency<Application>("app")
        val resp = application.sendRequest("GET", "/user")
        return (serializeToMap(resp))
    }

    fun deleteAccount(user: Map<String, Any?>) {

        val application = getDependency<Application>("app")
        val resp = application.sendRequest(
            "POST", "/user",
            user
        )
        val data = serializeToMap(resp)
        if (data.containsKey("success")) {
            println(data["success"])
            Routes.navigate("onBoarding")
        } else {
            errorln(data["error"].toString())
            Routes.navigateBack()
        }

    }

    fun update(inputs: Map<String, String>) {

        val application = getDependency<Application>("app")
        val resp = application.sendRequest("POST", "/user/update", inputs)
        val data = serializeToMap(resp)
        if (data.containsKey("success")) {
            println(data["success"])
        } else {
            errorln(data["error"].toString())
            Routes.navigate("profile")
        }

        Routes.navigateBack()
    }

    fun sync() {
        val application = getDependency<Application>("app")
        val resp = application.sendRequest("POST", "/users/sync")
        println(resp)
        Routes.navigateBack()

    }
}