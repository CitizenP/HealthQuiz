package edu.ufp.pam.project.healthquiz.ui.quiz1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel1 : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "I related easily to the people around me."
    }
    val text: LiveData<String> = _text
}