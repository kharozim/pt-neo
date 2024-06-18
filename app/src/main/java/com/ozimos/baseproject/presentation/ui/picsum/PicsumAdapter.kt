package com.ozimos.baseproject.presentation.ui.picsum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ozimos.baseproject.databinding.ItemPhotoBinding
import com.ozimos.baseproject.domain.PicsumDomain

class PicsumAdapter : ListAdapter<PicsumDomain, PicsumAdapter.MyViewHolder>(MyDiffUtil()) {

    var onClick: ((PicsumDomain) -> Unit)? = null
    var onLastItem: (() -> Unit)? = null

    inner class MyViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: PicsumDomain) {
            binding.run {
                tvName.text = item.author
                ivPicture.load(item.downloadUrl)
                tvId.text = item.id.toString()
                root.setOnClickListener {
                    onClick?.invoke(item)
                }
            }
        }

    }

    class MyDiffUtil : DiffUtil.ItemCallback<PicsumDomain>() {
        override fun areItemsTheSame(oldItem: PicsumDomain, newItem: PicsumDomain): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PicsumDomain, newItem: PicsumDomain): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(item = getItem(position))
        if (position == itemCount - 1) {
            onLastItem?.invoke()
        }
    }
}