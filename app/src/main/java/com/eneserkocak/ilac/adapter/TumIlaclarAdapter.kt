package com.eneserkocak.ilac.adapter

import android.app.AlertDialog
import android.content.Context
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.ItemIlacBinding
import com.eneserkocak.ilac.db.AppDatabase
import com.eneserkocak.ilac.model.Ilac
import com.eneserkocak.ilac.util.BaseAdapter

class TumIlaclarAdapter(val clickedItem: (Ilac) -> Unit, ) : BaseAdapter<Ilac,ItemIlacBinding>(clickedItem) {
    override fun getLayoutRes(): Int {
        return R.layout.item_ilac
    }

    override fun bindViewHolder(
        holder: BaseViewHolder<ItemIlacBinding>,
        item: Ilac,
        position: Int
    ) {
        holder.binding.ilac = item
        holder.binding.ilaciSil.setOnClickListener {
           val count =  AppDatabase.getInstance(holder.itemView.context)!!.ilacDao().delete(item)
            if (count>0) {
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, items.size)

            }


        }

       // holder.binding.ilacAdi.text = item.ilacAdi
      //  holder.binding.ilacSkt.text = item.skt



    }
}