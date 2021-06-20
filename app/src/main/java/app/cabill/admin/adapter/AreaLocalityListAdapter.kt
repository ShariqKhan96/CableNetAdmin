package app.cabill.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.model.Area
import app.cabill.admin.model.SubLocality

class AreaLocalityListAdapter<T>(val list: ArrayList<T>, val context: Context) :
    RecyclerView.Adapter<AreaLocalityListAdapter.MYVH>() {
    class MYVH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(R.layout.area_locality_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        if (list[position] is Area) {
            val area: Area = list[position] as Area
            holder.name.text = area.name
        } else {
            val subLocality: SubLocality = list[position] as SubLocality
            holder.name.text = subLocality.name
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}