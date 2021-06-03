package app.cabill.admin.view.ui.bill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cabill.admin.R
import app.cabill.admin.adapter.BillAdapter
import app.cabill.admin.adapter.ComplaintAdapter
import app.cabill.admin.databinding.FragmentResolvedBinding
import app.cabill.admin.model.CableBill
import app.cabill.admin.model.InternetBill
import app.cabill.admin.util.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CableTvBillFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CableTvBillFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentResolvedBinding
    private lateinit var adapater: BillAdapter<CableBill>
    private val list = ArrayList<CableBill>()
    private lateinit var viewModel: BillViewModel

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
        binding = FragmentResolvedBinding.inflate(inflater, container, false)
        adapater = BillAdapter(requireContext(), list)
        binding.complaints.apply {
            adapter = this@CableTvBillFragment.adapater
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        viewModel.getBills()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this)
            .get(BillViewModel::class.java)
        viewModel.getBills()
        viewModel.loadingObserver().observe(viewLifecycleOwner, Observer {
            if (it)
                Utils.getInstance().showLoader(requireContext(), "Please wait...")
            else Utils.getInstance().dismissLoader()
        })

        viewModel.billListObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                list.clear()
                list.addAll(it.data?.cableBills!!)
                adapater.notifyDataSetChanged()
            } else {
                Utils.getInstance().showAlertDialog(requireContext(), it?.message, "Error")
            }
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CableTvBillFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CableTvBillFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}