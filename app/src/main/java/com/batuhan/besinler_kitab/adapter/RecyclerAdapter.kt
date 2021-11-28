package com.batuhan.besinler_kitab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.batuhan.besinler_kitab.model.BesinList
import com.batuhan.besinler_kitab.R
import com.batuhan.besinler_kitab.util.ImageDownload
import com.batuhan.besinler_kitab.util.placeHolderCreate
import com.batuhan.besinler_kitab.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.besinlistesirow.view.*
import com.google.gson.Gson







class RecyclerAdapter(val besinList:ArrayList<BesinList>):RecyclerView.Adapter<RecyclerAdapter.BesinListesiViewHolder>() {
    class BesinListesiViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinListesiViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.besinlistesirow,parent,false)



        return BesinListesiViewHolder(view)
    }

    override fun onBindViewHolder(holder: BesinListesiViewHolder, position: Int) {
        holder.itemView.YemekAdiText.text=besinList.get(position).besinIsim
        holder.itemView.yemekKaloriText.text=besinList.get(position).besinKalori
        holder.itemView.setOnClickListener{
            var action=BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)

        }
        holder.itemView.imageView.ImageDownload(besinList.get(position).besinGorsel,
            placeHolderCreate(holder.itemView.context))

    }

    override fun getItemCount(): Int {
        return besinList.size
    }
    fun yeniBesinListesi(besinListesi:List<BesinList>){
        besinList.clear()
        besinList.addAll(besinListesi)
        notifyDataSetChanged()

    }
}