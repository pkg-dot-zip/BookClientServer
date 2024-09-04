package com.pkg_dot_zip.com.pkg_dot_zip.server

class BookProtocol {
    private var state = WAITING
    private var currentJoke = 0

    private val clues = arrayOf("Turnip", "Little Old Lady", "Atch", "Who", "Who")
    private val answers = arrayOf(
        "Turnip the heat, it's cold in here!",
        "I didn't know you could yodel!",
        "Bless you!",
        "Is there an owl in here?",
        "Is there an echo in here?"
    )

    fun processInput(theInput: String?): String? {
        var theOutput: String? = null

        if (state == WAITING) {
            theOutput = "Knock! Knock!"
            state = SENTKNOCKKNOCK
        } else if (state == SENTKNOCKKNOCK) {
            if (theInput.equals("Who's there?", ignoreCase = true)) {
                theOutput = clues[currentJoke]
                state = SENTCLUE
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! " +
                        "Try again. Knock! Knock!"
            }
        } else if (state == SENTCLUE) {
            if (theInput.equals(clues[currentJoke] + " who?", ignoreCase = true)) {
                theOutput = answers[currentJoke] + " Want another? (y/n)"
                state = ANOTHER
            } else {
                theOutput = "You're supposed to say \"" +
                        clues[currentJoke] +
                        " who?\"" +
                        "! Try again. Knock! Knock!"
                state = SENTKNOCKKNOCK
            }
        } else if (state == ANOTHER) {
            if (theInput.equals("y", ignoreCase = true)) {
                theOutput = "Knock! Knock!"
                if (currentJoke == (NUMJOKES - 1)) currentJoke = 0
                else currentJoke++
                state = SENTKNOCKKNOCK
            } else {
                theOutput = "Bye."
                state = WAITING
            }
        }
        return theOutput
    }

    companion object {
        private const val WAITING = 0
        private const val SENTKNOCKKNOCK = 1
        private const val SENTCLUE = 2
        private const val ANOTHER = 3

        private const val NUMJOKES = 5
    }
}