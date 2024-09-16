package com.example.newintent

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newintent.LoginPage
import com.example.newintent.R
import com.example.newintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setupPasswordToggle()

            btnRegister.setOnClickListener {
                val username = rusername.text.toString()
                val email = remail.text.toString()
                val phone = rphone.text.toString()
                val password = rpassword.text.toString()

                if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else if (!termsCheckbox.isChecked) {
                    Toast.makeText(this@MainActivity, "You must agree to the terms", Toast.LENGTH_SHORT).show()
                } else {
                    val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putString("username", username)
                    editor.putString("email", email)
                    editor.putString("phone", phone)
                    editor.putString("password", password)
                    editor.apply()

                    val intent = Intent(this@MainActivity, LoginPage::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            setLoginText()
        }
    }

    private fun setupPasswordToggle() {
        binding.rpassword.setOnTouchListener { v, event ->
            val drawableEnd = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.rpassword.right - binding.rpassword.compoundDrawables[drawableEnd].bounds.width())) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {

            binding.rpassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.rpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.baseline_lock_24, 0, R.drawable.eye_icon_close, 0
            )
        } else {

            binding.rpassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.rpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.baseline_lock_24, 0, R.drawable.eye_open, 0
            )
        }

        binding.rpassword.setSelection(binding.rpassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }

    private fun setLoginText() {
        val text = "Already Have an Account? Log In"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@MainActivity, LoginPage::class.java)
                startActivity(intent)
            }
        }

        spannableString.setSpan(
            ForegroundColorSpan(Color.BLUE),
            25, 31,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            clickableSpan,
            25, 31,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        with(binding) {
            tvlogin.text = spannableString
            tvlogin.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
