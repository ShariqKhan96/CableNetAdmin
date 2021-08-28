package app.cabill.admin.view.ui.inventory

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.databinding.ActivityAddInventoryBinding
import app.cabill.admin.model.Inventory
import app.cabill.admin.util.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_inventory.*

class AddInventoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddInventoryBinding
    lateinit var viewModel: InventoryViewModel

    var nameArray: ArrayList<String> = arrayListOf()
    var idArray: ArrayList<Int> = arrayListOf()
    var typeNameArray: ArrayList<String> = arrayListOf("Router", "Switch")
    var typeIdArray: ArrayList<Int> = arrayListOf(1, 2)


    var modelId: Int = 0
    var typeId: Int = 0

    var inventory: Inventory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.toolbarTv.text = "Add Product"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
        checkAndBindIntentData()
        binding.model.setOnClickListener {
            showModels()
        }
        binding.type.setOnClickListener {
            showTypes()
        }
        observeViewModel()
        binding.save.setOnClickListener {


            if (binding.description.text.length < 25) {
                Toast.makeText(
                    this,
                    "Description should be greater than 25 characters",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (binding.price.text.isNullOrEmpty() || binding.name.text.isNullOrEmpty() || binding.stock.text.isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "Some fields are empty",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener

            }
            if (binding.save.text.equals("Save")) {
                inventory = Inventory(
                    binding.name.text.toString(),
                    null,
                    binding.type.text.toString(),
                    modelId,
                    binding.stock.text.toString().toInt(),
                    binding.price.text.toString().toInt(),
                    modelName = binding.model.text.toString(),
                    binding.description.text.toString()
                )
                viewModel.create(inventory!!, this)
            } else {
                inventory = Inventory(
                    binding.name.text.toString(),
                    inventory?.id,
                    binding.type.text.toString(),
                    modelId,
                    binding.stock.text.toString().toInt(),
                    binding.price.text.toString().toInt(),
                    modelName = binding.model.text.toString(),
                    binding.description.text.toString()
                )
                viewModel.update(inventory!!, this)
            }

        }
        //viewModel.dataForList(this)

        // observerIntent()
    }

    private fun observerIntent() {
        if (intent.hasExtra("inventory")) {
            inventory = Gson().fromJson(intent.getStringExtra("inventory"), Inventory::class.java)
            inventory.let {
                binding.stock.setText("${inventory!!.stock}")
                binding.price.setText("${inventory!!.price}")
                binding.name.setText("${inventory!!.name}")
                binding.description.setText("${inventory!!.description}")
            }
            binding.save.setText("Update")
        } else binding.save.setText("Save")
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)
            .get(InventoryViewModel::class.java)

        viewModel.update.observe(this, Observer {
            Toast.makeText(this, "Inventory updated successfully!", Toast.LENGTH_SHORT).show()
            finish()
        })
        viewModel.getCreateObserver().observe(this, Observer {
            Toast.makeText(this, "Inventory created successfully!", Toast.LENGTH_SHORT).show()
            finish()
        })
        viewModel.loadingObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        viewModel.modelList.observe(this, Observer {
            for (index in it.models.indices) {
                nameArray.add(it.models[index].name)
                idArray.add(it.models[index].id)
            }
        })
    }

    private fun showTypes() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setItems(
            typeNameArray.toTypedArray(),
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                typeId = typeIdArray[i]
                binding.type.setText(typeNameArray[i])
            })
            .show()
    }

    private fun showModels() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setItems(
            nameArray.toTypedArray(),
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                modelId = idArray[i]
                binding.model.setText(nameArray[i])
            })
            .show()
    }

    private fun checkAndBindIntentData() {
        if (intent.hasExtra("inventory")) {

            inventory = Gson().fromJson(intent.getStringExtra("inventory"), Inventory::class.java)
            inventory?.let {
                binding.name.setText(it.name)
                binding.price.setText(it.price.toString())
                //binding.model.setText(it.modelName)
                // binding.type.setText(it.type)
                binding.stock.setText(it.stock.toString())
                binding.description.setText(it.description)
            }
            binding.toolbar.toolbarTv.text = "Update Product"

            binding.save.setText("Update")
        } else {
            binding.toolbar.toolbarTv.text = "Add Product"
            binding.save.setText("Save")
            return
        }

    }
}