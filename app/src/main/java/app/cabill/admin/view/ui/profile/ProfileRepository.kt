package app.cabill.admin.view.ui.profile

import app.cabill.admin.model.Profile
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance

class ProfileRepository {
    suspend fun getProfile(): Response<Profile> {
        return RetrofitInstance.client().getProfile()
    }
}