package app.cabill.admin.view.ui.area

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreateAreaBinding

class CreateAreaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAreaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.toolbarTv.text = "Create Area"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
    }
}