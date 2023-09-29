package com.eneserkocak.ilac.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.RecyclerRowMapsBinding
import com.eneserkocak.ilac.model.Place

class PlaceAdapter(val selectedPlace : (Place)->Unit): RecyclerView.Adapter<PlaceAdapter.PlaceHolder>() {
    var placeList= arrayListOf<Place>()
    class PlaceHolder(val view:RecyclerRowMapsBinding): RecyclerView.ViewHolder(view.root){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RecyclerRowMapsBinding>(inflater, R.layout.recycler_row_maps,parent,false)
        return PlaceHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {


        val place = placeList[position]
        holder.view.place = place



        holder.itemView.setOnClickListener {

            selectedPlace.invoke(place)




        }


    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    fun placeListGuncelle (yeniPlaceList:List<Place>){

        placeList.clear()
        placeList.addAll(yeniPlaceList)
        notifyDataSetChanged()

    }
}