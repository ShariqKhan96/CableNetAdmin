package app.cabill.admin.view.ui.agent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import app.cabill.admin.R
import app.cabill.admin.adapter.AgentAdapter
import app.cabill.admin.databinding.ActivityAgentListBinding
import app.cabill.admin.databinding.ActivityCreatePackageBinding

class AgentListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgentListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgentListBinding.inflate(layoutInflater)
        binding.root.findViewById<TextView>(R.id.toolbarTv).text =
            "Manage Agent"
        binding.root.findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        setContentView(binding.root)

        binding.agents.apply {
            adapter = AgentAdapter()
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, CreateAgentActivity::class.java))
        }


    }
}