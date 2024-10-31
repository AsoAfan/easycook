package models

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class ClientController(socket: Socket) : Runnable {


    private val inputReader: BufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
    private val outputWriter: BufferedWriter = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))

    init {
        DataSource.clientHandlers.add(this)
    }

    override fun run() {

    }

}