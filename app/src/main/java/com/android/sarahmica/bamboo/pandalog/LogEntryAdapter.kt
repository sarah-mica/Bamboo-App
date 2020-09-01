package com.android.sarahmica.bamboo.pandalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.sarahmica.bamboo.R
import com.android.sarahmica.bamboo.database.LogEntryWithActivities
import com.android.sarahmica.bamboo.databinding.GreenActivityChipBinding
import com.android.sarahmica.bamboo.databinding.ListItemLogEntryBinding
import com.android.sarahmica.bamboo.databinding.LogEntryActivityChipBinding
import timber.log.Timber

class LogEntryAdapter :
    ListAdapter<LogEntryWithActivities, RecyclerView.ViewHolder>(
        LogEntryDiffCallback()
    )
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LogEntryViewHolder(ListItemLogEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val logEntry = getItem(position)
        (holder as LogEntryViewHolder).bind(logEntry)
    }


    class LogEntryViewHolder(private val binding: ListItemLogEntryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LogEntryWithActivities) {
            val activityList = item.greenActivityList
            val inflater = LayoutInflater.from(binding.logEntryActivityChips.context)

            activityList.forEach { activity ->
                val chipBinding: LogEntryActivityChipBinding = DataBindingUtil.inflate(inflater, R.layout.log_entry_activity_chip, binding.logEntryActivityChips, true)
                chipBinding.greenActivity = activity
                chipBinding.lifecycleOwner = binding.lifecycleOwner
            }
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