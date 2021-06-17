package app.cabill.admin.view.ui.bill

import android.content.Context
import app.cabill.admin.model.BillData
import app.cabill.admin.remote.RetrofitInstance
import retrofit2.Response

class BillRepository(val context: Context) {
    suspend fun bill(): Response<app.cabill.admin.model.Response<BillData>> {
        return RetrofitInstance.client(context).bills()
    }
}