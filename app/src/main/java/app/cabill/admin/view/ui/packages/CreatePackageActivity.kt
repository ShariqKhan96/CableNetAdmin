package app.cabill.admin.view.ui.packages

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreatePackageBinding
import app.cabill.admin.model.NameID
import app.cabill.admin.model.Package
import app.cabill.admin.util.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_package.*

class CreatePackageActivity : AppCompatActivity() {
    var package_: Package? = null
    var categoryId: Int = 0
    var typeId: Int = 0
    var categoriesName: ArrayList<String> = ArrayList()
    var categoriesID: ArrayList<Int> = ArrayList()
    var typesName: ArrayList<String> = ArrayList()
    var typesID: ArrayList<Int> = ArrayList()


    fun show(array: ArrayList<String>, type: String) {


        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setItems(
            array.toTypedArray(),
            DialogInterface.OnClickListener { dialogInterface, i ->
                if (type == "type") {
                    activityCreatePackageBinding.type.setText(typesName[i])
                    typeId = typesID[i]
                } else {
                    activityCreatePackageBinding.cateogory.setText(categoriesName[i])
                    categoryId = categoriesID[i]
                }

                dialogInterface.dismiss()
            })
        builder.show()
    }


    private lateinit var activityCreatePackageBinding: ActivityCreatePackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreatePackageBinding = ActivityCreatePackageBinding.inflate(layoutInflater)
        setContentView(activityCreatePackageBinding.root)
        activityCreatePackageBinding.root.findViewById<TextView>(R.id.toolbarTv).text =
            "Create Package"
        activityCreatePackageBinding.root.findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        observeIntent()
        activityCreatePackageBinding.cateogory.setOnClickListener {
            show(categoriesName, "category")
        }
        activityCreatePackageBinding.type.setOnClickListener {
            show(typesName, "type")
        }
        initVm()

    }

    private fun initVm() {
        val vm = ViewModelProvider(this).get(PackageFragmentViewModel::class.java)
        vm.getLoaderObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        vm.getPackageCreateLiveDataObserver().observe(this, Observer { response ->
            if (!response.error) {
                Utils.getInstance().showAlertDialog(this, response.message, "Operation Successful")
            } else {
                Utils.getInstance().showAlertDialog(this, response.message, "Error")
            }
        })
        vm.getPackageUpdateObserver().observe(this, Observer { response ->
            if (!response.error) {
                Utils.getInstance().showAlertDialog(this, response.message, "Operation Successful")
            } else {
                Utils.getInstance().showAlertDialog(this, response.message, "Error")
            }
        })
        vm.catTypesObserver().observe(this, Observer { response ->
            for (i in response.data?.packageCategories?.indices!!) {
                categoriesName.add(response.data?.packageCategories!![i].name)
                categoriesID.add(response.data?.packageCategories!![i].id)
            }
            for (i in response.data?.packageTypes?.indices!!) {
                typesName.add(response.data?.packageTypes!![i].name)
                typesID.add(response.data?.packageTypes!![i].id)
            }
        })
        vm.categoriesAndTypes(this)

        activityCreatePackageBinding.save.setOnClickListener {
            if (package_ != null) {
                //edit
                package_!!.amount = activityCreatePackageBinding.amount.text.toString().toInt()
                package_!!.name = activityCreatePackageBinding.name.text.toString()
                package_!!.discount = activityCreatePackageBinding.discount.text.toString().toInt()
                package_!!.package_category_id = categoryId
                package_!!.package_type_id = typeId

                vm.updatePackage(package_!!,this)

            } else {
                //create
                vm.createPackage(
                    Package(
                        activityCreatePackageBinding.amount.text.toString().toInt(),
                        0,
                        "",
                        activityCreatePackageBinding.discount.text.toString().toInt(),
                        0,
                        1,
                        activityCreatePackageBinding.name.text.toString(),
                        categoryId, typeId, "", null, null
                    ),this
                )
            }
        }
    }

    private fun observeIntent() {
        if (intent.hasExtra("package")) {
            package_ = Gson().fromJson(
                intent.getStringExtra(
                    "package",
                ), Package::class.java
            )
            bind_()
            activityCreatePackageBinding.save.visibility = View.VISIBLE
            activityCreatePackageBinding.delete.visibility = View.VISIBLE
        } else {
            package_ = null
            activityCreatePackageBinding.save.visibility = View.VISIBLE
            activityCreatePackageBinding.delete.visibility = View.GONE
        }

    }

    private fun bind_() {
        activityCreatePackageBinding.name.setText(package_?.name)
        activityCreatePackageBinding.amount.setText(package_!!.amount.toString())
        activityCreatePackageBinding.discount.setText(package_!!.discount.toString())
        activityCreatePackageBinding.cateogory.setText(package_?.package_category!!.name)
        activityCreatePackageBinding.type.setText(package_?.package_type?.name)

        activityCreatePackageBinding.delete.visibility  =View.GONE
        activityCreatePackageBinding.save.visibility = View.GONE
        activityCreatePackageBinding.toolbar.toolbarTv.text = "Package"
    }
}