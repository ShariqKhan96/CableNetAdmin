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
    lateinit var categories: Array<NameID>
    lateinit var types: Array<NameID>


    fun show(type: String) {
        val array: Array<String>
        if (type == "type") {
            array = Array(types.size) { "" }
            val i: Int = 0
            for (va in types) {
                array[i] = va.name
            }
        } else {
            array = Array(categories.size) { "" }
            val i: Int = 0
            for (va in categories) {
                array[i] = va.name
            }
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setItems(array, DialogInterface.OnClickListener { dialogInterface, i ->
            if (type == "type") {
                activityCreatePackageBinding.type.setText(types[i].name)
                typeId = get_(type, types[i].name)
            } else {
                activityCreatePackageBinding.type.setText(categories[i].name)
                categoryId = get_(type, categories[i].name)
            }

            dialogInterface.dismiss()
        })
    }

    fun get_(type: String, name: String): Int {
        var id: Int = 0
        if (type == "type") {
            for (n_ in types) {
                if (n_.name == name) {
                    id = n_.id
                }
            }
        } else {
            for (n_ in categories) {
                if (n_.name == name) {
                    id = n_.id
                }
            }
        }
        return id
    }

    private lateinit var activityCreatePackageBinding: ActivityCreatePackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreatePackageBinding = ActivityCreatePackageBinding.inflate(layoutInflater)
        setContentView(activityCreatePackageBinding.root)
        observeIntent()
        activityCreatePackageBinding.root.findViewById<TextView>(R.id.toolbarTv).text =
            "Create Package"
        activityCreatePackageBinding.root.findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        activityCreatePackageBinding.cateogory.setOnClickListener {
            show("type")
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
            categories = response.data.packageCategories.toTypedArray()
            types = response.data.packageTypes.toTypedArray()
        })
        vm.categoriesAndTypes()

        activityCreatePackageBinding.save.setOnClickListener {
            if (package_ != null) {
                //edit
                package_!!.amount = activityCreatePackageBinding.amount.text.toString().toInt()
                package_!!.name = activityCreatePackageBinding.name.text.toString()
                package_!!.discount = activityCreatePackageBinding.discount.text.toString().toInt()
                package_!!.packageCategoryId = categoryId
                package_!!.packageTypeId = typeId

                vm.updatePackage(package_!!)

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
                    )
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
        activityCreatePackageBinding.amount.setText(package_!!.amount)
        activityCreatePackageBinding.discount.setText(package_!!.discount)
        activityCreatePackageBinding.cateogory.setText(package_?.package_category!!.name)
        activityCreatePackageBinding.type.setText(package_?.package_type?.name)
    }
}