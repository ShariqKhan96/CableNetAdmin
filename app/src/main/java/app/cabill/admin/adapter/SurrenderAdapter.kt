package app.cabill.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.interfaces.ClickListener
import app.cabill.admin.model.Surrender
import org.w3c.dom.Text

class SurrenderAdapter(
    val context: Context,
    val list: List<Surrender>,
    val listener: ClickListener
) :
    RecyclerView.Adapter<SurrenderAdapter.MYVH>() {
    inner class MYVH(view: View) : RecyclerView.ViewHolder(view) {
        val amount: TextView = view.findViewById(R.id.amount)
        val surrender: Button = view.findViewById(R.id.surrender)
        val name: TextView = view.findViewById(R.id.package_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(R.layout.surrender_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        holder.name.text = list[position].name
        holder.amount.text = "Collected: ${list[position].recovered_amount} Rs"
        if (list[position].recovered_amount == 0)
            holder.surrender.visibility = View.GONE
        else holder.surrender.visibility = View.VISIBLE
        holder.surrender.setOnClickListener {
            listener.onClick(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}