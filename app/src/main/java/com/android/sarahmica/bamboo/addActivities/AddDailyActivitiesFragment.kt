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
import com.android.sarahmica.bamboo.ActivityType
import com.android.sarahmica.bamboo.R
import com.android.sarahmica.bamboo.database.BambooDatabase
import com.android.sarahmica.bamboo.database.GreenActivity
import com.android.sarahmica.bamboo.database.LogEntryRepository
import com.android.sarahmica.bamboo.databinding.FragmentAddDailyActivitiesBinding
import com.android.sarahmica.bamboo.databinding.GreenActivityChipBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import timber.log.Timber

@Suppress("DEPRECATION")
class AddDailyActivitiesFragment : Fragment() {

    //TODO: Figure out how to get this screen to load a lot less slowly!!!

    //TODO: Have a better way of displaying all the options.. maybe you can have more general categories and
    //      then get more details from there after selecting? Or have it similar to the Waze buttons!

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

        val arguments = AddDailyActivitiesFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = AddDailyActivitiesViewModelFactory(arguments.dayKey, activityDao, logEntryRepository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddDailyActivitiesViewModel::class.java)

        // Create chips for all the activities in the DB
        viewModel.wasteActivitiesList.observe(viewLifecycleOwner, object: Observer<List<GreenActivity>> {
            override fun onChanged(data: List<GreenActivity>?) {
                data ?: return
                populateChipGroup(data, ActivityType.WASTE)
            }
        })

        viewModel.energyActivitiesList.observe(viewLifecycleOwner, object: Observer<List<GreenActivity>> {
            override fun onChanged(data: List<GreenActivity>?) {
                data ?: return
                populateChipGroup(data, ActivityType.ENERGY)
            }
        })

        viewModel.waterActivitiesList.observe(viewLifecycleOwner, object: Observer<List<GreenActivity>> {
            override fun onChanged(data: List<GreenActivity>?) {
                data ?: return
                populateChipGroup(data, ActivityType.WATER)
            }
        })

        viewModel.activismActivitiesList.observe(viewLifecycleOwner, object: Observer<List<GreenActivity>> {
            override fun onChanged(data: List<GreenActivity>?) {
                data ?: return
                populateChipGroup(data, ActivityType.ACTIVISM)
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
        var tags: MutableList<Int> = mutableListOf()

        for (activityType in ActivityType.values()) {
            // Get the list of all the selected "activity" chips
            val chipGroup: ChipGroup = getChipGroup(activityType)
            var ids: MutableList<Int> = chipGroup.checkedChipIds

            // the tags of each chip will be the activityId of that DB activity
            for (id in ids) {
                val chip: Chip = chipGroup.findViewById(id)
                tags.add(chip.tag as Int)
            }
        }
        // once we have this list, just let the viewModel take care of inserting all this to the DB
        viewModel.onAddGreenActivities(tags)
    }

    private fun populateChipGroup(activityList: List<GreenActivity>, type: ActivityType) {
        Timber.i("populating chip group: %s", type.toString())

        val chipGroup = getChipGroup(type)
        val inflater = LayoutInflater.from(chipGroup.context)
        activityList.forEach { activity ->
            val chipBinding: GreenActivityChipBinding = DataBindingUtil.inflate(inflater, R.layout.green_activity_chip, chipGroup, true)
            chipBinding.greenActivity = activity
            chipBinding.addActivitiesViewModel = viewModel
            chipBinding.lifecycleOwner = this
        }
    }

    private fun getChipGroup(type: ActivityType): ChipGroup  {
        return when(type) {
            ActivityType.WASTE -> binding.wasteActionsList
            ActivityType.ENERGY -> binding.energyActionsList
            ActivityType.WATER -> binding.waterActionsList
            ActivityType.ACTIVISM -> binding.activismActionsList
            else -> {
                Timber.e("Unknown ActivityType! %s", type)
                binding.activismActionsList
            }
        }
    }

}