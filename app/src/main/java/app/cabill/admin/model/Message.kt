package app.cabill.admin.model

data class Message(
    val religion_id: Int, val title: String, val body: String, val type: String,
    val customers: ArrayList<Int>?, val agents: ArrayList<Int>?
) {
}