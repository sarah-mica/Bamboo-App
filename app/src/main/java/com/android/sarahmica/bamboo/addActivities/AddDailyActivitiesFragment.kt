package com.android.sarahmica.bamboo.addActivities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.sarahmica.bamboo.R
import com.android.sarahmica.bamboo.database.BambooDatabase
import com.android.sarahmica.bamboo.database.GreenActivity
import com.android.sarahmica.bamboo.databinding.FragmentAddDailyActivitiesBinding
import com.google.android.material.chip.Chip

class AddDailyActivitiesFragment : Fragment() {

    /**
     * Called when the fragment is ready to display content on the screen
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_add_daily_activities
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views
        val binding: FragmentAddDailyActivitiesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_daily_activities, container, false)

        val application = requireNotNull(this.activity).application
        val activityDao = BambooDatabase.getInstance(application).activityDao()
        val logEntryDao = BambooDatabase.getInstance(application).logEntryDao()
        val logEntryActivityDao = BambooDatabase.getInstance(application).logEntryWithActivitiesDao()

        val viewModelFactory = AddDailyActivitiesViewModelFactory(activityDao, logEntryDao, logEntryActivityDao)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(AddDailyActivitiesViewModel::class.java)

        viewModel.activitiesList.observe(viewLifecycleOwner, object: Observer<List<GreenActivity>> {
            override fun onChanged(data: List<GreenActivity>?) {
                data ?: return

                val chipGroup = binding.wasteActionsList
                val inflater = LayoutInflater.from(chipGroup.context)

                val children = data.map { activity ->
                    val chip = inflater.inflate(R.layout.green_activity_chip, chipGroup, false) as Chip
                    chip.text = activity.activityName
                    chip.tag = activity.activityId
                    /*chip.setOnCheckedChangeListener { button, isChecked ->
                        TODO("what to do when an activity is selected?")
                    }*/

                    chip
                }

                chipGroup.removeAllViews()

                for (chip in children) {
                    chipGroup.addView(chip)
                }
            }
        })

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }
}