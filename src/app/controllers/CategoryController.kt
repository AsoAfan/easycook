package app.controllers

import app.Application
import app.Routes
import utills.errorln
import utills.getDependency
import utills.serializeToList
import utills.serializeToMap

class CategoryController {

    fun index(): List<Map<String, Any>> {

        val app = getDependency<Application>("app")
        val resp = app.sendRequest("GET", "/categories")
        val data = serializeToList(resp)
        return (data)
    }

    fun show(id: Any): Map<String, Any> {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/category", mapOf("id" to id))
        val data = serializeToList(resp, false)

        return data.first()
    }

    fun store(formInput: Map<String, Any>) {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/category/create", formInput)

        val data = serializeToMap(resp)
        if (data.containsKey("error")) {
            errorln(data["error"].toString())
        } else {
            println(data["success"])
        }

        Routes.navigateBack()
    }

    fun delete(id: Any) {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/categories/delete", mapOf("id" to id))
        val data = serializeToMap(resp)
        if (data.containsKey("error")) {
            errorln(data["error"] as String)
        } else {
            println(data["success"] as String)
        }
        Routes.navigateBack()

    }

    fun update(id: String, inputs: Map<String, String>) {

        val app = getDependency<Application>("app")
        val resp = app.sendRequest(
            "POST", "/category/update", mapOf(
                "id" to id,
                "name" to inputs["name"],
                "imageUrl" to inputs["imageUrl"]
            )
        )

        println(resp)
        Routes.navigateBack()
    }

    fun sync() {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/categories/sync")
        println(resp)
        Routes.navigateBack()
    }
}