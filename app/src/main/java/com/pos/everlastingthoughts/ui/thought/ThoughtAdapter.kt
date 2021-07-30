package com.pos.everlastingthoughts.ui.thought

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pos.everlastingthoughts.R
import com.pos.everlastingthoughts.database.Thought
import com.pos.everlastingthoughts.databinding.ThoughtItemBinding

class ThoughtAdapter: ListAdapter<Thought, ThoughtAdapter.ThoughtViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThoughtViewHolder {
        val binding = ThoughtItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThoughtViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThoughtViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class ThoughtViewHolder(private val binding: ThoughtItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(thoughtItem: Thought) {
            binding.apply {
                imageThought.setImageResource(R.drawable.ic_baseline_bubble_chart_24)
                textThought.text = thoughtItem.thought
                textThoughtDate.text = thoughtItem.date
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Thought>() {
        override fun areItemsTheSame(oldItem: Thought, newItem: Thought) = oldItem.uid == newItem.uid
        override fun areContentsTheSame(oldItem: Thought, newItem: Thought) = oldItem == newItem
    }
}