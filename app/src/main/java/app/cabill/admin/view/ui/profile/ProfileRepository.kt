package app.cabill.admin.view.ui.profile

import android.content.Context
import app.cabill.admin.model.Profile
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance

class ProfileRepository(val context: Context) {
    suspend fun getProfile(): retrofit2.Response<Response<Profile>> {
        return RetrofitInstance.client(context).getProfile()
    }

    suspend fun updateProfile(profile: Profile): retrofit2.Response<Response<Profile>> {
        return RetrofitInstance.client(context).updateProfile(profile)
    }
}