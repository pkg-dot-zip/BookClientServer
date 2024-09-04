package com.pkg_dot_zip.com.pkg_dot_zip.client

import com.pkg_dot_zip.com.pkg_dot_zip.server.Server
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.net.UnknownHostException
import kotlin.system.exitProcess

class Client {

    fun attemptLaunch(hostName: String, port: Int) {
        try {
            launch(hostName, port)
        } catch (e: UnknownHostException) {
            System.err.println("Don't know about host $hostName")
            exitProcess(1)
        } catch (e: IOException) {
            System.err.println(
                "Couldn't get I/O for the connection to " +
                        hostName
            )
            exitProcess(1)
        }
    }

    private fun launch(hostName: String, port: Int) {
        Socket(hostName, port).use { kkSocket ->
            PrintWriter(kkSocket.getOutputStream(), true).use { out ->
                BufferedReader(
                    InputStreamReader(kkSocket.getInputStream())
                ).use { `in` ->
                    val stdIn =
                        BufferedReader(InputStreamReader(System.`in`))
                    var fromServer: String
                    var fromUser: String?
                    while ((`in`.readLine().also { fromServer = it }) != null) {
                        println("Server: $fromServer")
                        if (fromServer == "Bye.") break

                        fromUser = stdIn.readLine()
                        if (fromUser != null) {
                            println("Client: $fromUser")
                            out.println(fromUser)
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    Client().attemptLaunch("localhost", Server.SERVER_PORT)
}