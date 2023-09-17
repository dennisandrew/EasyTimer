package com.dacoding.easytimer.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dacoding.easytimer.helper.SingleLiveEvent
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private var countDownTimer: CountDownTimer? = null

    private val timeFormat = "%02d:%02d"

    fun Long.formatTime(): String = String.format(
        timeFormat,
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )

    private val _pickedTime = MutableLiveData<Long>()
    val pickedTime: LiveData<Long> = _pickedTime

    private val _time = MutableLiveData(pickedTime.value?.formatTime() ?: "")
    val time: LiveData<String> = _time

    fun setPickedTime(time: Long) {
        _pickedTime.value = time
    }

    private val _progress = MutableLiveData(1.00F)
    val progress: LiveData<Float> = _progress

    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = _isPlaying


    val _celebrate = SingleLiveEvent<Boolean>()


    val celebrate: LiveData<Boolean> get() = _celebrate

    fun handleCountDownTimer() {
        if (isPlaying.value == true) {
            stopTimer()
            _celebrate.postValue(false)
        } else {
            startTimer()
                }
            }

            private fun stopTimer() {
                countDownTimer?.cancel()
                // !!
                handleTimerValues(false, pickedTime.value?.formatTime()!!, 1.0F, false)
            }

            private fun startTimer() {
                // !!
                val timeValue = pickedTime.value
                if (timeValue != null) {
                    _isPlaying.value = true
                    countDownTimer = object : CountDownTimer(timeValue.toLong(), 1000) {

                        override fun onTick(millisRemaining: Long) {
                            val progressValue = millisRemaining.toFloat() / timeValue.toFloat()
                    handleTimerValues(true, millisRemaining.formatTime(), progressValue, false)
                    _celebrate.postValue(false)
                }

                override fun onFinish() {
                    stopTimer()
                    _celebrate.postValue(true)
                }
            }.start()
        }

    }

    private fun handleTimerValues(
        isPlaying: Boolean,
        text: String,
        progress: Float,
        celebrate: Boolean
    ) {
        _isPlaying.value = isPlaying
        _time.value = text
        _progress.value = progress
        _celebrate.postValue(celebrate)
    }


}
