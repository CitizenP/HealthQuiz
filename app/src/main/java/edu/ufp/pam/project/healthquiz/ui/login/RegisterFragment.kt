package edu.ufp.pam.project.healthquiz.ui.login

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
import edu.ufp.pam.project.healthquiz.R
import edu.ufp.pam.project.healthquiz.databinding.FragmentLoginBinding
import edu.ufp.pam.project.healthquiz.databinding.FragmentRegisterBinding
import edu.ufp.pam.project.healthquiz.db.QuizDatabase
import edu.ufp.pam.project.healthquiz.db.users.User
import edu.ufp.pam.project.healthquiz.db.users.UserRepository
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = QuizDatabase.getDatabaseInstance(application).userDao
        val userRepo = UserRepository(dao)
        loginViewModel = LoginViewModel(userRepo)

        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this
        val root = binding.root

        val buttonSave = root.findViewById<Button>(R.id.btnNewUserRegister)
        val buttonCancel = root.findViewById<Button>(R.id.btnNewUserCancel)

        buttonSave?.setOnClickListener{
            val newName = root.findViewById<EditText>(R.id.edtNewUserName)
            val newUsername = root.findViewById<EditText>(R.id.edtNewUserUsername)
            val newPassword = root.findViewById<EditText>(R.id.edtNewUserPassword)
            val thisFragment = this
            lifecycleScope.launch {
                if (newName.text == null || newUsername.text == null || newPassword.text == null
                    || newName.text.toString() == "" || newUsername.text.toString() == "" || newPassword.text.toString() == "") {
                    Toast.makeText(
                        requireContext(),
                        "Please complete your register before submitting.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val existingUser = userRepo.loadUserByUsername(newUsername.text.toString())
                    Log.i(this.javaClass.simpleName, "Found user with username '${existingUser?.username}'")
                    if (existingUser != null) {
                        Toast.makeText(
                            requireContext(),
                            "Username already exists. Please choose another.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val user = User(0, newName.text.toString(), newUsername.text.toString(), newPassword.text.toString())
                        userRepo.insertUser(user)
                        NavHostFragment.findNavController(thisFragment).navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                }
            }
        }

        buttonCancel?.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return root
    }
}