import controllers.CategoryController
import controllers.IngredientController
import controllers.UserController
import models.DataSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class Server(private val port: Int) {
    //    private val controller = MyController()
    private val routes = mutableMapOf<String, (String) -> String>()

    init {
        DataSource.loadFile()
        initializeRoutes()

    }

    private fun initializeRoutes() {
        val userController = UserController()
        val categoryController = CategoryController()
        val ingredientController = IngredientController()

        routes["POST /sync"] = {
            DataSource.syncToFile()
            "sync succeed"
        }

        routes["POST /signup"] = userController::signup
        routes["POST /login"] = userController::login
        routes["POST /logout"] = userController::logout
        routes["POST /users/sync"] = userController::sync
        routes["GET /user"] = userController::info
        routes["POST /user"] = userController::destroy
        routes["POST /user/update"] = userController::update

        routes["GET /categories"] = categoryController::index
        routes["POST /category"] = categoryController::show
        routes["POST /category/create"] = categoryController::store
        routes["POST /category/update"] = categoryController::update
        routes["POST /categories/sync"] = categoryController::sync
        routes["POST /categories/delete"] = categoryController::delete


        routes["GET /ingredients"] = ingredientController::index
        routes["POST /ingredient/create"] = ingredientController::store
        routes["POST /ingredient/update"] = ingredientController::update
        routes["POST /ingredient/delete"] = ingredientController::delete
        routes["GET /ingredient"] = ingredientController::show
        routes["POST /ingredients/sync"] = ingredientController::sync

    }

    fun start() {
        ServerSocket(port).use { serverSocket ->
            println("Server started on port $port")
            while (true) {
                val clientSocket = serverSocket.accept()
                println("Client connected: ${clientSocket.inetAddress.hostAddress}")
                Thread { handleClient(clientSocket) }.start()
            }
        }
    }

    private fun handleClient(clientSocket: Socket) {
        clientSocket.use {
            val input = BufferedReader(InputStreamReader(it.getInputStream()))
            val output = PrintWriter(it.getOutputStream(), true)

            input.lineSequence().forEach { request ->

                val parts = request.split(" ")
                if (parts.size < 2) {
                    output.println(mapOf("error" to "Invalid request format. Expected 'METHOD /route'"))
                    return@forEach
                }

                val method = parts[0]
                val route = parts[1]
                val routeKey = "$method $route"
                val handler = routes[routeKey]

                val postData = if (method == "POST") {
                    input.readLine()
                } else {
                    ""
                }

                val response =
                    handler?.invoke(postData ?: "") ?: mapOf("error" to "Unknown route or method not allowed: $routeKey")

                output.println(response)
            }
        }
    }
}

fun main() {
    val server = Server(5000)
    server.start()
}
