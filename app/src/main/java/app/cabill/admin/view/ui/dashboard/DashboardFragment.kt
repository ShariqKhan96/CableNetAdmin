package app.cabill.admin.view.ui.dashboard

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.databinding.FragmentDashboardBinding
import app.cabill.admin.util.MonthYearPickerDialog
import app.cabill.admin.util.Utils
import app.cabill.admin.view.Dashboard
import app.cabill.admin.view.ui.agent.AgentListActivity
import app.cabill.admin.view.ui.area.AreaLocalityListActivity
import app.cabill.admin.view.ui.complaint.ComplaintListActivity
import app.cabill.admin.view.ui.connection.ConnectionActivity
import app.cabill.admin.view.ui.customer.CustomerListActivity
import java.util.*


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var date: String
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        date = Calendar.getInstance().get(Calendar.YEAR).toString() + "-" + Calendar.getInstance()
            .get(Calendar.MONTH).toString() + "-" + Calendar.getInstance()
            .get(Calendar.DAY_OF_MONTH).toString()
        Log.e("Date", date)
        initViewModel()
        //val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding.totalEarningTv.text = tvTransform("0", "PKR")
        binding.receivableTv.text = tvTransform("0", "PKR")
        binding.balanceTv.text = tvTransform("0", "PKR")


        binding.calendarIv.setOnClickListener {
            val pd = MonthYearPickerDialog()
            pd.setListener(DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                date = i.toString() + "-" + (i2).toString() + "-" + i3
                viewModel.dashboard(date, requireContext())
            })
            pd.show(parentFragmentManager, "MonthYearPickerDialog")
        }

        binding
        binding.manageCustomer.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, CustomerListActivity::class.java))
            }
        }

        binding.manageAgents.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, AgentListActivity::class.java))
            }
        }

        binding.managePackages.setOnClickListener {
            Dashboard.navigation_listener.onNavigate(2)
        }

        binding.manageArea.setOnClickListener {
            Toast.makeText(requireContext(), "Development in progress...", Toast.LENGTH_LONG).show()
            return@setOnClickListener
            activity?.let {
                startActivity(Intent(it, AreaLocalityListActivity::class.java))
            }
        }
        binding.cableBill.setOnClickListener {
            Dashboard.navigation_listener.onNavigate(1)
            Log.e("here", "jere")
        }

        binding.manageConnection.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, ConnectionActivity::class.java))
            }
        }
        return binding.root
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)
            .get(DashboardViewModel::class.java)
        viewModel.loadingObserver().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it)
                Utils.getInstance().showLoader(requireContext(), "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        viewModel.dashboardDataObserver().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null) {
                binding.totalConnection.setText(it.monthlyConnections.toString())
                binding.monthlyEarning.setText(it.monthlyEarnings.toString())
                binding.totalEarningTv.setText(it.totalEarnings.toString() + " PKR")
                binding.receivableTv.setText(it.monthlyReceived.toString() + " PKR")
                binding.balanceTv.setText(it.monthlyReceivables.toString() + " PKR")
            }
        })
        viewModel.dashboard(date, requireContext())
    }

    private fun tvTransform(text1: String, text2: String): CharSequence {
        val span1 = SpannableString(text1)
        span1.setSpan(AbsoluteSizeSpan(37), 0, text1.length, SPAN_INCLUSIVE_INCLUSIVE)

        val span2 = SpannableString(text2)
        span2.setSpan(AbsoluteSizeSpan(16), 0, text2.length, SPAN_INCLUSIVE_INCLUSIVE)
        return TextUtils.concat(span1, " ", span2)
    }
}