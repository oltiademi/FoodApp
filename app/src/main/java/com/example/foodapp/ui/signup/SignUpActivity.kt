package com.example.foodapp.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.foodapp.MainActivity
import com.example.foodapp.databinding.ActivitySignupBinding
import com.example.foodapp.model.User
import com.example.foodapp.ui.login.LoginActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        FirebaseApp.initializeApp(this)
        database =
            FirebaseDatabase.getInstance("https://foodapp-616ab-default-rtdb.europe-west1.firebasedatabase.app").reference
        println("database $database")

        binding.signupButton.setOnClickListener {
            signUp()
        }
//        binding.signupButton.setOnClickListener {
//            val username = binding.etUsername.text.toString()
//            val email = binding.etEmail.text.toString()
//            val phone = binding.etPhone.text.toString()
//            val password = binding.etPassword.text.toString()
//
//            if (username.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
//                val hashedPassword = hashPassword(password)
//                val userId = database.child("users").push().key
//                if (userId != null) {
//                    addUser(userId, username, email, phone, hashedPassword)
//                } else {
//                    Toast.makeText(this, "Error generating user ID", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        binding.login.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
//    }
//
//    private fun hashPassword(password: String): String {
//        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
//    }
//
//    private fun addUser(
//        userId: String,
//        username: String,
//        email: String,
//        phone: String,
//        password: String
//    ) {
//        val user = User(username, email, phone, password)
//        database.child("users").child(userId).setValue(user)
//            .addOnSuccessListener {
//                startActivity(Intent(this, MainActivity::class.java))
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(this, "Error adding user: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//    }
    }

    private fun signUp() {
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()
        val password = binding.etPassword.text.toString()

        auth.createUserWithEmailAndPassword(
            email, password
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                val user = auth.currentUser
                val hashedPassword = hashPassword(password)
                println("Register successfull ${user.toString()}")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)


                user?.let {
                    val userRef = database.child("users").child(it.uid)
                    userRef.setValue(User(username, email, phone, hashedPassword))
                }
            } else {
                Toast.makeText(
                    baseContext,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }
}
