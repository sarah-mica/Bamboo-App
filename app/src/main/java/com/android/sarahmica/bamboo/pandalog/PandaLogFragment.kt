package com.android.sarahmica.bamboo.pandalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.android.sarahmica.bamboo.database.BambooDatabase
import com.android.sarahmica.bamboo.databinding.FragmentPandaLogBinding
import com.android.sarahmica.bamboo.R
import com.android.sarahmica.bamboo.database.LogEntryRepository


class PandaLogFragment : Fragment() {

    /**
     * Called when the fragment is ready to display content on the screen
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_panda_log
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views
        val binding: FragmentPandaLogBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_panda_log, container, false)

        val application = requireNotNull(this.activity).application
        val logEntryRepository = LogEntryRepository.getInstance(
            BambooDatabase.getInstance(application).logEntryDao(),
            BambooDatabase.getInstance(application).logEntryWithActivitiesDao())
        val viewModelFactory = PandaLogViewModelFactory(logEntryRepository, application)

        val pandaLogViewModel = ViewModelProvider(this, viewModelFactory).get(PandaLogViewModel::class.java)

        binding.lifecycleOwner = this
        binding.pandaLogViewModel = pandaLogViewModel

        pandaLogViewModel.navigateToAddScreen.observe(viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate == true) {
                    val navController = binding.root.findNavController()
                    navController.navigate(PandaLogFragmentDirections
                        .actionPandaLogFragmentToAddDailyActivitiesFragment())
                    pandaLogViewModel.onNavigatedToAddScreen()
                }
            })

        return binding.root
    }
}