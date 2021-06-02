package app.cabill.admin.view.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityLoginBinding
import app.cabill.admin.util.Utils
import kotlinx.android.synthetic.main.agent_row.*

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        binding.login.setOnClickListener {
            viewModel.login(binding.email.text.toString(), binding.password.text.toString())
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModel.getLoginObserver().observe(this, Observer { response ->

        })
        viewModel.loaderObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Authenticating...")
            else Utils.getInstance().dismissLoader()
        })
        viewModel.validationObserver().observe(this, Observer {
            Utils.getInstance().showAlertDialog(this, it, "Error")
        })
    }

}