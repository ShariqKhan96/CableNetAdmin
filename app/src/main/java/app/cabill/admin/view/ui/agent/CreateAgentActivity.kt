package app.cabill.admin.view.ui.agent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.databinding.ActivityCreateAgentBinding
import app.cabill.admin.model.Agent
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.map.PickLocationActivity
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class CreateAgentActivity : AppCompatActivity() {
    val REQUEST_CODE = 123
    var long: Double? = null
    var lat: Double? = null

    var action: Int = 0 ///0->create, 1->edit/delete
    var agent: Agent? = null
    val religionID: Int = 1

    lateinit var viewModel: CreateAgentViewModel
    lateinit var binding: ActivityCreateAgentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAgentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intentDecider()
        bindData(agent)
        initViewModel()
        actions()


    }


    private fun intentDecider() {
        action = intent.getIntExtra("action", 0)
        if (action == 0)
            binding.delete.visibility = View.GONE
        else binding.delete.visibility = View.VISIBLE

        agent = if (intent.hasExtra("agent")) {
            Gson().fromJson(intent.getStringExtra("agent"), Agent::class.java)
        } else
            null

        if (agent != null)
            binding.save.visibility = View.GONE
    }

    private fun actions() {
        binding.toolbar.toolbarTv.text = "Create Agent"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
        binding.address.setOnClickListener {
            Dexter.withContext(this)
                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        if (Utils.isGPSEnabled(this@CreateAgentActivity)) {
                            startActivityForResult(
                                Intent(
                                    this@CreateAgentActivity,
                                    PickLocationActivity::class.java
                                ), REQUEST_CODE
                            )
                        } else {
                            Utils.getInstance().showAlertDialog(
                                this@CreateAgentActivity,
                                "Please enable your GPS location from settings to continue",
                                "Enable GPS"
                            )
                        }

                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        p1?.continuePermissionRequest()
                    }

                }).check()
        }
    }

    private fun bindData(agent: Agent?) {
        if (agent == null)
            return
        binding.nameEdt.setText(agent?.name)
        binding.cnicEdt.setText(agent?.cnic)
        binding.address.setText(agent?.address)
        binding.ownerEmailEdt.setText(agent?.email)
        binding.phoneEdt.setText(agent?.phone)
        binding.salary.setText(agent?.salary)
        binding.regligionEdt.setText(agent?.religion_id.toString())
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreateAgentViewModel::class.java)
        viewModel.createLoaderObserver().observe(this, Observer { show ->
            if (show)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        viewModel.createAgentObserver().observe(this, Observer { response ->
            if (response!=null){
                if (!response.error) {
                    Utils.getInstance()
                        .showAlertDialog(
                            this,
                            "Agent created with ID ${response.data?.id}",
                            "Operation Successful"
                        )
                } else {
                    Utils.getInstance()
                        .showAlertDialog(this, response.message, "Error")
                }

            }else{
                Utils.getInstance()
                    .showAlertDialog(this, "Something went wrong.", "Error")

            }

        })
        viewModel.updateAgentObserver().observe(this, Observer { response ->
            if (!response.error) {
                Utils.getInstance()
                    .showAlertDialog(
                        this,
                        "Agent updated with ID ${response.data?.id}",
                        "Operation Successful"
                    )
            } else {
                Utils.getInstance()
                    .showAlertDialog(this, response.message, "Error")
            }
        })

        binding.save.setOnClickListener {

            if (action == 0) {
                viewModel.create(
                    Agent(
                        binding.nameEdt.text.toString(),
                        binding.ownerEmailEdt.text.toString(),
                        null,
                        binding.phoneEdt.text.toString(),
                        binding.cnicEdt.text.toString(),
                        binding.address.text.toString(),
                        long!!,
                        lat!!,
                        1,
                        1,
                        null,
                        binding.salary.text.toString()
                    )
                )
            } else {
                agent?.name = binding.nameEdt.text.toString()
                agent?.address = binding.address.text.toString()
                agent?.email = binding.ownerEmailEdt.text.toString()
                agent?.phone = binding.phoneEdt.text.toString()
                agent?.cnic = binding.cnicEdt.text.toString()
                agent?.longitude = long!!
                agent?.latitude = lat!!
                agent?.salary = binding.salary.text.toString()

                viewModel.update(agent, agent?.id!!)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                long = data?.getDoubleExtra("longitude", 0.0)
                lat = data?.getDoubleExtra("latitude", 0.0)
                val address = data?.getStringExtra("address")
                binding.address.setText(address)
            }
        }
    }
}