package com.android.sarahmica.bamboo.pandalog

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.sarahmica.bamboo.database.LogEntryWithActivities

class LogEntryAdapter : ListAdapter<LogEntryWithActivities, RecyclerView.ViewHolder>(LogEntryDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class LogEntryViewHolder(
        private val binding: ListItemLogEntryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        TODO("Not yet implemented")
    }

    fun bind(item: LogEntryWithActivities) {
        TODO ("Not yet implemented")
    }


}

private class LogEntryDiffCallback : DiffUtil.ItemCallback<LogEntryWithActivities>() {

    override fun areItemsTheSame(
        oldItem: LogEntryWithActivities,
        newItem: LogEntryWithActivities
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(
        oldItem: LogEntryWithActivities,
        newItem: LogEntryWithActivities
    ): Boolean {
        TODO("Not yet implemented")
    }
}