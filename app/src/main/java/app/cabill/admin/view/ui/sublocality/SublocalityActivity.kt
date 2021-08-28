package app.cabill.admin.view.ui.sublocality

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreateAreaBinding
import app.cabill.admin.databinding.ActivitySublocalityBinding
import app.cabill.admin.model.Area
import app.cabill.admin.model.NameID
import app.cabill.admin.model.SubLocality
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.area.AreaViewModel
import kotlinx.android.synthetic.main.agent_row.*

class SublocalityActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySublocalityBinding
    private lateinit var viewModel: SubLocalityViewModel
    private var areaID: Int = 0
    var list = ArrayList<Area>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySublocalityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        binding.toolbar.toolbarTv.text = "Create Sublocality"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
        binding.area.setOnClickListener {
            val list_ = ArrayList<String>()
            for (indices in list.indices)
                list_.add(list[indices].name!!)
            showDialog(list_)
        }

        binding.save.setOnClickListener {
            if (!binding.name.text.isEmpty()) {

                if (binding.name.text.toString().isEmpty() || areaID == 0) {
                    Utils.getInstance().showAlertDialog(
                        this,
                        "Some fields are empty. Please fill the form completely.",
                        ""
                    )
                    return@setOnClickListener
                }

                val sublocality = SubLocality(0, binding.name.text.toString(), "", "", areaID)
                viewModel.createSubLocalityAPI(sublocality, this)

            }
        }
    }

    private fun showDialog(array: ArrayList<String>) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setItems(
            array.toTypedArray(),
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()

                //find religion by id
                areaID = list[i].id
                binding.area.setText(list[i].name)
                //binding.regligionEdt.setText(religionNameArray[i])


            })
        builder.show()
    }


    private fun initViewModel() {

        val vm = ViewModelProvider(this)
            .get(AreaViewModel::class.java)
        vm.list(this)
        vm.areaListObserver().observe(this, Observer {
            list.addAll(it.data!!)
        })

        viewModel = ViewModelProvider(this)
            .get(SubLocalityViewModel::class.java)
        viewModel.loadingObserver().observe(this, Observer {
            if (it) Utils.getInstance().showLoader(this, "Please wait..")
            else Utils.getInstance().dismissLoader()
        })
        viewModel.createSubLocalityObserver().observe(this, Observer { response ->
            if (response.status == "loading") {
                Utils.getInstance().showLoader(this, "Please wait...")
            } else if (response.status == "no_loading") {
                Utils.getInstance().dismissLoader()
                if (!response.error) {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                } else {
                    Utils.getInstance().showAlertDialog(this, response.message, "Error")
                }
            }
        })
    }
}