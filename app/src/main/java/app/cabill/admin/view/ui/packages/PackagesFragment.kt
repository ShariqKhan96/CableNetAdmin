package app.cabill.admin.view.ui.packages

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.adapter.PackageAdapter
import app.cabill.admin.databinding.FragmentPackagesBinding
import app.cabill.admin.model.Package
import app.cabill.admin.util.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PackagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PackagesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var list: ArrayList<Package> = arrayListOf()
    lateinit var adapter_: PackageAdapter

    private lateinit var binding: FragmentPackagesBinding

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
        binding = FragmentPackagesBinding.inflate(inflater, container, false)
        binding.root.findViewById<TextView>(R.id.toolbarTv).text = "My Packages"
        binding.root.findViewById<ImageView>(R.id.back).visibility = View.GONE
        binding.fab.setOnClickListener {
            activity?.let {
                it.startActivity(Intent(it, CreatePackageActivity::class.java))
            }
        }
        adapter_ = PackageAdapter(requireContext(), list)
        initViewModel()
        binding.packages.apply {
            adapter = adapter_
        }
        return binding.root
    }

    private fun initViewModel() {
        val vm = ViewModelProvider(requireActivity()).get(PackageFragmentViewModel::class.java)
        vm.getLoaderObserver().observe(requireActivity(), Observer {
            if (it)
                Utils.getInstance().showLoader(requireActivity(), "Please wait...")
            else Utils.getInstance().dismissLoader()
        })
        vm.getPackageListObserver().observe(requireActivity(), Observer { response ->
            if (!response.error) {
                list.addAll(response.data)
                adapter_.notifyDataSetChanged()
            } else
                Utils.getInstance().showAlertDialog(requireContext(), response.message, "Error")
        })
        vm.getPackages()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PackagesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PackagesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}