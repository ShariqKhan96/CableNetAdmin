package app.cabill.admin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.view.ui.bill.BillDetailActivity

class BillAdapter(context: Context) : RecyclerView.Adapter<BillAdapter.MYVH>() {

    var con: Context = context

    class MYVH(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(LayoutInflater.from(parent.context).inflate(R.layout.bill_row, parent, false))
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(Intent(con, BillDetailActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}