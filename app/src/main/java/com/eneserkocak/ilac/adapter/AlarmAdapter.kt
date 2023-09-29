package com.eneserkocak.ilac.adapter

import android.view.View
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.ItemSaatBinding
import com.kozan.alarm.Alarm
import com.kozan.alarm.AlarmUtil
import java.text.SimpleDateFormat

class AlarmAdapter(val tumIlaclar:Boolean?=null,clickedItem: (Alarm) -> Unit) : BaseAdapter<Alarm,ItemSaatBinding>(clickedItem) {
    val sdf = SimpleDateFormat("kk:mm")
    override fun getLayoutRes() = R.layout.item_saat

    override fun bindViewHolder(
        holder: BaseViewHolder<ItemSaatBinding>,
        item: Alarm,
        position: Int
    ) {
        if (tumIlaclar == true) holder.binding.deleteAlarm.visibility = View.GONE
       holder.binding.saatTextV.text = sdf.format(item.time)

        holder.binding.alarmDuzenle.setOnClickListener {

        }

    holder.binding.deleteAlarm.setOnClickListener {
            AlarmUtil.cancelAlarm(it.context,item)
            itemSil(position)
        }
    }
    fun itemSil(position: Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,itemList.size)
    }
}