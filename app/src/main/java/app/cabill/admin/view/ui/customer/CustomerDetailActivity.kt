package app.cabill.admin.view.ui.customer

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityCustomerDetailBinding
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList
import java.util.*

class CustomerDetailActivity : AppCompatActivity(),
    RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener<Any> {

    //fab
    private var rfaLayout: RapidFloatingActionLayout? = null
    private var rfaBtn: RapidFloatingActionButton? = null
    private var rfabHelper: RapidFloatingActionHelper? = null
    lateinit var binding: ActivityCustomerDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFAB(binding.root)

    }

    private fun initFAB(view: View) {
        val rfaContent = RapidFloatingActionContentLabelList(this)
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this)
        val items: MutableList<RFACLabelItem<Any>> = ArrayList()
        items.add(
            RFACLabelItem<Any>()
                .setLabel("Edit") //.setResId(R.drawable.ic_place_black_24dp)
                .setLabelColor(Color.parseColor("#2F4159"))
                .setIconNormalColor(Color.parseColor("#2F4159")) //   .setIconPressedColor(0xffbf360c)
                .setLabelSizeSp(16)
                .setWrapper(0)
        )
        items.add(
            RFACLabelItem<Any>()
                .setLabel("Delete") // .setResId(R.drawable.book)
                //    .setIconNormalColor(0xff4e342e)
                //  .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.parseColor("#2F4159"))
                .setIconNormalColor(Color.parseColor("#2F4159"))
                .setLabelSizeSp(16) //                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(context, 4)))
                .setWrapper(1)
        )
        rfaContent
            .setItems(items)
            .setIconShadowColor(-0x777778)
        rfabHelper = RapidFloatingActionHelper(
            this,
            binding.activityMainRfal,
            binding.activityMainRfab,
            rfaContent
        ).build()
    }


    override fun onRFACItemLabelClick(position: Int, item: RFACLabelItem<Any>?) {
        Toast.makeText(this, item!!.label, Toast.LENGTH_SHORT).show()
    }

    override fun onRFACItemIconClick(position: Int, item: RFACLabelItem<Any>?) {
    }

}