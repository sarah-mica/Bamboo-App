package com.android.sarahmica.bamboo.pandalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.sarahmica.bamboo.database.LogEntryWithActivities
import com.android.sarahmica.bamboo.databinding.ListItemLogEntryBinding
import timber.log.Timber

class LogEntryAdapter :
    ListAdapter<LogEntryWithActivities, RecyclerView.ViewHolder>(
        LogEntryDiffCallback()
    )
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Timber.i("create view holder!")
        return LogEntryViewHolder(ListItemLogEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Timber.i("bind view holder!!")
        val logEntry = getItem(position)
        (holder as LogEntryViewHolder).bind(logEntry)
    }


    class LogEntryViewHolder(private val binding: ListItemLogEntryBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            Timber.i("init new LogEntryViewHolder!")
            binding.setClickListener {
                binding.logEntry?.let{
                    Timber.i("You have clicked the log entry")
                }
            }
        }

        fun bind(item: LogEntryWithActivities) {
            binding.apply {
                logEntry = item
                executePendingBindings()
            }
        }
    }
}

private class LogEntryDiffCallback : DiffUtil.ItemCallback<LogEntryWithActivities>() {

    override fun areItemsTheSame(
        oldItem: LogEntryWithActivities,
        newItem: LogEntryWithActivities
    ): Boolean {
        return oldItem.logEntry.logEntryId == newItem.logEntry.logEntryId
    }

    override fun areContentsTheSame(
        oldItem: LogEntryWithActivities,
        newItem: LogEntryWithActivities
    ): Boolean {
        return oldItem.logEntry == newItem.logEntry && oldItem.greenActivityList == newItem.greenActivityList
    }
}