package edu.ufp.pam.project.healthquiz.ui.quiz2

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import edu.ufp.pam.project.healthquiz.MainActivity
import edu.ufp.pam.project.healthquiz.R
import edu.ufp.pam.project.healthquiz.databinding.FragmentQuizBinding
import edu.ufp.pam.project.healthquiz.db.QuizDatabase
import edu.ufp.pam.project.healthquiz.db.quizzes.QuizzResult
import edu.ufp.pam.project.healthquiz.db.quizzes.QuizzResultRepository
import edu.ufp.pam.project.healthquiz.ui.quiz.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class QuizFragment2 : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var quizResultRepository: QuizzResultRepository
    private lateinit var currentDate: String
    //private var userNumber: Int = 1 // user's identification logic
    private val totalQuestions = 3// total number of questions in quiz
    private val quizNumber = 3 //
    private var userId: Int = -1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.txtSleep

        // Retrieve the user ID from the intent
        userId = requireActivity().intent.getIntExtra("USER_ID", -1)
        // Now you have the user ID in the userId variable


        val application = requireNotNull(this.activity).application
        val dao = QuizDatabase.getDatabaseInstance(application).quizzDao
        quizResultRepository = QuizzResultRepository(dao)
        currentDate = getCurrentDate()

        val buttonOk = root.findViewById<ImageButton>(R.id.btnAgree)
        val btnNeither = root.findViewById<ImageButton>(R.id.btnNeither)
        val btnDisagree = root.findViewById<ImageButton>(R.id.btnDisagree)

        val text = root.findViewById<TextView>(R.id.txtSleep)

        var currentState = 1


        text.text = getString(R.string.quiz_3_question_1)


        val commonOnClickListener = View.OnClickListener { view ->
            // Move to the next state or go back to the main activity
            val newTextResId = when (currentState) {
                1 -> R.string.quiz_3_question_2
                2 -> R.string.quiz_3_question_3
                else -> R.string.quiz_3_question_1
            }

            val questionNumber = currentState
            val value = when (view.id) {
                R.id.btnAgree -> 1
                R.id.btnNeither -> 2
                R.id.btnDisagree -> 3
                else -> 0 // Default value
            }
            text.post {
                text.text = getString(newTextResId)
            }
            // Create a QuizResult instance and insert it into the database using a coroutine
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                val quizResult = QuizzResult(
                    date = currentDate,
                    userNumber = userId,
                    quizNumber = quizNumber,
                    questionNumber = questionNumber,
                    value = value
                )
                quizResultRepository.insertQuizResult(quizResult)
            }





            // If currentState is 1, increment it; otherwise, go back to the main activity
            if (currentState == totalQuestions) {
                // Quiz is finished, go back to the main activity
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            } else {
                currentState++
            }
        }


        buttonOk?.setOnClickListener(commonOnClickListener)
        btnNeither?.setOnClickListener(commonOnClickListener)
        btnDisagree?.setOnClickListener(commonOnClickListener)

        return root
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}