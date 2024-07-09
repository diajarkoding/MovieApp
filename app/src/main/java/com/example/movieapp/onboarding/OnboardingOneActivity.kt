package com.example.movieapp.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieapp.R
import com.example.movieapp.SignInActivity

class OnboardingOneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding_one)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnNext = findViewById<Button>(R.id.btn_next)
        btnNext.setOnClickListener{
            val intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }

        val btnSkip = findViewById<Button>(R.id.btn_skip)
        btnSkip.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}