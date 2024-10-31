package app

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Application(private val host: String = "localhost", private val port: Int = 5000) { // Client
//    val bufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
//    val bufferedWriter = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))

    companion object {
        val container: Container = Container()
//        val instance by lazy { Application() }
    }

    fun sendRequest(method: String, route: String, data: Any = ""): String {
        Socket(host, port).use { socket ->
            val output = PrintWriter(socket.getOutputStream(), true)
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))

            val request = "$method $route"
            output.println(request)

            if (method == "POST") {
                output.println(data)
            }

            val response = input.readLine()
            return response
        }
    }

    /*
        fun boostrap() {
            container.bind("application") {
                Routes.defineRoutes()
                this
            }

            container.bind("validation") {
                ValidationHandler()
            }

            Routes.navigate("onBoarding")

        }
    */


}