package com.rt04.myapplication.presentation.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.ActivityRegisterBinding
import com.rt04.myapplication.presentation.login.LoginActivity

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeComponen()
        auth = FirebaseAuth.getInstance()

    }

    private fun initializeComponen() {
        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_register -> {
                register()
            }

            R.id.btn_login -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun register() {
        val username = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val category = binding.spCategory.selectedItem.toString()

        binding.progressBar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userMap = hashMapOf(
                        "username" to username,
                        "email" to email,
                        "category" to category
                    )
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid

                    db.collection("user").document(userId).set(userMap)
                        .addOnSuccessListener {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Akun Berhasil Dibuat", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener { e ->
                            binding.progressBar.visibility = View.VISIBLE
                            Toast.makeText(
                                this,
                                "Gagal Membuat Akun ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                } else {
                    Log.e("REGIS", task.exception.toString())
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }
}