package app.cabill.admin.view.ui.sublocality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreateAreaBinding
import app.cabill.admin.databinding.ActivitySublocalityBinding
import app.cabill.admin.util.Utils
import kotlinx.android.synthetic.main.agent_row.*

class SublocalityActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySublocalityBinding
    private lateinit var viewModel: SubLocalityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySublocalityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        binding.toolbar.toolbarTv.text = "Create Sublocality"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)
            .get(SubLocalityViewModel::class.java)
        viewModel.createSubLocalityObserver().observe(this, Observer { response ->
            if (response.status == "loading") {
                Utils.getInstance().showLoader(this, "Please wait...")
            } else if (response.status == "no_loading") {
                Utils.getInstance().dismissLoader()
                if (!response.error) {

                } else {
                    Utils.getInstance().showAlertDialog(this, response.message, "Error")
                }
            }
        })
    }
}