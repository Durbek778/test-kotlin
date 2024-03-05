package com.example.hospitalapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapplication.Game
import com.example.hospitalapplication.databinding.ActivityGameBinding
import com.example.hospitalapplication.databinding.ItemGameRcBinding
import com.squareup.picasso.Picasso

class GameAdapter(private val list: List<Game>,val onClick:(Game)->Unit) : RecyclerView.Adapter<GameAdapter.VH>() {
    inner class VH(private val itemGameRcBinding: ItemGameRcBinding) :
        RecyclerView.ViewHolder(itemGameRcBinding.root) {
        fun buildView(game: Game) {
            itemGameRcBinding.gameName.setText("Game name: " + game.game_name)
            Picasso.get().load(game.gameImgSrc).into(itemGameRcBinding.gamImgId)
            itemGameRcBinding.itemId.setOnClickListener {
                onClick.invoke(game)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemGameRcBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.buildView(list[position])
    }

    override fun getItemCount(): Int = list.size
}