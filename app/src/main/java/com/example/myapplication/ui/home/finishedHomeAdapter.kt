package com.example.myapplication.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.response.ListEventsItem
import com.example.myapplication.databinding.ItemUpcomingEventBinding
import com.example.myapplication.ui.DetailActivity

class finishedHomeAdapter: ListAdapter<ListEventsItem, finishedHomeAdapter.FinishedViewHolder>(DIFF_CALLBACK) {
    class FinishedViewHolder(private val binding: ItemUpcomingEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(events : ListEventsItem){
            binding.cardTitle.text = events.name
            Glide.with(itemView.context)
                .load(events.mediaCover)
                .fitCenter()
                .into(binding.imgItemPhoto)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedViewHolder {
        val binding = ItemUpcomingEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinishedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinishedViewHolder, position: Int) {
        val finished = getItem(position)
        holder.bind(finished)

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            val intentDetail = Intent(context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.KEY_EVENT, finished)
            context.startActivity(intentDetail)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}