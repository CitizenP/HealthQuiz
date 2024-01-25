package edu.ufp.pam.project.healthquiz.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "I slept very well and feel that my sleep was totally restorative."
    }
    val text: LiveData<String> = _text
}