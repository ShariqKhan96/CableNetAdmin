package app.cabill.admin.model

import java.sql.ClientInfoStatus

class Response<T>(var data: T?, var message: String, var error: Boolean, var status: String) {

}