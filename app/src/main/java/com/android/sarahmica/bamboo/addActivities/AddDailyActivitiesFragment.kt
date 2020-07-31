package com.android.sarahmica.bamboo.addActivities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.sarahmica.bamboo.R
import com.android.sarahmica.bamboo.database.BambooDatabase
import com.android.sarahmica.bamboo.databinding.FragmentAddDailyActivitiesBinding

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
        val dataSource = BambooDatabase.getInstance(application).bambooDatabaseDao
        val viewModelFactory = AddDailyActivitiesViewModelFactory(dataSource)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddDailyActivitiesViewModel::class.java)



        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        return binding.root
    }
}