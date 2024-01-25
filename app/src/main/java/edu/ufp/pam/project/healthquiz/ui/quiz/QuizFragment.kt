package edu.ufp.pam.project.healthquiz.ui.quiz

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.ufp.pam.project.healthquiz.databinding.FragmentSleepBinding

class QuizFragment : Fragment() {
    private var _binding : FragmentSleepBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        _binding = FragmentSleepBinding.inflate(inflater,container, false)
        val root : View = binding.root
        val textView : TextView = binding.txtSleep
        quizViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}