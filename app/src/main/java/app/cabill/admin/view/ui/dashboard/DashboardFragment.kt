package app.cabill.admin.view.ui.dashboard

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.databinding.FragmentDashboardBinding
import app.cabill.admin.view.Dashboard
import app.cabill.admin.view.ui.agent.AgentListActivity
import app.cabill.admin.view.ui.area.AreaLocalityListActivity
import app.cabill.admin.view.ui.complaint.ComplaintListActivity
import app.cabill.admin.view.ui.customer.CustomerListActivity


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        //val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding.totalEarningTv.text = tvTransform("0", "PKR")
        binding.receivableTv.text = tvTransform("0", "PKR")
        binding.balanceTv.text = tvTransform("0", "PKR")

        binding.calendarIv.setOnClickListener {

        }

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
            activity?.let {
                startActivity(Intent(it, AreaLocalityListActivity::class.java))
            }
        }
        binding.cableBill.setOnClickListener {
            Dashboard.navigation_listener.onNavigate(1)
            Log.e("here", "jere")
        }

        binding.manageComplaint.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, ComplaintListActivity::class.java))
            }
        }
        return binding.root
    }

    private fun tvTransform(text1: String, text2: String): CharSequence {
        val span1 = SpannableString(text1)
        span1.setSpan(AbsoluteSizeSpan(37), 0, text1.length, SPAN_INCLUSIVE_INCLUSIVE)

        val span2 = SpannableString(text2)
        span2.setSpan(AbsoluteSizeSpan(16), 0, text2.length, SPAN_INCLUSIVE_INCLUSIVE)
        return TextUtils.concat(span1, " ", span2)
    }
}