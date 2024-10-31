import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(private val host: String, private val port: Int) {
    fun sendRequest(method: String, route: String) {
        Socket(host, port).use { socket ->
            val output = PrintWriter(socket.getOutputStream(), true)
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))

            val request = "$method $route"
            output.println(request)

            val response = input.readLine()
            println("Server response: $response")
        }
    }
}

fun main() {
    val client = Client("127.0.0.1", 5000)
    client.sendRequest("GET", "/hello")
    client.sendRequest("POST", "/hello")
    client.sendRequest("GET", "/time")
    client.sendRequest("POST", "/time") // Should return "Unknown route or method not allowed"
}
