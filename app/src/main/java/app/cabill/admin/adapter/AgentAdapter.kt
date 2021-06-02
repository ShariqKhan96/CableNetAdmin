package app.cabill.admin.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.model.Agent
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.agent.CreateAgentActivity


class AgentAdapter(context: Context, l: ArrayList<Agent>) :
    RecyclerView.Adapter<AgentAdapter.MYVH>() {
    var con: Context = context
    var list: ArrayList<Agent> = l

    class MYVH(view: View) : RecyclerView.ViewHolder(view) {

        var view: TextView = view.findViewById(R.id.view)
        var agentName: TextView = view.findViewById(R.id.customer_name)
        var agentID: TextView = view.findViewById(R.id.agent_id)
        var phone: ImageView = view.findViewById<ImageView>(R.id.phone)
        var whatsapp: ImageView = view.findViewById<ImageView>(R.id.whatsapp)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.agent_row, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        holder.agentID.text = "Agent ID: ${list[position].id}"
        holder.agentName.text = "${list[position].name}"
        holder.view.setOnClickListener {
            con.startActivity(Intent(con, CreateAgentActivity::class.java).putExtra("action", 1))
        }
        holder.phone.setOnClickListener {
            Utils.getInstance().call(list[position].phone, con)
        }
        holder.whatsapp.setOnClickListener {
            Utils.getInstance().openWhatsapp(list[position].phone, con)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}