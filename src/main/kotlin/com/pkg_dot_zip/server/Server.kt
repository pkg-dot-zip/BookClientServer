package com.pkg_dot_zip.com.pkg_dot_zip.server

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.IOException
import java.net.ServerSocket
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

class Server {

    fun launch(portNumber: Int) {
        val listening = true

        try {
            ServerSocket(portNumber).use { serverSocket ->
                while (listening) MultiServerThread(serverSocket.accept()).start()
            }
        } catch (e: IOException) {
            logger.error { "Could not listen on port $portNumber" }
            exitProcess(-1)
        }
    }

    companion object {
        const val SERVER_PORT = 88
    }
}

fun main() = Server().launch(Server.SERVER_PORT)