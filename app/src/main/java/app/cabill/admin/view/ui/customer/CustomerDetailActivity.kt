package app.cabill.admin.view.ui.customer

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCustomerDetailBinding
import app.cabill.admin.model.Agent
import app.cabill.admin.model.Customer
import app.cabill.admin.model.SulLocalitiesAreaReligions
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.map.PickLocationActivity
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList
import kotlinx.android.synthetic.main.agent_row.*
import java.util.*
import kotlin.collections.ArrayList

class CustomerDetailActivity : AppCompatActivity(),
    RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener<Any> {


    lateinit var viewModel: CustomerViewModel

    val REQUEST_CODE = 123
    var long: Double? = null
    var lat: Double? = null

    var action: Int = 0 ///0->create, 1->edit/delete
    var customer: Customer? = null
    lateinit var sulLocalitiesAreaReligions: SulLocalitiesAreaReligions

    var localityNameArray: ArrayList<String> = arrayListOf()
    var localityIdArray: ArrayList<Int> = arrayListOf()


    var subLocalityNameArray: ArrayList<String> = arrayListOf()
    var subLocalityIdArray: ArrayList<Int> = arrayListOf()


    var religionNameArray: ArrayList<String> = ArrayList()
    var religionIdArray: ArrayList<Int> = ArrayList()

    //fab
    private var rfaLayout: RapidFloatingActionLayout? = null
    private var rfaBtn: RapidFloatingActionButton? = null
    private var rfabHelper: RapidFloatingActionHelper? = null
        lateinit var binding: ActivityCustomerDetailBinding

        var subLocalityId: Int = 0
        var localityId: Int = 0
        var religionId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFAB(binding.root)
        intentDecider()
        bindData(customer)
        actions()
        initViewModel()

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)
            .get(CustomerViewModel::class.java)
        viewModel.getRelSubLoc(this)

        viewModel.loaderLiveDataObserver().observe(this, androidx.lifecycle.Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        viewModel.customerCreateLiveDataObserver().observe(this, androidx.lifecycle.Observer {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
        viewModel.getNecessaryDataObserver().observe(this, androidx.lifecycle.Observer {
            if (!it.error)
                sulLocalitiesAreaReligions = it.data!!

            for (i in sulLocalitiesAreaReligions.localities.indices) {
                localityNameArray.add(sulLocalitiesAreaReligions.localities[i].name)
                localityIdArray.add(sulLocalitiesAreaReligions.localities[i].id)
            }

            for (i in sulLocalitiesAreaReligions.religions.indices) {
                religionNameArray.add(sulLocalitiesAreaReligions.religions[i].name)
                religionIdArray.add(sulLocalitiesAreaReligions.religions[i].id)
            }

            if (customer != null) {
                //get position from locality id
                for (i in sulLocalitiesAreaReligions.localities.indices) {
                    if (localityIdArray[i] == customer?.localityId?.toInt()) {
                        localityId = localityIdArray[i]
                        binding.area.setText(localityNameArray[i])


                    }
                }
                for (index in sulLocalitiesAreaReligions.subLocalities.indices) {
                    if (sulLocalitiesAreaReligions.subLocalities[index].localityId == localityId) {
                        subLocalityNameArray.add(sulLocalitiesAreaReligions.subLocalities[index].name)
                        subLocalityIdArray.add(sulLocalitiesAreaReligions.subLocalities[index].id)
                    }
                }

                for (i in 0 until subLocalityIdArray.size) {

                    if (subLocalityIdArray[i] == customer?.subLocalityId?.toInt()) {
                        subLocalityId = subLocalityIdArray[i]
                        binding.subLocality.setText(subLocalityNameArray[i])
                        break
                    }

                }

                for (i in sulLocalitiesAreaReligions.religions.indices) {
                    if (religionIdArray[i] == customer?.religionId?.toInt()) {
                        religionId = religionIdArray[i]
                        binding.regligionEdt.setText(religionNameArray[i])
                    }
                }
            }


        })
    }

    private fun bindData(agent: Customer?) {
        if (customer == null)
            return
        binding.name.setText(agent?.name)
        binding.cnic.setText(agent?.cnic)
        binding.address.setText(agent?.address)
        binding.ownerEmailEdt.setText(agent?.email)
        binding.phone.setText(agent?.phone)
    }


    private fun showDialog(array: ArrayList<String>, type: String) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setItems(
            array.toTypedArray(),
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                when (type) {
                    "area" -> {
                        localityId = localityIdArray[i]
                        binding.area
                            .setText(localityNameArray[i])
                        subLocalityNameArray.clear()
                        subLocalityIdArray.clear()
                        for (index in sulLocalitiesAreaReligions.subLocalities.indices) {
                            if (sulLocalitiesAreaReligions.subLocalities[index].localityId == localityId) {
                                subLocalityNameArray.add(sulLocalitiesAreaReligions.subLocalities[index].name)
                                subLocalityIdArray.add(sulLocalitiesAreaReligions.subLocalities[index].id)
                            }
                        }
                    }
                    "sub" -> {
                        //find sub by name
                        subLocalityId = subLocalityIdArray[i]
                        binding.subLocality.setText(subLocalityNameArray[i])

                    }
                    else -> {
                        //find religion by id
                        religionId = religionIdArray[i]
                        binding.regligionEdt.setText(religionNameArray[i])
                    }
                }
            })
        builder.show()
    }

    private fun actions() {
        binding.toolbar.toolbarTv.text = "Create Customer"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
        binding.area.setOnClickListener {
            showDialog(localityNameArray, "area")
        }
        binding.subLocality.setOnClickListener {
            showDialog(subLocalityNameArray, "sub")
        }
        binding.regligionEdt.setOnClickListener {
            showDialog(religionNameArray, "rel")
        }
        binding.address.setOnClickListener {
            Dexter.withContext(this)
                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        if (Utils.isGPSEnabled(this@CustomerDetailActivity)) {
                            startActivityForResult(
                                Intent(
                                    this@CustomerDetailActivity,
                                    PickLocationActivity::class.java
                                ), REQUEST_CODE
                            )
                        } else {
                            Utils.getInstance().showAlertDialog(
                                this@CustomerDetailActivity,
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
        binding.save.setOnClickListener {
            if (customer == null) {
                //create
                val customer1: Customer = Customer(
                    binding.address.text.toString(),
                    binding.cnic.text.toString(),
                    1,
                    "",
                    binding.ownerEmailEdt.text.toString(),
                    0,
                    "",
                    "1",
                    lat.toString(),
                    localityId.toString(),
                    binding.name.text.toString(),
                    binding.phone.text.toString(),
                    religionId.toString(),
                    subLocalityId.toString(),
                    ""
                )
                viewModel.createCustomer(customer1,this)

            } else {
                //edit
            }
        }
    }

    private fun intentDecider() {
        action = intent.getIntExtra("action", 0)
        if (action == 0)
            binding.delete.visibility = View.GONE
        else binding.delete.visibility = View.VISIBLE



        customer = if (intent.hasExtra("customer")) {
            Gson().fromJson(intent.getStringExtra("customer"), Customer::class.java)
        } else
            null

        if (customer != null)
            binding.save.visibility = View.GONE
    }

    private fun initFAB(view: View) {
        val rfaContent = RapidFloatingActionContentLabelList(this)
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this)
        val items: MutableList<RFACLabelItem<Any>> = ArrayList()
        items.add(
            RFACLabelItem<Any>()
                .setLabel("Edit") //.setResId(R.drawable.ic_place_black_24dp)
                .setLabelColor(Color.parseColor("#2F4159"))
                .setIconNormalColor(Color.parseColor("#2F4159")) //   .setIconPressedColor(0xffbf360c)
                .setLabelSizeSp(16)
                .setWrapper(0)
        )
        items.add(
            RFACLabelItem<Any>()
                .setLabel("Delete") // .setResId(R.drawable.book)
                //    .setIconNormalColor(0xff4e342e)
                //  .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.parseColor("#2F4159"))
                .setIconNormalColor(Color.parseColor("#2F4159"))
                .setLabelSizeSp(16) //                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(context, 4)))
                .setWrapper(1)
        )
        rfaContent
            .setItems(items)
            .setIconShadowColor(-0x777778)
        rfabHelper = RapidFloatingActionHelper(
            this,
            binding.activityMainRfal,
            binding.activityMainRfab,
            rfaContent
        ).build()
    }


    override fun onRFACItemLabelClick(position: Int, item: RFACLabelItem<Any>?) {
        Toast.makeText(this, item!!.label, Toast.LENGTH_SHORT).show()
    }

    override fun onRFACItemIconClick(position: Int, item: RFACLabelItem<Any>?) {
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