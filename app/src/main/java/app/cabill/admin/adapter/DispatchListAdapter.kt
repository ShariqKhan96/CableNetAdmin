package app.cabill.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.databinding.DipatchRowBinding
import app.cabill.admin.model.Dispatcher
import kotlinx.android.synthetic.main.dipatch_row.view.*

class DispatchListAdapter(val list: ArrayList<Dispatcher>, context: Context) :
    RecyclerView.Adapter<DispatchListAdapter.MYVH>() {
    lateinit var binding: DipatchRowBinding

    inner class MYVH(itemView: DipatchRowBinding) : RecyclerView.ViewHolder(itemView.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        binding = DipatchRowBinding.inflate(LayoutInflater.from(parent.context))
        return MYVH(binding)
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        val dispatcher = list[position]
        binding.customerName.setText(dispatcher.inventory_item.name)
        binding.customerAddress.setText("Quantity : ${dispatcher.quantity}")
        binding.customerAgent.setText("Given to : ${dispatcher.user.name}")
    }

    override fun getItemCount(): Int {
return list.size    }
}