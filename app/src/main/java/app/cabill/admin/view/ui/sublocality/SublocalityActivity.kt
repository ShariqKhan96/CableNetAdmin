package app.cabill.admin.view.ui.sublocality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreateAreaBinding
import app.cabill.admin.databinding.ActivitySublocalityBinding

class SublocalityActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySublocalityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySublocalityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.toolbarTv.text = "Create Sublocality"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
    }
}