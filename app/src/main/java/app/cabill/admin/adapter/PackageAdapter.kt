package app.cabill.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R

class PackageAdapter() : RecyclerView.Adapter<PackageAdapter.MYVH>() {
    class MYVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(R.layout.package_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
    }

    override fun getItemCount(): Int {
        return 10
    }
}