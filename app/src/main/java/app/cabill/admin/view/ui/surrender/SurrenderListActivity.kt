package app.cabill.admin.view.ui.surrender

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import app.cabill.admin.R
import app.cabill.admin.adapter.SurrenderAdapter
import app.cabill.admin.databinding.ActivitySurrenderListBinding
import app.cabill.admin.interfaces.ClickListener
import app.cabill.admin.model.ApiState
import app.cabill.admin.model.Surrender
import app.cabill.admin.remote.API
import app.cabill.admin.remote.RetrofitInstance
import app.cabill.admin.util.Utils
import kotlinx.coroutines.flow.collect
import retrofit2.Response

class SurrenderListActivity : AppCompatActivity(), ClickListener {

    private lateinit var binding: ActivitySurrenderListBinding
    private lateinit var viewModel: SurrenderViewModel
    private lateinit var adapter: SurrenderAdapter
    private var list = arrayListOf<Surrender>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurrenderListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.toolbarTv.text = "Available for Surrender"
        binding.toolbar.back.setOnClickListener {
            finish()
        }
        adapter = SurrenderAdapter(this, list, this)
        binding.surrenderList.apply {
            adapter = this@SurrenderListActivity.adapter
        }
        val api: API = RetrofitInstance.client(this)
        viewModel = SurrenderViewModel(SurrenderRepository(api))
        viewModel.getSurrenderList()
        lifecycleScope.launchWhenCreated {
            viewModel._surrenderListPostState.collect { state ->
                when (state) {
                    is ApiState.Loading -> {
                        Utils.getInstance().showLoader(this@SurrenderListActivity, "Please wait...")
                    }
                    is ApiState.Error -> {
                        Utils.getInstance().dismissLoader()
                        Toast.makeText(
                            this@SurrenderListActivity,
                            state.error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ApiState.Success<*> -> {
                        Utils.getInstance().dismissLoader()
                        list.clear()
                        list.addAll(state.data as ArrayList<Surrender>)
                        adapter.notifyDataSetChanged()
                    }
                    ApiState.Idle -> {

                    }

                }
            }
            viewModel.surrenderPostState.collect { state ->
                when (state) {
                    is ApiState.Loading -> {
                        Utils.getInstance().showLoader(this@SurrenderListActivity, "Please wait...")
                    }
                    is ApiState.Error -> {
                        Utils.getInstance().dismissLoader()
                        Toast.makeText(
                            this@SurrenderListActivity,
                            state.error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ApiState.Success<*> -> {
                        Utils.getInstance().dismissLoader()
                        Toast.makeText(
                            this@SurrenderListActivity,
                            "Amount recovered successful",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    ApiState.Idle -> {

                    }

                }
            }

        }
    }

    override fun onClick(position: Int) {
        viewModel.updateSurrender(list[position].id)
        list[position].recovered_amount = 0
        adapter.notifyDataSetChanged()
    }
}