package app.cabill.admin.view.ui.messaging

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreateMessageBinding
import app.cabill.admin.model.NameID
import app.cabill.admin.model.Template

interface SelectDismissListener {
    fun onSelected(position: Int)
}

class CreateMessageActivity : AppCompatActivity(), SelectDismissListener {
    private val mUserItemsAgent: List<String> = emptyList()
    private val mUserItemsCustomer: List<String> = emptyList()
    private val iUserItemsAgent: List<Int> = emptyList()
    private val iUserItemsCustomer: List<Int> = emptyList()
    lateinit var selectDismissListener: SelectDismissListener
    private val agentList: List<NameID> = listOf(
        NameID("Shariq", 1), NameID("Saqib", 2),
        NameID("Ali", 3), NameID("Hassan", 4)
    )
    private val customerList: List<NameID> = listOf(
        NameID("Hussain", 1), NameID("Hassan", 2),
        NameID("Haider", 3), NameID("Adnan", 4)
    )
    private val templateList: List<Template> =
        listOf(Template("Independance Day", "This is test message", 1))
    var count = 0
    lateinit var templatesRv: RecyclerView
    var checkedItem: Int = 0
    lateinit var selectedTemplate: Template
    lateinit var templateDialog: AlertDialog

    private var templates: Array<CharSequence> = Array(3) { "" }


    private lateinit var checkedItemsAgent: BooleanArray
    private lateinit var checkedItemsCustomer: BooleanArray

    private lateinit var binding: ActivityCreateMessageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectDismissListener = this
        checkedItemsAgent = BooleanArray(agentList.size)
        checkedItemsCustomer = BooleanArray(agentList.size)
        binding.selectAgent.setOnClickListener {
            listDialogServicesAgent("Select Agent(s) you want to send message", 0)
        }
        binding.selectCustomer.setOnClickListener {
            listDialogServicesCustomer("Select Customer(s) you want to send message", 1)
        }
        binding.selectTemplate.setOnClickListener {
            showTemplates()
        }

        binding.toolbar.toolbarTv.text = "Send Message"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
    }

    private fun showTemplates() {
        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Select Template")
        var view: View = LayoutInflater.from(this).inflate(R.layout.template_dialog, null)
        var templateRv: RecyclerView = view.findViewById(R.id.templates)
        templateRv.apply {
            adapter = TemplateAdapter(templateList, selectDismissListener)
        }
        builder.setView(view)
        templateDialog = builder.show()
    }


    private fun listDialogServicesAgent(title: String, case: Int) {
        count = 0
        Log.e("size", agentList.toMutableList().size.toString())
        val arr = arrayOfNulls<String>(agentList.size)
        for (i in agentList.indices) {
            arr[i] = agentList[i].name
            //getProductName or any suitable method
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMultiChoiceItems(
            arr, checkedItemsAgent
        ) { dialog: DialogInterface?, position: Int, isChecked: Boolean ->
            //Log.d(TAG, "onClick: " + count);
            if (isChecked) {
//                    if (mUserItems.size() < 3) {
                mUserItemsAgent.toMutableList().add(arr[position]!!)
                iUserItemsAgent.toMutableList().add(agentList[position].id)
                // Log.d(TAG, "addonClick: " + mUserItems.size());
//                    } else {
//                        Toast.makeText(VendorActivity.this, "You can select only 3 services", Toast.LENGTH_SHORT).show();
//                        checkedItems[position] = false;
//                        ((AlertDialog) dialog).getListView().setItemChecked(position, false);
//                    }
            } else {
                mUserItemsAgent.toMutableList().remove(arr[position])
                //iUserItems.remove(servicesList.get(position));
                try {
                    iUserItemsAgent.toMutableList().remove(agentList[position].id)
                } catch (e: Exception) {
                }
                //  ((AlertDialog) dialog).getListView().setItemChecked(position, false);

                //count--;
            }
        }
        builder.setCancelable(false)
        builder.setPositiveButton("Send") { dialog: DialogInterface?, which: Int ->
            val data = StringBuilder()
            val idata = StringBuilder()
            for (item in mUserItemsAgent) {
                if (data.isNotEmpty()) {
                    data.append(',')
                }
                data.append(item)
                //Toast.makeText(MainActivity.this, "position: " +which, Toast.LENGTH_SHORT).show();

            }
            try {
                for (`val` in iUserItemsAgent) {
                    if (idata.isNotEmpty()) {
                        idata.append(',')
                    }
                    idata.append(`val`)
                    //Toast.makeText(MainActivity.this, "position: " +which, Toast.LENGTH_SHORT).show();
                }
            } catch (e: Exception) {
                Toast.makeText(this, "" + e.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        builder.setNegativeButton(
            "Dismiss"
        ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
        builder.setNeutralButton(
            "Clear All"
        ) { dialog: DialogInterface?, which: Int ->
            try {
                for (i in checkedItemsAgent.indices) {
                    checkedItemsAgent[i] = false
                    mUserItemsAgent.toMutableList().clear()
                    iUserItemsAgent.toMutableList().clear()
                    //   tv_services.setText(getResources().getString(R.string.select_services));
                    //   tv_services_no.setText(getResources().getString(R.string.select_services));
                }
            } catch (ex: Exception) {
                //Toast.makeText(VendorActivity.this, "error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        val alertDialog = builder.create()
        alertDialog.show()
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).isAllCaps = false
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).isAllCaps = false
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).isAllCaps = false
    }

    private fun listDialogServicesCustomer(title: String, case: Int) {
        count = 0
        Log.e("size", agentList.toMutableList().size.toString())
        val arr = arrayOfNulls<String>(customerList.size)
        for (i in customerList.indices) {
            arr[i] = customerList[i].name
            //getProductName or any suitable method
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMultiChoiceItems(
            arr, checkedItemsCustomer
        ) { dialog: DialogInterface?, position: Int, isChecked: Boolean ->
            //Log.d(TAG, "onClick: " + count);
            if (isChecked) {
//                    if (mUserItems.size() < 3) {
                mUserItemsCustomer.toMutableList().add(arr[position]!!)
                iUserItemsCustomer.toMutableList().add(customerList[position].id)
                // Log.d(TAG, "addonClick: " + mUserItems.size());
//                    } else {
//                        Toast.makeText(VendorActivity.this, "You can select only 3 services", Toast.LENGTH_SHORT).show();
//                        checkedItems[position] = false;
//                        ((AlertDialog) dialog).getListView().setItemChecked(position, false);
//                    }
            } else {
                mUserItemsCustomer.toMutableList().remove(arr[position])
                //iUserItems.remove(servicesList.get(position));
                try {
                    iUserItemsCustomer.toMutableList().remove(customerList[position].id)
                } catch (e: Exception) {
                }
                //  ((AlertDialog) dialog).getListView().setItemChecked(position, false);

                //count--;
            }
        }
        builder.setCancelable(false)
        builder.setPositiveButton("Send") { dialog: DialogInterface?, which: Int ->
            val data = StringBuilder()
            val idata = StringBuilder()
            for (item in mUserItemsCustomer) {
                if (data.isNotEmpty()) {
                    data.append(',')
                }
                data.append(item)
                //Toast.makeText(MainActivity.this, "position: " +which, Toast.LENGTH_SHORT).show();
            }
            try {
                for (`val` in iUserItemsCustomer) {
                    if (idata.isNotEmpty()) {
                        idata.append(',')
                    }
                    idata.append(`val`)
                    //Toast.makeText(MainActivity.this, "position: " +which, Toast.LENGTH_SHORT).show();
                }
            } catch (e: Exception) {
                Toast.makeText(this, "" + e.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        builder.setNegativeButton(
            "Dismiss"
        ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
        builder.setNeutralButton(
            "Clear All"
        ) { dialog: DialogInterface?, which: Int ->
            try {
                for (i in checkedItemsCustomer.indices) {
                    checkedItemsCustomer[i] = false
                    mUserItemsCustomer.toMutableList().clear()
                    iUserItemsCustomer.toMutableList().clear()
                    //   tv_services.setText(getResources().getString(R.string.select_services));
                    //   tv_services_no.setText(getResources().getString(R.string.select_services));
                }
            } catch (ex: Exception) {
                //Toast.makeText(VendorActivity.this, "error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        val alertDialog = builder.create()
        alertDialog.show()
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).isAllCaps = false
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).isAllCaps = false
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).isAllCaps = false
    }


    class TemplateAdapter(val templateList: List<Template>, val listener: SelectDismissListener) :
        RecyclerView.Adapter<TemplateAdapter.MYVH>() {
        class MYVH(view: View) : RecyclerView.ViewHolder(view) {
            var title: TextView = view.findViewById(R.id.title)
            var message: TextView = view.findViewById(R.id.message)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
            return MYVH(
                LayoutInflater.from(parent.context).inflate(R.layout.template_row, parent, false)
            )
        }

        override fun onBindViewHolder(holder: MYVH, position: Int) {
            holder.message.text = templateList[position].message
            holder.title.text = templateList[position].title
            holder.itemView.setOnClickListener {
                listener.onSelected(position)
            }
        }

        override fun getItemCount(): Int {
            return templateList.size
        }

    }

    override fun onSelected(position: Int) {
        templateDialog.dismiss()
        selectedTemplate = templateList[position]
        binding.titleEdt.setText(selectedTemplate.title)
        binding.messageEdt.setText(selectedTemplate.message)
    }
}