package edu.ufp.pam.project.healthquiz.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import edu.ufp.pam.project.healthquiz.QuizNavDrawerActivity
import edu.ufp.pam.project.healthquiz.R
import edu.ufp.pam.project.healthquiz.databinding.FragmentLoginBinding
import edu.ufp.pam.project.healthquiz.db.QuizDatabase
import edu.ufp.pam.project.healthquiz.db.users.UserRepository
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = QuizDatabase.getDatabaseInstance(application).userDao
        val userRepo = UserRepository(dao)
        loginViewModel = LoginViewModel(userRepo)

        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        val root = binding.root
        val buttonLogin = root.findViewById<Button>(R.id.btnLogin)
        val buttonNewUser = root.findViewById<Button>(R.id.btnNewUser)
        val buttonForgetPass = root.findViewById<Button>(R.id.btnForgetPass)

        buttonLogin?.setOnClickListener{ view ->
            val edtUsername = root.findViewById<EditText>(R.id.edtUsername)
            val edtPassword = root.findViewById<EditText>(R.id.edtPassword)
            if (edtUsername.text == null || edtPassword.text == null) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val username = edtUsername.text.toString()
                    val user = userRepo.loadUserByUsername(username)
                    Log.i(this.javaClass.simpleName, "Found user with username '${user?.username}'")
                    if (user != null) {
                        if (user.password == edtPassword.text.toString()) {
                            val intent = Intent(root.context, QuizNavDrawerActivity::class.java)

                            // Pass the user ID to the QuizFragment
                            intent.putExtra("USER_ID", user.id)

                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Password is incorrect.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "User doesn't exist. Please Register!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        buttonNewUser?.setOnClickListener{ view ->
            Log.d(this.javaClass.simpleName, "Navigating from Login to Register fragment")
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        buttonForgetPass?.setOnClickListener{
            val edtUsername = root.findViewById<EditText>(R.id.edtUsername)
            if (edtUsername.text == null || edtUsername.text.toString() == "") {
                Toast.makeText(
                    requireContext(),
                    "Please introduce your username.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                lifecycleScope.launch {
                    val username = edtUsername.text.toString()
                    val user = userRepo.loadUserByUsername(username)
                    Toast.makeText(
                        requireContext(),
                        "Your password is '${user?.password}'",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        return binding.root
    }
}