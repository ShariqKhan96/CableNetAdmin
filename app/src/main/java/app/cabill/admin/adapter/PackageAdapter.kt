package app.cabill.admin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.view.ui.packages.CreatePackageActivity

class PackageAdapter(context: Context) : RecyclerView.Adapter<PackageAdapter.MYVH>() {
    var con = context

    class MYVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewDetails = itemView.findViewById<TextView>(R.id.view_details)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(R.layout.package_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        holder.viewDetails.setOnClickListener {
            con.startActivity(Intent(con, CreatePackageActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}