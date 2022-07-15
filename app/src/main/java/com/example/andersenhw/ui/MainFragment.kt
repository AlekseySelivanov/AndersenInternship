package com.example.andersenhw.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.andersenhw.CustomClockView
import com.example.andersenhw.R
import com.example.andersenhw.databinding.FragmentMainBinding
import java.util.*

class MainFragment: BaseFragment<FragmentMainBinding>() {
    private lateinit var clockView: CustomClockView
    private var defaultStyle = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clockView = binding.clockView
        initViews()
    }

    private fun initViews() {
        binding.clockView.mTimeCallBack = {
            binding.timeTv.text = "${it[Calendar.YEAR]}-${it[Calendar.MONTH] + 1}-${it[Calendar.DAY_OF_MONTH]} ${it[Calendar.HOUR_OF_DAY]}:${it[Calendar.MINUTE]}:${it[Calendar.SECOND]}"
        }
        binding.btnFlags.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_flagsFragment)
        }
       binding.changeColor.setOnClickListener {
            defaultStyle = when (defaultStyle) {
                true -> {
                    clockView.hourHandPaint.color = Color.RED
                    clockView.minuteHandPaint.color = Color.YELLOW
                    clockView.secondHandPaint.color = Color.MAGENTA
                    clockView.hourHandPaint.strokeWidth = CustomClockView.HOUR_HAND_WIDTH_2
                    clockView.minuteHandPaint.strokeWidth = CustomClockView.MINUTE_HAND_WIDTH_2
                    clockView.secondHandPaint.strokeWidth = CustomClockView.SECOND_HAND_WIDTH_2
                    false
                }
                false -> {
                    clockView.hourHandPaint.color = Color.BLACK
                    clockView.minuteHandPaint.color = Color.BLACK
                    clockView.secondHandPaint.color = Color.RED
                    clockView.hourHandPaint.strokeWidth = CustomClockView.HOUR_HAND_WIDTH
                    clockView.minuteHandPaint.strokeWidth = CustomClockView.MINUTE_HAND_WIDTH
                    clockView.secondHandPaint.strokeWidth = CustomClockView.SECOND_HAND_WIDTH
                    true
                }
            }
        }
    }

        override fun inflateViewBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
        ): FragmentMainBinding {
            return FragmentMainBinding.inflate(inflater, container, false)
        }
    }



