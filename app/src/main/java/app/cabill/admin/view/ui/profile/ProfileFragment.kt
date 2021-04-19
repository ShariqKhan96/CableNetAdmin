package app.cabill.admin.view.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import app.cabill.admin.R
import app.cabill.admin.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewPager: ViewPager2
    private lateinit var profileDividerAdapter: ProfileDividerAdapter
    private lateinit var fragmentProfileBinding: FragmentProfileBinding


    class ProfileDividerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            lateinit var fragment1: Fragment
            if (position == 0)
                fragment1 = GeneralSettingsFragment()
            else if (position == 1)
                fragment1 = ChangePasswordFragment()
            return fragment1
        }
    }


    class ChangePasswordFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            return inflater.inflate(R.layout.fragment_change_password, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        }
    }

    class GeneralSettingsFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            return inflater.inflate(R.layout.fragment_general_settings, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        profileDividerAdapter = ProfileDividerAdapter(this)
        viewPager = fragmentProfileBinding.root.findViewById(R.id.pager)
        fragmentProfileBinding.root.findViewById<TextView>(R.id.toolbarTv).text = "My Profile"
        fragmentProfileBinding.root.findViewById<ImageView>(R.id.back).visibility = View.GONE
        viewPager.adapter = profileDividerAdapter

        val tabLayout = fragmentProfileBinding.root.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0)
                tab.text = "General Settings"
            if (position == 1)
                tab.text = "Change Password"
        }.attach()



        return fragmentProfileBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}