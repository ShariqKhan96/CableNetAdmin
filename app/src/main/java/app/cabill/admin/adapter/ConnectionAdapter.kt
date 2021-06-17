package app.cabill.admin.adapter

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.model.Connection
import app.cabill.admin.model.Customer
import app.cabill.admin.view.ui.customer.CustomerDetailActivity

class ConnectionAdapter(context: Context, val list: List<Connection>) :
    RecyclerView.Adapter<ConnectionAdapter.MYVH>() {
    var con = context

    class MYVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var view: TextView = itemView.findViewById(R.id.view_details)
        var customerNmae: TextView = itemView.findViewById(R.id.customer_name)
        var customerPackage: TextView = itemView.findViewById(R.id.customer_address)
        var packageName: TextView = itemView.findViewById(R.id.package_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(R.layout.connection_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {

        holder.customerNmae.text = "Customer: ${list[position].customer.name}"
        holder.customerPackage.text = "Amount: ${list[position].packageX.amount} Rs"
        holder.packageName.text = list[position].packageX.name
        holder.view.visibility = View.GONE
//        holder.view.setOnClickListener {
//            con.startActivity(Intent(con, CustomerDetailActivity::class.java))
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}