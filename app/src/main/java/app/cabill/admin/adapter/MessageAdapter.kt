package app.cabill.admin.adapter

import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R

class MessageAdapter(val list: List<app.cabill.admin.model.Message>) :
    RecyclerView.Adapter<MessageAdapter.MYVH>() {
    class MYVH(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title)
        val message = view.findViewById<TextView>(R.id.message)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(R.layout.message_list_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        holder.title.text = list[position].title
        holder.message.text = list[position].body
    }

    override fun getItemCount(): Int {
        return list.size
    }
}