package app.cabill.admin.view.ui.auth2

import android.util.Patterns
import app.cabill.admin.util.Utils

class AuthValidationFactory {
    fun validateEmailAndPassword(email: String, password: String): String? {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Invalid Email Address"
        } else if (password.length < 6)
            "Password length should be greater than 6"
        else if (Utils.getInstance().anyFieldEmpty(arrayOf(email, password)))
            "Some field(s) are empty"
        else null
    }
}