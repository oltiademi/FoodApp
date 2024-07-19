package com.example.foodapp.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.foodapp.MainActivity
import com.example.foodapp.databinding.ActivityLoginBinding
import com.example.foodapp.model.User
import com.example.foodapp.ui.signup.SignUpActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        FirebaseApp.initializeApp(this)

        database =
            FirebaseDatabase.getInstance("https://foodapp-616ab-default-rtdb.europe-west1.firebasedatabase.app").reference

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        binding.loginButton.setOnClickListener {
            val username = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser()
            } else {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val userRef = database.child("users").child(it.uid)
                        userRef.get().addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                val userData = snapshot.getValue(User::class.java)
                                Log.d("SignIn", "User data: $userData")
                            } else {
                                Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed to retrieve user data", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    fun verifyPassword(plainTextPassword: String, hashedPassword: String?): Boolean {
        val result = BCrypt.verifyer().verify(plainTextPassword.toCharArray(), hashedPassword)
        return result.verified
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
