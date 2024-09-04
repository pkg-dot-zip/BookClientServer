package com.pkg_dot_zip.com.pkg_dot_zip.server

import com.pkg_dot_zip.com.pkg_dot_zip.bookquery.BookHandler

class BookProtocol {
    private var state = WAITING
    private var currentISBN: String = ""

    fun processInput(theInput: String): String? {
        var theOutput: String? = null

        if (state == WAITING) {
            theOutput = "Please input book ISBN:"
            state = SENTBOOKISBN
        } else if (state == SENTBOOKISBN) {
            if (theInput.isNotEmpty()) { // TODO: Implement ISBN regex check.
                currentISBN = theInput // TODO: Store book here ONLY for this specific client (maybe in a map?)
                theOutput = "Please request either $INPUT_TITLE, $INPUT_SUBTITLE or $INPUT_AUTHORS. Type $INPUT_SWITCH to switch books. Type $INPUT_QUIT to quit."
                state = SENDINFO
            } else {
                theOutput = "Please insert a valid ISBN"
            }
        } else if (state == SENDINFO) {
            when (theInput) {
                INPUT_TITLE -> theOutput = "Title: ${BookHandler.getTitle(currentISBN)}"
                INPUT_SUBTITLE -> theOutput = "Subtitle: ${BookHandler.getSubtitle(currentISBN)}"
                INPUT_AUTHORS -> theOutput = "Authors: ${BookHandler.getAuthors(currentISBN)}"
                INPUT_SWITCH -> {
                    state = WAITING
                    return processInput("")
                }
                INPUT_QUIT -> {
                    theOutput = "Bye."
                    state = WAITING
                }
            }

        }
        return theOutput
    }

    companion object {
        //<editor-fold desc="States">
        private const val WAITING = 0
        private const val SENTBOOKISBN = 1
        private const val SENDINFO = 2
        //</editor-fold>


        //<editor-fold desc="Commands">
        private const val INPUT_SWITCH = "switch"
        private const val INPUT_QUIT = "quit"
        private const val INPUT_TITLE = "title"
        private const val INPUT_SUBTITLE = "subtitle"
        private const val INPUT_AUTHORS = "authors"
        //</editor-fold>
    }
}