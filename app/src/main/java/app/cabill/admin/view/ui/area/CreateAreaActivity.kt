package app.cabill.admin.view.ui.area

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreateAreaBinding
import app.cabill.admin.model.Area
import app.cabill.admin.util.Utils

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

        val vm = ViewModelProvider(this).get(AreaViewModel::class.java)
        binding.save.setOnClickListener {
            if (!binding.name.text.isNullOrEmpty()) {
                val area = Area(0, binding.name.text.toString(), "")
                vm.create(this, area)
            }
        }
        vm.loadingObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        vm.createAreaObserver().observe(this, Observer {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
    }
}