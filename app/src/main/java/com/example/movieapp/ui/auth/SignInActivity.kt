package com.example.movieapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieapp.ui.HomeActivity
import com.example.movieapp.R
import com.example.movieapp.model.User
import com.example.movieapp.utils.Preferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignInActivity : AppCompatActivity() {
    private lateinit var userRef: DatabaseReference
    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Database with the correct URL
         userRef = FirebaseManager.getDatabaseReference()
        Log.d("SignInActivity", "Firebase Database initialized")

        preference = Preferences(this)

        preference.setValue("onboarding", "1")
        if (preference.getValues("status") == "1"){
            finishAffinity()
            var goHome = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        val btnSignIn = findViewById<Button>(R.id.btn_sign_in)
        btnSignIn.setOnClickListener {
            val etUsername = findViewById<EditText>(R.id.et_username)
            val etPassword = findViewById<EditText>(R.id.et_password)

            val iUsername = etUsername.text.toString().trim()
            val iPassword = etPassword.text.toString().trim()

            if (iUsername.isEmpty()) {
                etUsername.error = "Silakan masukkan username Anda"
                etUsername.requestFocus()
                Log.d("SignInActivity", "Username is empty")
            } else if (iPassword.isEmpty()) {
                etPassword.error = "Silakan masukkan password Anda"
                etPassword.requestFocus()
                Log.d("SignInActivity", "Password is empty")
            } else {
                Log.d("SignInActivity", "Attempting login with username: $iUsername")
                pushLogin(iUsername, iPassword)
            }
        }


        val btnSignUp = findViewById<Button>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        userRef.child(iUsername).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG).show()
                Log.e("SignInActivity", "Database error: ${databaseError.message}")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("SignInActivity", "DataSnapshot received: $dataSnapshot")
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_LONG).show()
                    Log.d("SignInActivity", "User not found")
                } else {
                    Log.d("SignInActivity", "User found: ${user.username}")
                    if (user.password == iPassword) {

                        preference.setValue("nama", user.nama.toString())
                        preference.setValue("username", user.username.toString())
                        preference.setValue("email", user.email.toString())
                        preference.setValue("url", user.url.toString())
                        preference.setValue("saldo", user.saldo.toString())
                        preference.setValue("status", "1")


                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        Log.d("SignInActivity", "Login successful, navigating to HomeActivity")
                    } else {
                        Toast.makeText(this@SignInActivity, "Username / Password salah", Toast.LENGTH_LONG).show()
                        Log.d("SignInActivity", "Password mismatch")
                    }
                }
            }
        })
    }
}