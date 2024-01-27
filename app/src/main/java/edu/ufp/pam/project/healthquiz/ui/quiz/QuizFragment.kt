package edu.ufp.pam.project.healthquiz.ui.quiz

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import edu.ufp.pam.project.healthquiz.R
import edu.ufp.pam.project.healthquiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    private var _binding : FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        _binding = FragmentQuizBinding.inflate(inflater,container, false)
        val root : View = binding.root
        val textView : TextView = binding.txtSleep
        quizViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val buttonOk = root.findViewById<ImageButton>(R.id.btnAgree)
        buttonOk?.setOnClickListener{ view ->
            val text = root.findViewById<TextView>(R.id.txtSleep)
            if (text != null) {
                text.text = "I feel totally rested after this night's sleep"
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}