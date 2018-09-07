package com.lamia.firstmessanger.Register

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.lamia.firstmessanger.R
import com.lamia.firstmessanger.message.MessageActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity:AppCompatActivity(){

    val Tag = "LoginActivity"

    var loginBtn:Button? = null
    var loginEmail:TextView? = null
    var loginPassword:TextView? = null
    var create_acc:TextView? = null
    var app_logo:ImageView? = null

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = login_btn
        loginEmail = login_email
        loginPassword = login_password
        create_acc = login_create_an_account
        app_logo = msg_logo

        mAuth = FirebaseAuth.getInstance()

        //login User
        loginBtn!!.setOnClickListener(){
            signIn()
        }

        // Switch to Create an account
        create_acc!!.setOnClickListener(){
            Log.d(Tag,"Switch to RegisterActivity")

            var createAcc = Intent(this, RegisterActivity::class.java)
            startActivity(createAcc)
        }
    }

    private fun signIn(){

        val email = loginEmail!!.text.toString()
        val password = loginPassword!!.text.toString()

        mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(){
            if(!it.isSuccessful) return@addOnCompleteListener

            Log.d(Tag, "Log in: Success")

            // change to messaging screen
            val messageIntent = Intent(this, MessageActivity::class.java)
            messageIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(messageIntent)

        }  .addOnFailureListener(){
            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
            Log.d(Tag, "Failed to log in: ${it.message}")
        }

    }


}