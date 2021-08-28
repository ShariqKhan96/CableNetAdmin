package app.cabill.admin.view.ui.connection

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreateConnectionBinding
import app.cabill.admin.model.Customer
import app.cabill.admin.model.Package
import app.cabill.admin.model.Profile
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.profile.ProfileViewModel

class CreateConnectionActivity : AppCompatActivity() {
    lateinit var viewModel: ConnectionViewModel
    lateinit var binding: ActivityCreateConnectionBinding
    lateinit var profile: Profile
    val packageList = ArrayList<Package>()
    val customerList = ArrayList<Customer>()
    var packageID: Int = 0
    var packagePrice: Int = 0
    var customerId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        getProfile()

        binding.toolbar.toolbarTv.text = "Create Connection"
        binding.toolbar.back.setOnClickListener {
            finish()
        }

        binding.save.setOnClickListener {
            viewModel.create(profile.id, packageID,this)
        }

        binding.packageName.setOnClickListener {
            showDialog()
        }

        binding.name.setOnClickListener {
            showDialog2()
        }

    }

    private fun getProfile() {


        val profileViewModel = ViewModelProvider(this)
            .get(ProfileViewModel::class.java)
        profileViewModel.getProfile(this)
        profileViewModel.getProfileObserver().observe(this, Observer {
            profile = it.data!!
        })


    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val StringArrayList = ArrayList<String>()
        for (index in packageList.indices) {
            StringArrayList.add(packageList[index].name)
        }
        builder.setItems(
            StringArrayList.toTypedArray(),
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                packageID = packageList[i].id!!
                packagePrice = packageList[i].amount!!
                binding.packageName.setText(StringArrayList[i])
                binding.price.setText(packageList[i].amount.toString())
            })
        builder.show()
    }

    private fun showDialog2() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val StringArrayList = ArrayList<String>()
        for (index in customerList.indices) {
            StringArrayList.add(customerList[index].name)
        }
        builder.setItems(
            StringArrayList.toTypedArray(),
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
//                packageID = packageList[i].id
//                packagePrice = packageList[i].amount
//                binding.packageName.setText(StringArrayList[i])
//                binding.price.setText(packagePrice)
                binding.name.setText(customerList[i].name)
                customerId = customerList[i].id!!
            })
        builder.show()
    }

    private fun initViewModel() {


        viewModel = ViewModelProvider(this)
            .get(ConnectionViewModel::class.java)
        viewModel.loaderLiveDataObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()

        })

        viewModel.getCreateObserver().observe(this, Observer {
            if (it != null) {
                Toast.makeText(this, "Connection Created Successfully", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT)
                .show()
        })


        viewModel.getListPackageObserver().observe(this, Observer {
            if (it != null) {
                packageList.clear()
                customerList.clear()
                packageList.addAll(it.packages!!)
                customerList.addAll(it.customers!!)
            }
        })


        viewModel.listPackages(this)


    }
}