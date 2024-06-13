package com.projects.recyclerview_advanced.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projects.recyclerview_advanced.R
import com.projects.recyclerview_advanced.data.Status
import com.projects.recyclerview_advanced.databinding.CharacterItemBinding
import com.projects.recyclerview_advanced.data.entities.Character

class CharactersAdapter : PagingDataAdapter<Character, CharacterViewHolder>(
    CharacterDiffUtilCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        with(holder.binding) {
            name.text = character?.name
            when (character?.status) {
                Status.Alive.name -> status.setColorFilter(ContextCompat.getColor(holder.itemView.context,
                    R.color.green
                ), android.graphics.PorterDuff.Mode.MULTIPLY)
                Status.Dead.name -> status.setColorFilter(ContextCompat.getColor(holder.itemView.context,
                    R.color.red
                ), android.graphics.PorterDuff.Mode.MULTIPLY)
                else ->  status.setColorFilter(ContextCompat.getColor(holder.itemView.context,
                    R.color.grey
                ), android.graphics.PorterDuff.Mode.MULTIPLY)
            }
            statusText.text = "${character?.status} - ${character?.species}"
            location.text = character?.location?.name

            character.let {
                Glide.with(image.context)
                    .load(it?.image)
                    .override(150,150)
                    .into(image)
            }
        }
    }


}

class CharacterViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root)

class CharacterDiffUtilCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem

}
