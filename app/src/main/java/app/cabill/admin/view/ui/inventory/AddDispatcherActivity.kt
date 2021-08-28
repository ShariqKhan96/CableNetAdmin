package app.cabill.admin.view.ui.inventory

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityAddDispatcherBinding
import app.cabill.admin.model.Agent
import app.cabill.admin.model.Inventory
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.agent.AgentListViewModel

class AddDispatcherActivity : AppCompatActivity() {

    val inventoryViewModel: InventoryViewModel by viewModels()
    val agentViewModel: AgentListViewModel by viewModels()
    var itemId: Int = 0
    var agentId: Int = 0
    var agentNameArray: ArrayList<String> = ArrayList()
    var agentIdArray: ArrayList<Int> = ArrayList()
    var list: ArrayList<Inventory> = arrayListOf()

    var productNameArray: ArrayList<String> = ArrayList()
    var productIdArray: ArrayList<Int> = ArrayList()
    var selectedStock = 0

    lateinit var bindingAddDispatcherBinding: ActivityAddDispatcherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAddDispatcherBinding = ActivityAddDispatcherBinding.inflate(layoutInflater)
        setContentView(bindingAddDispatcherBinding.root)
        agentViewModel.callAPI(this)
        agentViewModel.getAgentListObserver().observe(this, Observer {
            for (i in it.data!!.indices) {
                agentNameArray.add(it.data!![i].name)
                agentIdArray.add(it.data!![i].id!!)
            }

        })
        bindingAddDispatcherBinding.toolbar.toolbarTv.text = " Dispatch"
        bindingAddDispatcherBinding.toolbar.back.setOnClickListener {
            finish()
        }
        inventoryViewModel.list(this)
        inventoryViewModel.getListObserver().observe(this, Observer {
            for (i in it.indices) {
                productNameArray.add(it[i].name)
                productIdArray.add(it[i].id!!)
            }
            list.addAll(it!!)

        })

        inventoryViewModel.loadingObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        bindingAddDispatcherBinding.product.setOnClickListener {
            showDialog(productNameArray, "product")
        }

        bindingAddDispatcherBinding.name.setOnClickListener {
            showDialog(agentNameArray, "agent")
        }

        inventoryViewModel.dispatchItem.observe(this, Observer {
            Toast.makeText(this, "Items has been dispatched", Toast.LENGTH_LONG).show()
            finish()
        })
        bindingAddDispatcherBinding.save.setOnClickListener {

            if (bindingAddDispatcherBinding.description.text.isNullOrEmpty() || agentId == 0 ||
                itemId == 0 || bindingAddDispatcherBinding.stock.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Some fields are empty. Please fill the form completely.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (bindingAddDispatcherBinding.stock.text.toString().toInt() <= selectedStock)


                inventoryViewModel.dispatchItem(
                    this,
                    agentId,
                    bindingAddDispatcherBinding.stock.text.toString().toInt(),
                    bindingAddDispatcherBinding.description.text.toString(),
                    itemId
                )
            else Utils.getInstance().showAlertDialog(
                this,
                "Maximum ${selectedStock} Stock available for this product",
                "Stock Error"
            )
        }

    }

    private fun showDialog(array: ArrayList<String>, type: String) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setItems(
            array.toTypedArray(),
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()

                //find religion by id
                if (type == "agent") {
                    agentId = agentIdArray[i]
                    bindingAddDispatcherBinding.name.setText(agentNameArray[i])
                } else {
                    itemId = productIdArray[i]
                    bindingAddDispatcherBinding.product.setText(productNameArray[i])
                    selectedStock = list[i].stock
                }


            })
        builder.show()
    }

}