package com.rt04.myapplication.presentation.login

import android.content.Context
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
import com.rt04.myapplication.databinding.ActivityLoginBinding
import com.rt04.myapplication.presentation.main.MainActivity
import com.rt04.myapplication.presentation.register.RegisterActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeComponen()
        auth = FirebaseAuth.getInstance()
    }

    private fun initializeComponen() {
        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_login -> {
                login()
            }

            R.id.btn_register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun login() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        binding.progressBar.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    saveLoginInfo()
                    checkUserRole(auth.currentUser?.uid ?: "")

                } else {
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.VISIBLE
            }
    }

    private fun checkUserRole(userId: String) {

        db.collection("user").document(userId).get()
            .addOnSuccessListener { document ->
                val kategori = document.getString("category")
                Log.e("tes", "$kategori")
                if (document.exists()) {
                    if (kategori == "Warga" || kategori == "Ketua RT") {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveLoginInfo() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply() {
            putBoolean(IS_LOGIN, true)
        }.apply()
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isLogin = sharedPreferences.getBoolean(IS_LOGIN, false)
        if (isLogin) {
            val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intentToMain)
            finish()
        }
    }

    companion object {
        const val PREFS_NAME = "rt04_app_pref"
        const val IS_LOGIN = "login"
    }
}