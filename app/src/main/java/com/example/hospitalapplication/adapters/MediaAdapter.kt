package com.example.hospitalapplication.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapplication.Media
import com.example.hospitalapplication.databinding.ItemMediaRcBinding
import com.squareup.picasso.Picasso

class MediaAdapter(val list: List<Media>, val onClick: (Media) -> Unit) :
    RecyclerView.Adapter<MediaAdapter.VH>() {
    inner class VH(val itemMediaBinding: ItemMediaRcBinding) :
        RecyclerView.ViewHolder(itemMediaBinding.root) {
        fun viewBuild(media: Media) {
            Picasso.get().load(media.media_img_src).into(itemMediaBinding.mediaImageId)
            itemMediaBinding.textTitileId.text = media.mediatitle
            itemMediaBinding.textMessageId.text = media.mediadescription
            itemMediaBinding.root.setOnClickListener {
                onClick.invoke(media)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemMediaRcBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.viewBuild(list[position])
    }

    override fun getItemCount(): Int = list.size
}