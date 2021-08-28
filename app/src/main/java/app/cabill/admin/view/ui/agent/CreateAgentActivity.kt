package app.cabill.admin.view.ui.agent

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.databinding.ActivityCreateAgentBinding
import app.cabill.admin.model.Agent
import app.cabill.admin.model.SulLocalitiesAreaReligions
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.customer.CustomerViewModel
import app.cabill.admin.view.ui.map.PickLocationActivity
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class CreateAgentActivity : AppCompatActivity() {
    private lateinit var sulLocalitiesAreaReligions: SulLocalitiesAreaReligions
    val REQUEST_CODE = 123
    var long: Double? = 0.0
    var lat: Double? = 0.0

    var action: Int = 0 ///0->create, 1->edit/delete
    var agent: Agent? = null
    var religionNameArray: ArrayList<String> = ArrayList()
    var religionIdArray: ArrayList<Int> = ArrayList()

    var religionId: Int = 0

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
        binding.regligionEdt.setOnClickListener {
            showDialog(religionNameArray, "rel")
        }


    }

    private fun showDialog(array: ArrayList<String>, type: String) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setItems(
            array.toTypedArray(),
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()

                //find religion by id
                religionId = religionIdArray[i]
                binding.regligionEdt.setText(religionNameArray[i])


            })
        builder.show()
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

        binding.save.visibility = View.GONE
        binding.delete.visibility = View.GONE

        binding.toolbar.toolbarTv.text = "Agent"
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreateAgentViewModel::class.java)
        viewModel.createLoaderObserver().observe(this, Observer { show ->
            if (show)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        viewModel.createAgentObserver().observe(this, Observer { response ->
            if (response != null) {
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

            } else {
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


        val vm = ViewModelProvider(this)
            .get(CustomerViewModel::class.java)
        vm.getNecessaryDataObserver().observe(this, androidx.lifecycle.Observer {
            if (!it.error)
                sulLocalitiesAreaReligions = it.data!!



            for (i in sulLocalitiesAreaReligions.religions.indices) {
                religionNameArray.add(sulLocalitiesAreaReligions.religions[i].name)
                religionIdArray.add(sulLocalitiesAreaReligions.religions[i].id)
            }

            for (i in sulLocalitiesAreaReligions.religions.indices) {
                if (religionIdArray[i] == agent?.religion_id) {
                    religionId = religionIdArray[i]
                    binding.regligionEdt.setText(religionNameArray[i])
                }
            }
        })
        vm.getRelSubLoc(this)



        binding.save.setOnClickListener {

            if (action == 0) {

                if (binding.nameEdt.text.toString()
                        .isEmpty() || binding.ownerEmailEdt.text.toString().isEmpty() ||
                    binding.phoneEdt.text.toString().isEmpty() || binding.cnicEdt.text.toString()
                        .isEmpty() ||
                    binding.address.text.toString().isEmpty() || binding.salary.text.toString()
                        .isEmpty()
                ) {
                    Utils.getInstance().showAlertDialog(
                        this,
                        "Some fields are empty. Please fill the form completely.",
                        ""
                    )
                    return@setOnClickListener
                }
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
                    ), this
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

                viewModel.update(agent, agent?.id!!, this)
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