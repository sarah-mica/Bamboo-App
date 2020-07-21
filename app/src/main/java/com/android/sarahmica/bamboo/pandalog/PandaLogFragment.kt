package com.android.sarahmica.bamboo.pandalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.sarahmica.bamboo.database.BambooDatabase
import com.android.sarahmica.bamboo.databinding.FragmentPandaLogBinding
import com.android.sarahmica.bamboo.R


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
        val dataSource = BambooDatabase.getInstance(application).bambooDatabaseDao
        val viewModelFactory = PandaLogViewModelFactory(dataSource, application)

        val pandaLogViewModel = ViewModelProviders.of(this, viewModelFactory).get(PandaLogViewModel::class.java)


        binding.setLifecycleOwner(this)
        binding.pandaLogViewModel = pandaLogViewModel
        return binding.root
    }
}