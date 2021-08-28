package app.cabill.admin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.cabill.admin.R
import app.cabill.admin.databinding.InventoryRowBinding
import app.cabill.admin.model.Inventory
import app.cabill.admin.view.ui.inventory.AddInventoryActivity
import com.google.gson.Gson

class InventoryAdapter(val list: List<Inventory>, val context: Context) :
    RecyclerView.Adapter<InventoryAdapter.MYVH>() {
    class MYVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.product_name)
        val stock = itemView.findViewById<TextView>(R.id.stock)
        val price = itemView.findViewById<TextView>(R.id.price)
        val viewDetails = itemView.findViewById<TextView>(R.id.view_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYVH {
        val binding = InventoryRowBinding.inflate(LayoutInflater.from(parent.context))
        return MYVH(binding.root)
    }

    override fun onBindViewHolder(holder: MYVH, position: Int) {
        val inventory = list[position]
        holder.name.text = inventory.name
        holder.stock.text = "Stock (${inventory.stock})"
        holder.price.text = "Price : ${inventory.price}"
        holder.viewDetails.setOnClickListener {
            val intent = Intent(context, AddInventoryActivity::class.java)
            intent.apply {
                putExtra("inventory", Gson().toJson(inventory))
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}