package com.pkg_dot_zip.com.pkg_dot_zip.server

import com.pkg_dot_zip.com.pkg_dot_zip.bookquery.BookHandler

class BookProtocol {
    private var state = WAITING
    private var currentJoke = 0

    fun processInput(theInput: String): String? {
        var theOutput: String? = null



        if (state == WAITING) {
            theOutput = "Please input book ISBN:"
            state = SENTBOOKISBN
        } else if (state == SENTBOOKISBN) {
            if (theInput.isNotEmpty()) { // TODO: Implement ISBN regex check.
                BookHandler.printAll(theInput) // TODO: Store book here ONLY for this specific client (maybe in a map?)
                state = SENDINFO
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! " +
                        "Try again. Knock! Knock!"
            }
        } else if (state == SENDINFO) {
            // TODO: Prompt user for de titel, de sub-titel en de auteurs.
        } else if (state == ANOTHER) {
            if (theInput.equals("y", ignoreCase = true)) {
                theOutput = "Knock! Knock!"
                if (currentJoke == (NUMJOKES - 1)) currentJoke = 0
                else currentJoke++
                state = SENTBOOKISBN
            } else {
                theOutput = "Bye."
                state = WAITING
            }
        }
        return theOutput
    }

    companion object {
        private const val WAITING = 0
        private const val SENTBOOKISBN = 1
        private const val SENDINFO = 2
        private const val ANOTHER = 3

        private const val NUMJOKES = 5
    }
}