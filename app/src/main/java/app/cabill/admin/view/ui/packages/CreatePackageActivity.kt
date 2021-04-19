package app.cabill.admin.view.ui.packages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreatePackageBinding

class CreatePackageActivity : AppCompatActivity() {
    private lateinit var activityCreatePackageBinding: ActivityCreatePackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreatePackageBinding = ActivityCreatePackageBinding.inflate(layoutInflater)
        activityCreatePackageBinding.root.findViewById<TextView>(R.id.toolbarTv).text =
            "Create Package"
        activityCreatePackageBinding.root.findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        setContentView(activityCreatePackageBinding.root)
    }
}