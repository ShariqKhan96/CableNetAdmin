package app.cabill.admin.view.ui.agent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.adapter.AgentAdapter
import app.cabill.admin.databinding.ActivityAgentListBinding
import app.cabill.admin.databinding.ActivityCreatePackageBinding
import app.cabill.admin.model.Agent
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.map.MapsActivity

class AgentListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgentListBinding
    private var list: List<Agent> = arrayListOf()
    private lateinit var adapter: AgentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        binding.root.findViewById<TextView>(R.id.toolbarTv).text =
            "Manage Agent"
        binding.root.findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        adapter = AgentAdapter(this@AgentListActivity, list)
        binding.agents.apply {
            adapter = this@AgentListActivity.adapter
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, CreateAgentActivity::class.java).putExtra("action", 0))
        }
        binding.mapView.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }


    }

    private fun initViewModel() {
        val vm = ViewModelProvider(this).get(AgentListViewModel::class.java)
        vm.getLoaderObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })

        vm.getAgentListObserver().observe(this, Observer {
            if (!it.error) {
                list.toMutableList().addAll(it.data)
                adapter.notifyDataSetChanged()
            } else {
                Utils.getInstance().showAlertDialog(this, it.message, "Error")
            }
        })
        vm.callAPI()
    }
}