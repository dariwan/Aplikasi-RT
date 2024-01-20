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
import com.rt04.myapplication.core.utils.Constant
import com.rt04.myapplication.core.utils.Constant.KEY_EMAIL
import com.rt04.myapplication.core.utils.Constant.KEY_IS_LOGIN
import com.rt04.myapplication.core.utils.Constant.KEY_NAME
import com.rt04.myapplication.core.utils.Constant.KEY_ROLE
import com.rt04.myapplication.core.utils.Constant.KEY_TOKEN
import com.rt04.myapplication.core.utils.SessionManager
import com.rt04.myapplication.databinding.ActivityRegisterBinding
import com.rt04.myapplication.presentation.login.LoginActivity

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPref: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeComponen()
        auth = FirebaseAuth.getInstance()
        sharedPref = SessionManager(this)

    }

    private fun initializeComponen() {
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_register ->{
                register()
            }
        }
    }

    private fun register() {
        val username = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val category = binding.spCategory.selectedItem.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userMap = hashMapOf(
                        "username" to username,
                        "email" to email,
                        "category" to category
                    )
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                    sharedPref.apply {
                        setStringPref(KEY_TOKEN, userId)
                        setStringPref(KEY_NAME, username)
                        setStringPref(KEY_ROLE, category)
                        setStringPref(KEY_EMAIL, email)
                    }

                    db.collection("user").document(userId).set(userMap)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Akun Berhasil Dibuat", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {e ->
                            Toast.makeText(this, "Gagal Membuat Akun ${e.message}", Toast.LENGTH_SHORT).show()
                        }

                } else {
                    Log.e("REGIS", "${task.exception.toString()}")
                    Toast.makeText(this, "${task.exception.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}