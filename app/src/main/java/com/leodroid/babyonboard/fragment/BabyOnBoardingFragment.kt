package com.leodroid.babyonboard.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leodroid.babyonboard.R
import com.leodroid.babyonboard.databinding.FragmentBabyOnBoardingBinding
import com.plattysoft.leonids.ParticleSystem


class BabyOnBoardingFragment: Fragment() {

    private lateinit var binding: FragmentBabyOnBoardingBinding
    lateinit var ps1: ParticleSystem
    lateinit var ps2: ParticleSystem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_baby_on_boarding, container, false)

        showProgress()
        startFireWorks()

        return binding.root
    }

    private fun showProgress() {
        var progressStatus = 0
        val handler = Handler()

        // Start the lengthy operation in a background thread
        Thread(Runnable {
            while (progressStatus < 80) {
                // Update the progress status
                progressStatus += 1

                // Try to sleep the thread for 20 milliseconds
                try {
                    Thread.sleep(20)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                // Update the progress bar
                handler.post(Runnable {
                    binding.progress.setProgress(progressStatus)
                    // Show the progress on TextView
                    //tv.setText(progressStatus + "")
                })
            }
        }).start() // Start the operation
    }

    private fun startFireWorks() {
        ps1 = ParticleSystem(activity!!, 80, R.drawable.fireworks_1, 10000)
        ps1.cancel()
        ps1.setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                .setRotationSpeed(144f)
                .setAcceleration(0.00005f, 90)
                .emit(binding.emiterTopRight, 8)

        ps2 = ParticleSystem(activity!!, 80, R.drawable.fireworks_2, 10000)
        ps2.cancel()
        ps2.setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                .setRotationSpeed(144f)
                .setAcceleration(0.00005f, 90)
                .emit(binding.emiterTopLeft, 8)
    }

    override fun onPause() {
        ps1.cancel()
        ps2.cancel()
        super.onPause()
    }

}