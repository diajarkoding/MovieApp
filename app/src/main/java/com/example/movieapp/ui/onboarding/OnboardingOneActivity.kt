package com.example.movieapp.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieapp.R
import com.example.movieapp.ui.auth.SignInActivity
import com.example.movieapp.utils.Preferences

class OnboardingOneActivity : AppCompatActivity() {
    lateinit var preference: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding_one)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        preference = Preferences(this)
        if (preference.getValues("onboarding") == "1") {
            finishAffinity()
            var goSignIn = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(goSignIn)
        }

        val btnNext = findViewById<Button>(R.id.btn_next)
        btnNext.setOnClickListener{
            val intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }

        val btnSkip = findViewById<Button>(R.id.btn_skip)
        btnSkip.setOnClickListener {
            preference.setValue("onboarding", "1")

            finishAffinity()
            val intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}