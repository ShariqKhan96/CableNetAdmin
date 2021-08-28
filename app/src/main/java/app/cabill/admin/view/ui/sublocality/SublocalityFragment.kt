package app.cabill.admin.view.ui.sublocality

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.adapter.AreaLocalityListAdapter
import app.cabill.admin.databinding.FragmentAreaListBinding
import app.cabill.admin.databinding.FragmentSublocalityBinding
import app.cabill.admin.model.Area
import app.cabill.admin.model.SubLocality
import app.cabill.admin.util.Utils
import app.cabill.admin.view.ui.area.AreaViewModel
import app.cabill.admin.view.ui.area.CreateAreaActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SublocalityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SublocalityFragment : Fragment() {

    private lateinit var binding: FragmentSublocalityBinding
    lateinit var viewModel: SubLocalityViewModel
    private val list: ArrayList<SubLocality> = arrayListOf()
    private lateinit var adapter: AreaLocalityListAdapter<SubLocality>

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentSublocalityBinding.inflate(inflater, container, false)
        adapter = AreaLocalityListAdapter(list, requireContext())
        binding.areas.apply {
            adapter = this@SublocalityFragment.adapter
        }
        binding.fab.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, SublocalityActivity::class.java))
            }
        }
        initViewModel()

        return binding.root
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)
            .get(SubLocalityViewModel::class.java)
        viewModel.getSubLocalityLiveDataObserver().observe(viewLifecycleOwner, Observer {
            if (!it.error) {
                list.clear()
                list.addAll(it.data!!)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.loadingObserver().observe(viewLifecycleOwner, Observer {
            if (it)
                Utils.getInstance().showLoader(requireContext(), "Please wait..")
            else Utils.getInstance().dismissLoader()
        })
    }

    override fun onStart() {
        super.onStart()
        if (viewModel != null)
            viewModel.getSubLocalitiesAPI(requireContext())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SublocalityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SublocalityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}