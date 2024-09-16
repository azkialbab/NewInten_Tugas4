package com.example.newintent

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newintent.databinding.ActivityLoginPageBinding

class LoginPage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setupRegisterText()

            btnLogin.setOnClickListener {
                val username = lusername.text.toString()
                val password = lpassword.text.toString()

                val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
                val savedUsername = prefs.getString("username", "")
                val savedPassword = prefs.getString("password", "")

                if (username == savedUsername && password == savedPassword) {
                    val intent = Intent(this@LoginPage, HomePage::class.java).apply {
                        putExtra("username", savedUsername)
                        putExtra("email", prefs.getString("email", ""))
                        putExtra("phone", prefs.getString("phone", ""))
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginPage, "Username or Password is incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRegisterText() {
        val text = "New Member? Register"
        val spannableString = SpannableString(text)

        val registerSpan = object : ClickableSpan() {
            override fun onClick(widget: android.view.View) {
                val intent = Intent(this@LoginPage, MainActivity::class.java)
                startActivity(intent)
            }
        }

        spannableString.setSpan(
            ForegroundColorSpan(Color.BLUE),
            text.indexOf("Register"),
            text.indexOf("Register") + "Register".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            registerSpan,
            text.indexOf("Register"),
            text.indexOf("Register") + "Register".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        with(binding) {
            tvRegister.text = spannableString
            tvRegister.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
