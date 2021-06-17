package app.cabill.admin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.model.InternetBill
import app.cabill.admin.view.ui.bill.BillDetailActivity
import com.google.gson.Gson

class BillAdapter<T>(context: Context, val list: ArrayList<T>) :
    RecyclerView.Adapter<BillAdapter.MYVH>() {

    var con: Context = context

    class MYVH(view: View) : RecyclerView.ViewHolder(view) {
        val cName = view.findViewById<TextView>(R.id.customer_name)
        val status = view.findViewById<TextView>(R.id.bill_status)
        val price = view.findViewById<TextView>(R.id.bill_price)
        val balance = view.findViewById<TextView>(R.id.bill_balance)
        val package_ = view.findViewById<TextView>(R.id.bill_package)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(LayoutInflater.from(parent.context).inflate(R.layout.bill_row, parent, false))
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        if (list[position] is InternetBill) {
            val bill = list[position] as InternetBill
            try {
                holder.balance.setText("Balance :${bill.balance} Rs")
                if (bill.status == null)
                    holder.status.visibility = View.GONE
                else holder.status.visibility = View.VISIBLE
                holder.status.setText("Status :${bill.status}")
                holder.cName.setText("Customer : ${bill.connection.customer.name}")
                holder.price.setText("Price : ${bill.connection.packageX.amount} Rs")
                holder.package_.setText(bill.connection.packageX.name + " " + bill.connection.packageX.package_type?.name)
                holder.itemView.setOnClickListener {
                    holder.itemView.context.startActivity(
                        Intent(
                            con,
                            BillDetailActivity::class.java
                        ).putExtra("internet_bill", Gson().toJson(bill))
                    )
                }
            } catch (e: Exception) {
                Toast.makeText(con, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        } else {

            //DO IT FOR CABLE LATER

            val bill = list[position] as InternetBill
            try {
                holder.balance.setText("Balance :${bill.balance} Rs")
                if (bill.status == null)
                    holder.status.visibility = View.GONE
                else holder.status.visibility = View.VISIBLE
                holder.status.setText("Status :${bill.status}")
                holder.cName.setText("Customer : ${bill.connection.customer.name}")
                holder.price.setText("Price : ${bill.connection.packageX.amount} Rs")
                holder.package_.setText(bill.connection.packageX.name + " " + bill.connection.packageX.package_type?.name)
                holder.itemView.setOnClickListener {
                    holder.itemView.context.startActivity(
                        Intent(
                            con,
                            BillDetailActivity::class.java
                        )
                    )
                }
            } catch (e: Exception) {
                Toast.makeText(con, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}