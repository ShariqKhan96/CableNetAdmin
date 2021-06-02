package app.cabill.admin.view.ui.customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.adapter.ConnectionAdapter
import app.cabill.admin.adapter.CustomerAdapter
import app.cabill.admin.databinding.ActivityCustomerListBinding
import app.cabill.admin.model.Customer
import app.cabill.admin.util.Utils
import app.cabill.admin.util.ViewProgressDialog
import app.cabill.admin.view.ui.map.MapsActivity
import kotlinx.android.synthetic.main.agent_row.*

class CustomerListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerListBinding
    private lateinit var adapter: CustomerAdapter
    private lateinit var viewModel: CustomerViewModel
    private val list: ArrayList<Customer> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            viewModel.getList()
        }
        listenBind()
        initViewModel()
        adapter = CustomerAdapter(this, list)
        binding.customers.apply {
            adapter = this@CustomerListActivity.adapter
        }


    }

    private fun listenBind() {
        binding.root.findViewById<TextView>(R.id.toolbarTv).text =
            "My Customers"
        binding.root.findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, CustomerDetailActivity::class.java))
        }
        binding.mapView.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)
            .get(CustomerViewModel::class.java)
        viewModel.loaderLiveDataObserver().observe(this, { response ->
            if (response)
                Utils.getInstance().showLoader(this, "Loading...")
            else Utils.getInstance().dismissLoader()

        })
        viewModel.customerListLiveDataObserver().observe(this, { response ->
            if (!response.error) {
                list.clear()
                list.addAll(response?.data!!)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.getList()
    }

    override fun onStop() {
        super.onStop()
    }
}