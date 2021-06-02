package app.cabill.admin.model

data class Agent(
    var name: String,
    var email: String,
    var password: String?,
    var phone: String,
    var cnic: String,
    var address: String,
    var longitude: Double,
    var latitude: Double,
    var religion_id: Int,
    var is_active: Int,
    var id:Int?,
    var salary:String
)
