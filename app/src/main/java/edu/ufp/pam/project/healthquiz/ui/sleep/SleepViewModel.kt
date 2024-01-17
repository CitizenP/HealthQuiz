package edu.ufp.pam.project.healthquiz.ui.sleep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SleepViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "I slept very well and feel that my sleep was totally restorative."
    }
    val text: LiveData<String> = _text
}