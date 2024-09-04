package com.pkg_dot_zip.com.pkg_dot_zip.client

import com.pkg_dot_zip.com.pkg_dot_zip.server.Server
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.net.UnknownHostException
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

class Client {

    fun attemptLaunch(hostName: String, port: Int) {
        try {
            launch(hostName, port)
        } catch (e: UnknownHostException) {
            logger.error { "Don't know about host $hostName" }
            exitProcess(1)
        } catch (e: IOException) {
            logger.error { "Couldn't get I/O for the connection to $hostName" }
            exitProcess(1)
        }
    }

    private fun launch(hostName: String, port: Int) {
        Socket(hostName, port).use { socket ->
            PrintWriter(socket.getOutputStream(), true).use { out ->
                BufferedReader(
                    InputStreamReader(socket.getInputStream())
                ).use { `in` ->
                    val stdIn =
                        BufferedReader(InputStreamReader(System.`in`))
                    var fromServer: String
                    var fromUser: String?
                    while ((`in`.readLine().also { fromServer = it }) != null) {
                        logger.info { "Server: $fromServer" }
                        if (fromServer == "Bye.") break

                        fromUser = stdIn.readLine()
                        if (fromUser != null) {
                            logger.info { "Client: $fromUser" }
                            out.println(fromUser)
                        }
                    }
                }
            }
        }
    }
}

fun main() = Client().attemptLaunch("localhost", Server.SERVER_PORT)