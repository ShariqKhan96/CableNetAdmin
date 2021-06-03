package app.cabill.admin.view.ui.bill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import app.cabill.admin.R

class BillDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_detail)
        findViewById<TextView>(R.id.toolbarTv).text = "Bill Details"
        findViewById<TextView>(R.id.toolbarTv).setOnClickListener {
            finish()
        }
    }
}