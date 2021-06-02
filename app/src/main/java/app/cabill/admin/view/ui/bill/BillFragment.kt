package app.cabill.admin.view.ui.bill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.cabill.admin.R
import app.cabill.admin.databinding.FragmentBillBinding
import app.cabill.admin.view.ui.profile.ProfileFragment
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BillFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BillFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: BillViewModel
    private lateinit var binding: FragmentBillBinding


    class DividerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            lateinit var fragment1: Fragment
            if (position == 0)
                fragment1 = InternetBillFragment()
            else if (position == 1)
                fragment1 = CableTvBillFragment()
            return fragment1
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBillBinding.inflate(inflater, container, false)
        binding.pager.adapter = DividerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            if (position == 0)
                tab.text = "Internet Connections"
            if (position == 1)
                tab.text = "Cable TV"
        }.attach()

        binding.root.findViewById<TextView>(R.id.toolbarTv).text = "Bills"
        binding.root.findViewById<ImageView>(R.id.back).visibility = View.GONE

        return binding.root

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BillFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BillFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}