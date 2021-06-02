package app.cabill.admin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.model.Customer
import app.cabill.admin.view.ui.customer.CustomerDetailActivity
import com.google.gson.Gson

class CustomerAdapter(context: Context, val list: List<Customer>) :
    RecyclerView.Adapter<CustomerAdapter.MYVH>() {
    var con = context

    class MYVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var view: TextView = itemView.findViewById(R.id.view_details)
        val customerName = itemView.findViewById<TextView>(R.id.customer_name)
        val customerAddress = itemView.findViewById<TextView>(R.id.customer_address)
        val agent = itemView.findViewById<TextView>(R.id.customer_agent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(R.layout.customer_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        holder.customerAddress.text = "Address: " + list[position].address
        holder.customerName.text = "Customer: ${list[position].name}"
      //  holder.agent.text = "Agent: ${list[position].}"
        holder.view.setOnClickListener {
            con.startActivity(
                Intent(con, CustomerDetailActivity::class.java).putExtra(
                    "customer",
                    Gson().toJson(list[position])
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}