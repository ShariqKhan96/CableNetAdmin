package app.cabill.admin.view.ui.connection

import android.content.Context
import app.cabill.admin.model.Connection
import app.cabill.admin.model.Package
import app.cabill.admin.model.PackageCustomerObject
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance

class ConnectionRepository {

    suspend fun create(cId: Int, pId: Int,context: Context): Response<Connection> {
        return RetrofitInstance.client(context).createConnection(cId, pId,1)
    }

    suspend fun list(context: Context): Response<List<Connection>> {
        return RetrofitInstance.client(context).getConnections()
    }

    suspend fun listPackages(context: Context): PackageCustomerObject {
        return RetrofitInstance.client(context).connectionPackages().data!!
    }
}