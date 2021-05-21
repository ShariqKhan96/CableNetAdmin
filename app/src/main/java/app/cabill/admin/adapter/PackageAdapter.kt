package app.cabill.admin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.model.Package
import app.cabill.admin.view.ui.packages.CreatePackageActivity

class PackageAdapter(context: Context, val list: ArrayList<Package>) :
    RecyclerView.Adapter<PackageAdapter.MYVH>() {
    var con = context

    class MYVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewDetails = itemView.findViewById<TextView>(R.id.view_details)
        var package_name = itemView.findViewById<TextView>(R.id.package_name)
        var package_type = itemView.findViewById<TextView>(R.id.package_type)
        var package_amount = itemView.findViewById<TextView>(R.id.amount)
        var package_discount = itemView.findViewById<TextView>(R.id.discount)
        var package_speed = itemView.findViewById<TextView>(R.id.speed)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(R.layout.package_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        holder.package_amount.text = "Amount : ${list[position].amount} PKR"
        holder.package_discount.text = "Discount: ${list[position].discount}%"
        holder.package_name.text = list[position].name
        holder.package_type.text = list[position].package_type!!.name
        holder.viewDetails.setOnClickListener {
            con.startActivity(Intent(con, CreatePackageActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}