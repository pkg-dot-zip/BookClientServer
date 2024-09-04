package com.pkg_dot_zip.com.pkg_dot_zip.server

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.IOException
import java.net.ServerSocket
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

class Server {

    fun attemptLaunch(port: Int) {
        try {
            launch(port)
        } catch (e: IOException) {
            logger.error { "Could not listen on port $port" }
            exitProcess(-1)
        }
    }

    private fun launch(port: Int) {
        val listening = true

        ServerSocket(port).use { serverSocket ->
            while (listening) MultiServerThread(serverSocket.accept()).start()
        }
    }

    companion object {
        const val SERVER_PORT = 88
    }
}

fun main() = Server().attemptLaunch(Server.SERVER_PORT)