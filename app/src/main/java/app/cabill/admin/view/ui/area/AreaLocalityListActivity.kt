package app.cabill.admin.view.ui.area

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityAreaLocalityListBinding
import app.cabill.admin.view.ui.sublocality.SublocalityFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_area_locality_list.view.*


class AreaLocalityListActivity : FragmentActivity() {

    private lateinit var binding: ActivityAreaLocalityListBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaLocalityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.findViewById<TextView>(R.id.toolbarTv).text = "Manage Area / Sublocality"
        binding.root.findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        binding.pager.adapter = AreaLocalityDividerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            if (position == 0)
                tab.text = "Areas"
            if (position == 1)
                tab.text = "Sublocalities"
        }.attach()
    }

    override fun onStart() {
        super.onStart()
    }
    class AreaLocalityDividerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {


        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            lateinit var fragment1: Fragment
            if (position == 0)
                fragment1 = AreaListFragment()
            else if (position == 1)
                fragment1 = SublocalityFragment()
            return fragment1
        }
    }

}