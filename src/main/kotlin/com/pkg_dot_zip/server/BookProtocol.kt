package com.pkg_dot_zip.server

import com.pkg_dot_zip.bookquery.BookHandler
import com.pkg_dot_zip.util.ISBN

class BookProtocol {
    private var state = State.WAITING
    private var currentISBN: ISBN = ""

    fun processInput(theInput: String): String? {
        var theOutput: String? = null

        when (state) {
            State.WAITING -> {
                theOutput = "Please input book ISBN:"
                state = State.SENT_BOOK_ISBN
            }

            State.SENT_BOOK_ISBN -> {
                if (theInput.isNotEmpty()) { // TODO: Implement ISBN regex check.
                    currentISBN = theInput // TODO: Store book here ONLY for this specific client (maybe in a map?)
                    theOutput =
                        "Please request either $INPUT_TITLE, $INPUT_SUBTITLE or $INPUT_AUTHORS. Type $INPUT_SWITCH to switch books. Type $INPUT_QUIT to quit."
                    state = State.SEND_INFO
                } else {
                    theOutput = "Please insert a valid ISBN"
                }
            }

            State.SEND_INFO -> {
                when (theInput) {
                    INPUT_TITLE -> theOutput = BookHandler.getTitle(currentISBN)
                    INPUT_SUBTITLE -> theOutput = BookHandler.getSubtitle(currentISBN)
                    INPUT_AUTHORS -> theOutput = BookHandler.getAuthors(currentISBN)
                    INPUT_SWITCH -> {
                        state = State.WAITING
                        return processInput("")
                    }

                    INPUT_QUIT -> {
                        theOutput = "Bye."
                        state = State.WAITING
                    }
                }
            }
        }
        return theOutput
    }

    companion object {
        enum class State {
            WAITING,
            SENT_BOOK_ISBN,
            SEND_INFO;
        }

        //<editor-fold desc="Commands">
        private const val INPUT_SWITCH = "switch"
        private const val INPUT_QUIT = "quit"
        private const val INPUT_TITLE = "title"
        private const val INPUT_SUBTITLE = "subtitle"
        private const val INPUT_AUTHORS = "authors"
        //</editor-fold>
    }
}