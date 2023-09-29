package com.eneserkocak.ilac.adapter

import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.ItemIlacBinding
import com.eneserkocak.ilac.model.Ilac
import com.eneserkocak.ilac.view2.TumIlaclarFragmentDirections
import com.kozan.alarm.AlarmUtil
import java.text.SimpleDateFormat

class TumIlaclarAdapter(val mLifecycleOwner: LifecycleOwner, val clickedAlarm: (Ilac) -> Unit,val clickedItem: (Ilac) -> Unit,) : BaseAdapter<Ilac, ItemIlacBinding>(clickedItem) {

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

            //İlaç silmeyi BottomSheet fragment içinde yapacağımız için ..o fragment a sileceğimiz ilacı (yani positionu)
            //Safe Argumant ile göndereceğiz.

            val action= TumIlaclarFragmentDirections.actionTumIlaclarFragmentToIlacSilBSFragment(item)
            Navigation.findNavController(it).navigate(action)


            //Sileceğimiz ilaç bottomSheet kullanmadan önce recycler içinde siliyorduk.
           // Silmek işini burada Adapter onBindViewHolder içinde yapıyorduk.

/*
           val count =  AppDatabase.getInstance(holder.itemView.context)!!.ilacDao().delete(item)
            if (count>0) {
                itemList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemList.size)



            }*/


        }


        holder.binding.alarmButton.setOnClickListener {
            clickedAlarm.invoke(item)


        }

        val adapter = AlarmAdapter(true){}
        holder.binding.recyclerView.layoutManager =GridLayoutManager(holder.binding.root.context,2)
       holder.binding.recyclerView.adapter = adapter
        val sdf = SimpleDateFormat("E,dd/MM/yyyy kk:mm:ss")
        AlarmUtil.getAlarms(holder.binding.root.context)?.observe(mLifecycleOwner) {
            it?.let {
                val list =  it.filter { it.id==item.id.toInt() }
                val alarmText = if (list.isEmpty()) "Alarm Kur" else "Alarm Düzenle"
                holder.binding.alarmKurText.text = alarmText
                list.forEach {
                    val dateTime = sdf.format(it.time)
                    println(dateTime)
                }
                adapter.updateItems(list)
            }
        }

       // holder.binding.ilacAdi.text = item.ilacAdi
      //  holder.binding.ilacSkt.text = item.skt



    }

}