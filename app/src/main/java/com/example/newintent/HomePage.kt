package com.example.newintent

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newintent.databinding.ActivityHomePageBinding

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val username = intent.getStringExtra("username")
            val email = intent.getStringExtra("email")
            val phone = intent.getStringExtra("phone")

            tusername.text = "Welcome $username"
            temail.text = "Your $email has been activated"
            tphone.text = "Phone: $phone has been registered"
        }
    }
}
