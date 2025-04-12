package utills

import app.Routes

object UI {

    fun separator(length: Int = 5, character: Char = '~', withSpace: Boolean = true) {

        for (i in 1..length) {
            print(character + if (withSpace) " " else "")
        }
        println()
    }

    fun pageHeader(title: String) {
        println(title)
        separator(15)
    }

    fun pageDescription(description: String) {
        println(description)
        separator(15, '_', false)
    }

    fun <T> input(inputName: String, withReturnOption: Boolean = true): T {
        print("Enter your $inputName: ")
        try {
            val input = readln() as T
            if (withReturnOption && input.toString() == "-1") {
                Routes.navigateBack()
            }

            return input
        } catch (e: Exception) {
            errorln("Invalid input")
            return input<T>(inputName)
        }
    }

    fun getUserOption(): Int {
        separator(15, '-', false)
        print("option: ")
        val option: Int
        try {
            option = readln().toInt()
        } catch (e: NumberFormatException) {
            println("invalid option, try again")
            return getUserOption()
        }
        if (option == -1) {
            Routes.navigateBack()
        }
        separator(25, '*')

        return option
    }
}