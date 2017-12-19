package com.cihansoylular.soundrecordtestv11

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_create_user.*

class createUser : AppCompatActivity() {
    var fbsignup: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        var email = userSaveText.text.toString().trim()
        var password = passSaveText.text.toString().trim()
        submitButton.setOnClickListener {
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "enter ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        fbsignup.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(this, "succesfully created account", Toast.LENGTH_SHORT)
                return@addOnCompleteListener
            } else {
                Toast.makeText(this, "errore!!", Toast.LENGTH_SHORT)
                return@addOnCompleteListener
            }

        }
    }

fun goLogin(view: View){
    val intent =Intent(applicationContext,UserLogin::class.java)
    startActivity(intent)}
}

