package app.cabill.admin.view.ui.complaint

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityComplaintListBinding
import com.google.android.material.tabs.TabLayoutMediator

class ComplaintListActivity : FragmentActivity() {
    private lateinit var binding: ActivityComplaintListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplaintListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<TextView>(R.id.toolbarTv).text = "Complaints"
        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        binding.pager.adapter = DividerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            if (position == 0) {
                tab.text = "Resolved"
            } else {
                tab.text = "Pending"
            }
        }.attach()

    }


    class DividerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {


        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            lateinit var fragment1: Fragment
            if (position == 0)
                fragment1 = ResolvedFragment()
            else if (position == 1)
                fragment1 = PendingFragment()
            return fragment1
        }
    }
}