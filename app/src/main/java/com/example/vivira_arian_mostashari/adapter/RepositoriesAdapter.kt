package com.example.vivira_arian_mostashari.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.vivira_arian_mostashari.databinding.ItemRepositoriesBinding
import com.example.vivira_arian_mostashari.models.Item
import com.example.vivira_arian_mostashari.models.Repositories
import javax.inject.Inject

class RepositoriesAdapter @Inject constructor() :
    PagingDataAdapter<Item, RepositoriesAdapter.ViewHolder>(differCallback){

    private lateinit var binding: ItemRepositoriesBinding
    private lateinit var context: Context



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRepositoriesBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    //override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){
        fun set(item: Item){
            binding.apply {
                txtItemName.text = item.name
                txtFullName.text = item.fullName
                txtDescription.text = "Description: ${item.description}"
                txtOwnersName.text = "Owner's name: ${item.owner?.login}"
                txtUrl.text = item.url
                txtLang.text = "Language: ${item.language}"
                val avatarUrl = item.owner?.avatarUrl
                imgAvatarUrl.load(avatarUrl){
                    crossfade(true)
                    scale(Scale.FILL)
                }
            }
        }
    }

    companion object{
        val differCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }

    //val differ = AsyncListDiffer(this, differCallback)
}