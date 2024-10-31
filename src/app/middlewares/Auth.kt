package app.middlewares

import app.DataSource
import app.Routes

class Auth : Middleware {
    override fun handle() {
        if (DataSource.session["user"] == null) {
            Routes.popView()
            Routes.navigate("login")
        }
    }

}