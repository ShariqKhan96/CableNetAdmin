package app.cabill.admin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.view.ui.agent.CreateAgentActivity

class AgentAdapter(context: Context) : RecyclerView.Adapter<AgentAdapter.MYVH>() {
    var con: Context = context

    class MYVH(view: View) : RecyclerView.ViewHolder(view) {

        var view: TextView = view.findViewById(R.id.view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        return MYVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.agent_row, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        holder.view.setOnClickListener {
            con.startActivity(Intent(con, CreateAgentActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}