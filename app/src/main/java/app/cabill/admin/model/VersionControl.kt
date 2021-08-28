package app.cabill.admin.model

class VersionControl(val ACCESS_APPROVAL: String, val AGENT_VERSION: Int, val ADMIN_VERSION: Int) {
    constructor() : this("", 0, 0)
}