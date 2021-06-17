package app.cabill.admin.view.ui.bill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityBillDetailBinding
import app.cabill.admin.model.CableBill
import app.cabill.admin.model.InternetBill
import com.google.gson.Gson

class BillDetailActivity : AppCompatActivity() {

    private lateinit var internetBill: InternetBill
    private lateinit var cableBill: CableBill

    private lateinit var binding: ActivityBillDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<TextView>(R.id.toolbarTv).text = "Bill Details"
        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        intentDecider()
    }

    private fun intentDecider() {
        if (intent.hasExtra("internet_bill")) {
            intent.getStringExtra("internet_bill")
                .also { internetBill = Gson().fromJson(it, InternetBill::class.java) }

            binding.customerName.setText(internetBill.connection.customer.name)
            binding.billBalance.setText(internetBill.balance.toString() + " Rs")
            binding.billPrice.setText("${internetBill.amount} Rs")
            binding.packageName.setText(internetBill.connection.packageX.name)
            binding.billIssuance.setText(internetBill.createdAt)
        }
    }
}