package app.core

import java.io.File
import java.time.LocalDateTime

object Logger {
    private val LOGGER_FILE = File("")

    private const val INFO = 1
    private const val WARNING = 2
    private const val ERROR = 3
    private const val DEBUG = 4


    init {
        println(LOGGER_FILE.absolutePath)
        if (!LOGGER_FILE.exists()) {
            LOGGER_FILE.createNewFile()
        }
    }

    private fun write(tag: Int, text: String) {
        LOGGER_FILE.appendText("${getTag(tag)}:$text\n")
//        LOGGER_FILE.bufferedWriter().use { out ->
//            out.write("${getTag(tag)}:$text")
//            out.newLine()
//        }
    }

    fun info(str: String) {
        write(INFO, str)
    }

    fun warning(str: String) {
        write(WARNING, str)
    }

    fun error(str: String) {
        write(ERROR, str)
    }

    fun debug(str: String) {
        write(DEBUG, str)
    }

    private fun getTag(tag: Int): String = "${
        when (tag) {
            1 -> "INFO"
            2 -> "WARNING"
            3 -> "ERROR"
            4 -> "DEBUG"
            else -> "INFO"
        }
    }:${LocalDateTime.now()}:"
}