package edu.ufp.pam.project.healthquiz.ui.quiz2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel2 : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "I felt emotionally balanced."
    }
    val text: LiveData<String> = _text
}