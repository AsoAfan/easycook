package main

import app.Application
import app.Application.Companion.container
import app.Routes
import app.ValidationHandler

fun main() {


    boostrap()


}

fun boostrap() {

    container.bind("app") {
        Routes.defineRoutes()
        val app = Application()
        app
    }

    container.bind("validation") {
        ValidationHandler()
    }

    Routes.navigate("onBoarding")

}