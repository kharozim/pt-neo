package com.ozimos.baseproject.presentation.ui.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ozimos.baseproject.databinding.ItemGithubUserBinding
import com.ozimos.baseproject.domain.UserDomain

class GithubAdapter : ListAdapter<UserDomain, GithubAdapter.MyViewHolder>(MyDiffUtil()) {

    var onClick: ((UserDomain) -> Unit)? = null
    var onLastPage: (() -> Unit)? = null

    inner class MyViewHolder(private val binding: ItemGithubUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: UserDomain) {
            binding.run {
                tvName.text = item.login
                ivPicture.load(item.avatarUrl)
                tvId.text = item.id.toString()
                root.setOnClickListener {
                    onClick?.invoke(item)
                }
            }
        }

    }

    class MyDiffUtil : DiffUtil.ItemCallback<UserDomain>() {
        override fun areItemsTheSame(oldItem: UserDomain, newItem: UserDomain): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserDomain, newItem: UserDomain): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemGithubUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(item = getItem(position))

        if (position == itemCount - 1) {
            onLastPage?.invoke()
        }
    }
}