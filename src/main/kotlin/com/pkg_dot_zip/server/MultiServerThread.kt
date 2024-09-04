package com.pkg_dot_zip.com.pkg_dot_zip.server

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket


private val logger = KotlinLogging.logger {}

class MultiServerThread(private var socket: Socket? = null) : Thread("MultiServerThread") {

    override fun run() {
        try {
            logger.info { "Starting server at ${socket?.inetAddress} ${socket?.port}" }

            PrintWriter(socket!!.getOutputStream(), true).use { out ->
                BufferedReader(InputStreamReader(socket!!.getInputStream())).use { `in` ->
                    var inputLine: String
                    var outputLine: String?
                    val kkp = BookProtocol()
                    outputLine = kkp.processInput("")
                    out.println(outputLine)

                    while ((`in`.readLine().also { inputLine = it }) != null) {
                        outputLine = kkp.processInput(inputLine)
                        out.println(outputLine)
                        if (outputLine == "Bye") break
                    }
                    socket!!.close()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}