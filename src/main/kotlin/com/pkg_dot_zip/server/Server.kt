package com.pkg_dot_zip.com.pkg_dot_zip.server

import java.io.IOException
import java.net.ServerSocket
import kotlin.system.exitProcess

class Server {

    fun launch(portNumber: Int) {
        val listening = true

        try {
            ServerSocket(portNumber).use { serverSocket ->
                while (listening) {
                    MultiServerThread(serverSocket.accept()).start()
                }
            }
        } catch (e: IOException) {
            System.err.println("Could not listen on port $portNumber")
            exitProcess(-1)
        }
    }

    companion object {
        const val SERVER_PORT = 88
    }
}

fun main() {
    val server = Server().apply {
        launch(Server.SERVER_PORT)
    }
}