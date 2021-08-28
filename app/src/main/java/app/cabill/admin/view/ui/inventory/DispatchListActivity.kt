package app.cabill.admin.view.ui.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import app.cabill.admin.adapter.DispatchListAdapter
import app.cabill.admin.databinding.ActivityInventoryListBinding
import app.cabill.admin.model.Dispatcher
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.profile.ProfileViewModel

class DispatchListActivity : AppCompatActivity() {
    lateinit var binding: ActivityInventoryListBinding
    val viewModel: InventoryViewModel by viewModels()
    val profileViewModel: ProfileViewModel by viewModels()
    lateinit var adapter: DispatchListAdapter
    val list: ArrayList<Dispatcher> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = DispatchListAdapter(list, this)
        binding.toolbar.toolbarTv.text ="Dispatch Inventory"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddDispatcherActivity::class.java))
        }
        binding.inventoryRv.apply {
            adapter = this@DispatchListActivity.adapter
        }
//        profileViewModel.getProfile(this)
//        profileViewModel.getProfileObserver().observe(this, Observer {
//            if (!it.error) {
//            }
//        })
//        profileViewModel.loaderLiveData.observe(this, Observer {
//            if (it)
//                Utils.getInstance().showLoader(this, "Please wait...")
//            else Utils.getInstance().dismissLoader()
//        })
        viewModel.loadingObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Please wait..")
            else Utils.getInstance().dismissLoader()
        })
        viewModel.dispatch.observe(this, Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        })

    }

    override fun onStart() {
        super.onStart()
        viewModel.dispatcherList(this, 0)

    }

}