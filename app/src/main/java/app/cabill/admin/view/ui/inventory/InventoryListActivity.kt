package app.cabill.admin.view.ui.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.adapter.InventoryAdapter
import app.cabill.admin.databinding.ActivityInventoryListBinding
import app.cabill.admin.model.Inventory
import app.cabill.admin.util.Utils

class InventoryListActivity : AppCompatActivity() {
    lateinit var binding: ActivityInventoryListBinding
    lateinit var viewModel: InventoryViewModel
    var adapter: InventoryAdapter? = null
    val list: ArrayList<Inventory> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.toolbarTv.text = "Inventory Products"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
        listeners()
        observeViewModel()
        adapter = InventoryAdapter(list, this)
        binding.inventoryRv.apply {
            adapter = this@InventoryListActivity.adapter
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddInventoryActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.list(this)

    }
    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)
            .get(InventoryViewModel::class.java)
        viewModel.getListObserver().observe(this, Observer {
            list.clear()
            list.addAll(it)
            adapter?.notifyDataSetChanged()
        })
        viewModel.loadingObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })

    }


    private fun listeners() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = false
        }
    }
}