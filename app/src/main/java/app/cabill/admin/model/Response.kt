package app.cabill.admin.model

import java.sql.ClientInfoStatus

class Response<T>(var data: T?, val message: String, val error: Boolean, var status: String) {

}