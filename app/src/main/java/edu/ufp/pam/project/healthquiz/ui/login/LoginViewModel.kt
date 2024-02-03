package edu.ufp.pam.project.healthquiz.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import edu.ufp.pam.project.healthquiz.db.users.User
import edu.ufp.pam.project.healthquiz.db.users.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class LoginViewModel(private val userRepo: UserRepository) : ViewModel() {
    val users: LiveData<List<User>> = userRepo.allUsers

    suspend fun findUserByUsername(username: String): User? {
        var user: User? = null
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(this.javaClass.simpleName, "findUserByUsername(): $username")
            user = userRepo.loadUserByUsername(username)
        }
        return user
    }

    suspend fun insertUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(this.javaClass.simpleName, "launch(): async insert new user ${user}")
        userRepo.insertUser(user)
    }
}

//class LoginViewModelFactory(private val userRepo: UserRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return LoginViewModel(userRepo) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}