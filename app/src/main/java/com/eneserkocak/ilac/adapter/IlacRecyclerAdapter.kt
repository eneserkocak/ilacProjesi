package com.eneserkocak.ilac.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.IlacRecyclerRowBinding
import com.eneserkocak.ilac.model.Ilac

class IlacRecyclerAdapter(val secilenIlac : (Ilac)->Unit): RecyclerView.Adapter<IlacRecyclerAdapter.IlacViewHolder>() {
    var ilacListesi= arrayListOf<Ilac>()
    class IlacViewHolder(var view: IlacRecyclerRowBinding):RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IlacViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view= DataBindingUtil.inflate<IlacRecyclerRowBinding>(inflater,R.layout.ilac_recycler_row,parent,false)
        return IlacViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: IlacViewHolder, position: Int) {

        val ilac = ilacListesi[position]
        holder.view.ilac = ilac




        holder.itemView.setOnClickListener {
            secilenIlac.invoke(ilac)

        }


}

    override fun getItemCount(): Int {
        return ilacListesi.size
    }

    fun ilacListesiniGuncelle(yeniIlacListesi:List<Ilac>){

        ilacListesi.clear()
        ilacListesi.addAll(yeniIlacListesi)
        notifyDataSetChanged()

    }

}