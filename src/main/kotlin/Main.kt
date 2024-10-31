fun main() {
    /*   val serverSocket = ServerSocket(8888)
       val server: Server = Server(serverSocket)
       println("Server listening on port ${serverSocket.localPort}")
       server.startServer()*/

    val server: Server = Server(5000);
    server.start();
}