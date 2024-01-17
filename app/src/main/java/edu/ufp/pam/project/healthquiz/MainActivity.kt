package edu.ufp.pam.project.healthquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton =findViewById<Button>(R.id.btnLogin)
        loginButton.setOnClickListener{v : View? -> this.onLoginClick(v) }
    }

    private fun onLoginClick(v : View?) {
        Log.e(this.javaClass.simpleName, "onLoginClick(): v=${v?.toString()}")

        val username = findViewById<EditText>(R.id.edtUsername)
        val password = findViewById<EditText>(R.id.edtPassword)

        if (username.text.toString() == "admin" && password.text.toString() == "admin") {
            val intent = Intent(this, QuizNavDrawerActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "LOGIN FAILED!", Toast.LENGTH_SHORT).show()
        }
    }
}