package com.android.sarahmica.bamboo.addActivities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.android.sarahmica.bamboo.R
import com.android.sarahmica.bamboo.database.BambooDatabase
import com.android.sarahmica.bamboo.database.GreenActivity
import com.android.sarahmica.bamboo.database.LogEntryRepository
import com.android.sarahmica.bamboo.databinding.FragmentAddDailyActivitiesBinding
import com.android.sarahmica.bamboo.pandalog.PandaLogFragmentDirections
import com.google.android.material.chip.Chip

class AddDailyActivitiesFragment : Fragment() {

    private lateinit var binding: FragmentAddDailyActivitiesBinding
    private lateinit var viewModel: AddDailyActivitiesViewModel

    /**
     * Called when the fragment is ready to display content on the screen
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_add_daily_activities
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_daily_activities, container, false)

        val application = requireNotNull(this.activity).application
        val activityDao = BambooDatabase.getInstance(application).activityDao()
        val logEntryRepository = LogEntryRepository.getInstance(
            BambooDatabase.getInstance(application).logEntryDao(),
            BambooDatabase.getInstance(application).logEntryWithActivitiesDao())

        val viewModelFactory = AddDailyActivitiesViewModelFactory(activityDao, logEntryRepository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddDailyActivitiesViewModel::class.java)

        // Create chips for all the activities in the DB
        viewModel.activitiesList.observe(viewLifecycleOwner, object: Observer<List<GreenActivity>> {
            override fun onChanged(data: List<GreenActivity>?) {
                data ?: return

                val chipGroup = binding.wasteActionsList
                val inflater = LayoutInflater.from(chipGroup.context)

                val children = data.map { activity ->
                    val chip = inflater.inflate(R.layout.green_activity_chip, chipGroup, false) as Chip
                    chip.text = activity.activityName
                    chip.tag = activity.activityId
                    chip
                }

                chipGroup.removeAllViews()

                for (chip in children) {
                    chipGroup.addView(chip)
                }
            }
        })

        binding.submitButton.setOnClickListener {
            addLogEntry()
        }

        viewModel.navigateToPandaLog.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate) {
                val navController = binding.root.findNavController()
                navController.navigate(
                    AddDailyActivitiesFragmentDirections
                        .actionAddDailyActivitiesFragmentToPandaLogFragment()
                )

                viewModel.doneNavigating()
            }
        })

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    private fun addLogEntry() {
        // Get the list of all the selected "activity" chips
        val ids: List<Int> = binding.wasteActionsList.checkedChipIds
        var tags: MutableList<Int> = mutableListOf()

        // the tags of each chip will be the activityId of that DB activity
        for (id in ids) {
            val chip: Chip = binding.wasteActionsList.findViewById(id)
            tags.add(chip.tag as Int)
        }

        // once we have this list, just let the viewModel take care of inserting all this to the DB
        viewModel.onAddGreenActivities(tags)
    }
}