package app.controllers

import app.Application
import app.Routes
import utills.errorln
import utills.getDependency
import utills.serializeToList
import utills.serializeToMap

class IngredientsController {

    fun index(): List<Any> {

        val app = getDependency<Application>("app")
        val resp = app.sendRequest("GET", "/ingredients")
        val data = serializeToList(resp)
        return data
    }


    fun store(formInput: Map<String, Any>) {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/ingredient/create", formInput)

        val data = serializeToMap(resp)
        if (data.containsKey("error")) {
            errorln(data["error"].toString())
        } else {
            println(data["success"])
        }

        Routes.navigateBack()
    }

    fun delete(id: String) {
//        DataSource.ingredients.remove(ingredient)
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/ingredient/delete", mapOf("id" to id))
        val data = serializeToMap(resp)
        if (data.containsKey("error")) {
            errorln(data["error"] as String)
        } else {
            println(resp)
        }
        Routes.navigateBack()
    }

    fun update(id: String, inputs: Map<String, String>) {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest(
            "POST", "/ingredient/update", mapOf(
                "id" to id,
                "name" to inputs["name"],
                "imageUrl" to inputs["imageUrl"]
            )
        )

        println(resp)
        Routes.navigateBack()

    }
}