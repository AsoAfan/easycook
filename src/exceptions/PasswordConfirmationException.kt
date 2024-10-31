package exceptions

import utills.errorln

class PasswordConfirmationException(msg: String = "passwords does not match", resolve: () -> Unit) {

    init {
        errorln(msg)
        resolve()
    }
}