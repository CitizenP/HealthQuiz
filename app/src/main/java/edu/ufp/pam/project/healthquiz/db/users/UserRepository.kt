package edu.ufp.pam.project.healthquiz.db.users

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class UserRepository(private val userDAO: UserDAO) {
    val allUsers: LiveData<List<User>> = userDAO.loadAllUsers()

    @WorkerThread
    suspend fun loadUserByUsername(username: String): User? {
        Log.i(this.javaClass.simpleName, "loadUserByUsername(): looking for user with username '$username'")
        return userDAO.loadUserByUsername(username)
    }

    @WorkerThread
    suspend fun insertUser(user: User): Long {
        Log.i(this.javaClass.simpleName, "insertUser(): inserting user $user")
        return userDAO.insertUser(user)
    }
}