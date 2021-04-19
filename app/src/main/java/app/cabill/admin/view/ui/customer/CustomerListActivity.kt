package app.cabill.admin.view.ui.customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import app.cabill.admin.R
import app.cabill.admin.adapter.ConnectionAdapter
import app.cabill.admin.databinding.ActivityCustomerListBinding

class CustomerListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.findViewById<TextView>(R.id.toolbarTv).text =
            "My Connections"
        binding.root.findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        binding.customers.apply {
            adapter = ConnectionAdapter()
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, CustomerDetailActivity::class.java))
        }
    }
}