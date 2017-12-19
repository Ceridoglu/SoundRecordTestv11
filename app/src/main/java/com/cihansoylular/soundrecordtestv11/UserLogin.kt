package com.cihansoylular.soundrecordtestv11

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_login.*

class UserLogin : AppCompatActivity() {
    var mauth: FirebaseAuth = FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        loginButton.setOnClickListener() {
            var emaillogin: String = userText.text.toString().trim()
            var passwordlogin: String = passText.text.toString().trim()

            if (TextUtils.isEmpty(emaillogin)) {
                Toast.makeText(this, "enter the email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(passwordlogin)) {
                Toast.makeText(this, "enter the password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mauth.signInWithEmailAndPassword(emaillogin, passwordlogin).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "succesfully login", Toast.LENGTH_LONG).show()
                }
            }
            fun mainPage(view: View) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("input", userText.text.toString())
                startActivity(intent)
            }

            mainPage(view = )

        }
            singupButton.setOnClickListener {
                fun crtUser (view: View) {
                    val intent = Intent(applicationContext, createUser::class.java)
                    startActivity(intent)
                }
            }

        }





}
