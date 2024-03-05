package com.example.hospitalapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapplication.Art
import com.example.hospitalapplication.databinding.ItemArtRcBinding
import com.squareup.picasso.Picasso

class ArtAdapter(private val list: List<Art>, val onClick: (Art) -> Unit) :
    RecyclerView.Adapter<ArtAdapter.VH>() {
    inner class VH(val itemArtRcBinding: ItemArtRcBinding) :
        RecyclerView.ViewHolder(itemArtRcBinding.root) {
        fun viewBuild(art: Art) {
            Picasso.get().load(art.artimage[2].imagesrc).into(itemArtRcBinding.imageArtId)
            itemArtRcBinding.textView.text = art.name
            itemArtRcBinding.textView.isVerticalScrollBarEnabled = true
            itemArtRcBinding.root.setOnClickListener {
                onClick.invoke(art)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemArtRcBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.viewBuild(list[position])
    }

    override fun getItemCount(): Int = list.size
}