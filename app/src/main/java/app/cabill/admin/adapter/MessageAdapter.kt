package app.cabill.admin.adapter

import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R

class MessageAdapter() : RecyclerView.Adapter<MessageAdapter.MYVH>() {
    class MYVH(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(R.layout.message_list_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
    }

    override fun getItemCount(): Int {
        return 10
    }
}