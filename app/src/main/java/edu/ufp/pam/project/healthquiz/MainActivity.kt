package edu.ufp.pam.project.healthquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import edu.ufp.pam.project.healthquiz.db.QuizDatabase
import edu.ufp.pam.project.healthquiz.db.users.User
import edu.ufp.pam.project.healthquiz.db.users.UserDAO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED VARIABLE")
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)

        val userDao: UserDAO = QuizDatabase.getDatabaseInstance(this).userDao

        val user = User(0, "Admin", "admin", "admin")

        lifecycleScope.launch {
            val existingUser = userDao.loadUserByUsername("admin")
            if (existingUser == null) userDao.insertUser(user)
        }
    }
}