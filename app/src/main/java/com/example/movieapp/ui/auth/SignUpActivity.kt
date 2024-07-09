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
import com.example.movieapp.R
import com.example.movieapp.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class SignUpActivity : AppCompatActivity() {
    private lateinit var userRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userRef = FirebaseManager.getDatabaseReference()
        Log.d("SignInActivity", "Firebase Database initialized")

        var btnSignUp = findViewById<Button>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {
            val etUsername = findViewById<EditText>(R.id.et_username)
            val etPassword = findViewById<EditText>(R.id.et_password)
            val etName = findViewById<EditText>(R.id.et_name)
            val etEmail = findViewById<EditText>(R.id.et_email)


            val sUsername = etUsername.text.toString().trim()
            val sPassword = etPassword.text.toString().trim()
            val sName = etName.text.toString().trim()
            val sEmail = etEmail.text.toString().trim()



            when {
                sUsername.isEmpty() -> {
                    etUsername.error = "Silakan masukkan username Anda"
                    etUsername.requestFocus()
                    Log.d("SignInActivity", "Username is empty")
                }
                sPassword.isEmpty() -> {
                    etPassword.error = "Silakan masukkan password Anda"
                    etPassword.requestFocus()
                    Log.d("SignInActivity", "Password is empty")
                }
                sName.isEmpty() -> {
                    etName.error = "Silakan masukkan nama Anda"
                    etName.requestFocus()
                    Log.d("SignInActivity", "Name is empty")
                }
                sEmail.isEmpty() -> {
                    etEmail.error = "Silakan masukkan email Anda"
                    etEmail.requestFocus()
                    Log.d("SignInActivity", "Email is empty")
                }
                !isEmailValid(sEmail) -> {
                    etEmail.error = "Format email tidak valid"
                    etEmail.requestFocus()
                    Log.d("SignInActivity", "Invalid email format")
                }
                else -> {
                    Log.d("SignInActivity", "Attempting login with username: $sUsername")
                    saveUser(sUsername, sPassword, sName, sEmail)
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun saveUser(sUsername: String, sPassword: String, sName: String, sEmail: String) {
        var user = User()
        user.username = sUsername
        user.password = sPassword
        user.nama = sName
        user.email = sEmail

        if (sUsername != null) {
            checkingUsername(sUsername, user)
        }
    }

    private fun checkingUsername(sUsername: String, data: User) {
        userRef.child(sUsername).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan", Toast.LENGTH_LONG).show()
                } else {
                    userRef.child(sUsername).setValue(data)

                    val goPhotoScreen = Intent(this@SignUpActivity, SignUpPhotoScreenActivity::class.java)
                        .putExtra("nama", data.nama)
                    startActivity(goPhotoScreen)
                }
            }
        })
    }
}