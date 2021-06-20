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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCreateMessageBinding
import app.cabill.admin.model.Message
import app.cabill.admin.model.NameID
import app.cabill.admin.model.Template
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.agent.AgentListViewModel
import app.cabill.admin.view.ui.customer.CustomerViewModel
import com.google.gson.Gson

interface SelectDismissListener {
    fun onSelected(position: Int)
}

class CreateMessageActivity : AppCompatActivity(), SelectDismissListener {
    private val mUserItemsAgent: ArrayList<String> = arrayListOf()
    private val mUserItemsCustomer: ArrayList<String> = arrayListOf()
    private val iUserItemsAgent: ArrayList<Int> = arrayListOf()
    private val iUserItemsCustomer: ArrayList<Int> = arrayListOf()
    lateinit var selectDismissListener: SelectDismissListener
    private val agentList: ArrayList<NameID> = arrayListOf()
    private val customerList: ArrayList<NameID> = arrayListOf()
    private val templateList: ArrayList<Template> = arrayListOf()
    lateinit var templateAdapter: TemplateAdapter
    var count = 0
    lateinit var templatesRv: RecyclerView
    var checkedItem: Int = 0
    private var selectedTemplate: Template? = null
    lateinit var templateDialog: AlertDialog

    lateinit var message: Message
    private var templates: Array<CharSequence> = Array(3) { "" }


    private lateinit var checkedItemsAgent: BooleanArray
    private lateinit var checkedItemsCustomer: BooleanArray

    private lateinit var binding: ActivityCreateMessageBinding
    lateinit var viewModel: MessageViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectDismissListener = this

        binding.selectAgent.setOnClickListener {
            listDialogServicesAgent("Select Agent(s) you want to send message", 0)
        }
        binding.selectCustomer.setOnClickListener {
            listDialogServicesCustomer("Select Customer(s) you want to send message", 1)
        }
        binding.selectTemplate.setOnClickListener {
            showTemplates()
        }
        binding.sendAllAgent.setOnClickListener {
            if (selectedTemplate != null) {
                message = Message(
                    1, selectedTemplate?.title!!, selectedTemplate?.body!!,
                    "agent", null, null
                )
                viewModel.create(message, this)
            }


        }
        binding.sendAllCustomer.setOnClickListener {
            if (selectedTemplate != null) {
                message = Message(
                    1, selectedTemplate?.title!!, selectedTemplate?.body!!,
                    "customer", null, null
                )
                viewModel.create(message, this)
            }

        }

        initViewModel()

        binding.toolbar.toolbarTv.text = "Send Message"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)
            .get(MessageViewModel::class.java)
        viewModel.loaderObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        viewModel.getTemplatesObserver().observe(this, Observer {
            if (it.isNotEmpty()) {
                templateList.clear()
                templateList.addAll(it)
            }
        })
        viewModel.messageCreateObserver().observe(this, Observer {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
        viewModel.getTemplates(this)
        val agentViewModel = ViewModelProvider(this).get(AgentListViewModel::class.java)
        agentViewModel.getAgentListObserver().observe(this, Observer {
            if (!it.error) {
                agentList.clear()
                for (i in it.data?.indices!!) {
                    agentList.add(NameID(it.data!![i].name, it.data!![i].id!!))
                }
                checkedItemsAgent = BooleanArray(agentList.size)
            }
        })
        agentViewModel.callAPI(this)
        val customerViewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        customerViewModel.customerListLiveDataObserver().observe(this, Observer {
            if (!it.error) {
                customerList.clear()
                for (i in it.data?.indices!!) {
                    customerList.add(NameID(it.data!![i].name, it.data!![i].id!!))
                }
                checkedItemsCustomer = BooleanArray(customerList.size)

            }
        })
        customerViewModel.getList(this)
    }

    private fun showTemplates() {
        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Select Template")
        var view: View = LayoutInflater.from(this).inflate(R.layout.template_dialog, null)
        var templateRv: RecyclerView = view.findViewById(R.id.templates)
        templateAdapter = TemplateAdapter(templateList, selectDismissListener)
        templateRv.apply {
            adapter = templateAdapter
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
                mUserItemsAgent.add(arr[position]!!)
                iUserItemsAgent.add(agentList[position].id)
                // Log.d(TAG, "addonClick: " + mUserItems.size());
//                    } else {
//                        Toast.makeText(VendorActivity.this, "You can select only 3 services", Toast.LENGTH_SHORT).show();
//                        checkedItems[position] = false;
//                        ((AlertDialog) dialog).getListView().setItemChecked(position, false);
//                    }
            } else {
                mUserItemsAgent.remove(arr[position])
                //iUserItems.remove(servicesList.get(position));
                try {
                    iUserItemsAgent.remove(agentList[position].id)
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

            if (selectedTemplate != null)
                message = Message(
                    1, selectedTemplate?.title!!, selectedTemplate?.body!!,
                    "agent", null, iUserItemsAgent
                )
            else {
                Toast.makeText(this, "Select any template first", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }
            viewModel.create(message, this)
            try {
                Log.e("agents", Gson().toJson(iUserItemsAgent))
//                for (`val` in iUserItemsAgent) {
//                    if (idata.isNotEmpty()) {
//                        idata.append(',')
//                    }
//                    idata.append(`val`)
//                    //Toast.makeText(MainActivity.this, "position: " +which, Toast.LENGTH_SHORT).show();
//                }
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
                    mUserItemsAgent.clear()
                    iUserItemsAgent.clear()
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
                mUserItemsCustomer.add(arr[position]!!)
                iUserItemsCustomer.add(customerList[position].id)
                // Log.d(TAG, "addonClick: " + mUserItems.size());
//                    } else {
//                        Toast.makeText(VendorActivity.this, "You can select only 3 services", Toast.LENGTH_SHORT).show();
//                        checkedItems[position] = false;
//                        ((AlertDialog) dialog).getListView().setItemChecked(position, false);
//                    }
            } else {
                mUserItemsCustomer.remove(arr[position])
                //iUserItems.remove(servicesList.get(position));
                try {
                    iUserItemsCustomer.remove(customerList[position].id)
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
            if (selectedTemplate != null)
                message = Message(
                    1, selectedTemplate?.title!!, selectedTemplate?.body!!,
                    "customer", iUserItemsCustomer, null
                )
            else {
                Toast.makeText(this, "Select any template first", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }
            viewModel.create(message, this)

            try {
                Log.e("customers", Gson().toJson(iUserItemsCustomer))

//                for (`val` in iUserItemsCustomer) {
//                    if (idata.isNotEmpty()) {
//                        idata.append(',')
//                    }
//                    idata.append(`val`)
//                    //Toast.makeText(MainActivity.this, "position: " +which, Toast.LENGTH_SHORT).show();
//                }
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
                    mUserItemsCustomer.clear()
                    iUserItemsCustomer.clear()
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


    class TemplateAdapter(
        val templateList: ArrayList<Template>,
        val listener: SelectDismissListener
    ) :
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
            holder.message.text = templateList[position].body
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
        binding.titleEdt.setText(selectedTemplate?.title)
        binding.messageEdt.setText(selectedTemplate?.body)
    }
}