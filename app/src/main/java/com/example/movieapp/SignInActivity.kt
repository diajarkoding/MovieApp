package com.example.movieapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieapp.R
import com.google.firebase.Firebase
import com.google.firebase.database.database

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSignIn = findViewById<Button>(R.id.btn_sign_in)
        btnSignIn.setOnClickListener {
            val database = Firebase.database("https://auth-demo-flutter-917eb-default-rtdb.asia-southeast1.firebasedatabase.app")
            val myRef = database.getReference("message")

            myRef.setValue("Hello, World!")
        }
    }
}