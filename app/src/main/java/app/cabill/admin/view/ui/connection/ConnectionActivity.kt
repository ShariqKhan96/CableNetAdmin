package app.cabill.admin.view.ui.connection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.adapter.ConnectionAdapter
import app.cabill.admin.databinding.ActivityConnectionBinding
import app.cabill.admin.databinding.ActivityCreateConnectionBinding
import app.cabill.admin.databinding.ToolbarBinding
import app.cabill.admin.model.Connection
import app.cabill.admin.util.Utils

class ConnectionActivity : AppCompatActivity() {
    lateinit var binding: ActivityConnectionBinding
    lateinit var adapter: ConnectionAdapter
    lateinit var viewModel: ConnectionViewModel
    val list = ArrayList<Connection>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectionBinding.inflate(layoutInflater)
        initViewModel()
        setContentView(binding.root)
        binding.root.findViewById<TextView>(R.id.toolbarTv).text =
            "Connections"
        binding.root.findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            viewModel.list(this)
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, CreateConnectionActivity::class.java))
        }
        adapter = ConnectionAdapter(this, list)
        binding.connections.apply {
            adapter = this@ConnectionActivity.adapter
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)
            .get(ConnectionViewModel::class.java)
        viewModel.loaderLiveDataObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        viewModel.getConnectionListObserver().observe(this, Observer {
            if (it != null) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.list(this)
    }

}