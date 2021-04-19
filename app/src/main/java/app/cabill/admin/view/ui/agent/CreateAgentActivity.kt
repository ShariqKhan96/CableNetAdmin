package app.cabill.admin.view.ui.agent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreateAgentBinding

class CreateAgentActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateAgentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAgentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.toolbarTv.text = "Create Agent"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
    }
}