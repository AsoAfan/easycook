package app.controllers

import app.Application
import app.Routes
import utills.errorln
import utills.getDependency
import utills.serializeToList
import utills.serializeToMap

class PostController {
    fun index(): List<Map<String, Any>> {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("GET", "/posts")
        println(resp)
        val list = serializeToList(resp)
        return list
    }

    fun getUser(postId: String, userId: String): String {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest(
            "POST", "/post/user", mapOf(
                "userId" to userId,
                "postId" to postId
            )
        )
        return resp
    }

    fun store(data: Map<String, Any>) {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/posts/create", data)
        val responseData = serializeToMap(resp)

        if (responseData.containsKey("error")) {
            errorln(responseData["error"].toString())
        } else println(responseData["success"].toString())

//        val post = Post(
//            title = data["title"] as String,
//            content = data["description"] as String,
//            ingredients = data["ingredients"] as MutableList<Ingredient>,
//            categories = data["categories"] as MutableList<Category>,
//        )


//        DataSource.posts.add(post)

        Routes.navigateBack()

    }

    fun delete(post: String) {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/post/delete", mapOf("id" to post))
        val data = serializeToMap(resp)
        if (data.containsKey("error")) {
            errorln(data["error"] as String)
        } else println(data["success"].toString())
        Routes.navigateBack()
    }

    fun update(postId: String, inputs: Map<String, Any>) {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/post/update", inputs)
        val data = serializeToMap(resp)
        if (data.containsKey("error")) {
            errorln(data["error"] as String)
        } else println(data["success"].toString())
//        post?.let {
//            it.title = inputs["title"] as String
//            it.content = inputs["description"] as String
//            it.categories = inputs["categories"] as MutableList<Category>
//            it.ingredients = inputs["ingredients"] as MutableList<Ingredient>
//        }

        Routes.navigateBack()
    }

    fun show(id: Any): Map<String, Any> {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/posts", mapOf("id" to id))
        val data = serializeToList(resp, false)

        return data.first()


    }

    fun likes(props: Any): String {
        val app = getDependency<Application>("app")
        val resp = app.sendRequest("POST", "/likes-count", mapOf("id" to props))
        return resp
    }


}
