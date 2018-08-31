package com.lamia.firstmessanger

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity:AppCompatActivity(){

    val Tag = "LoginActivity"

    var loginBtn:Button? = null
    var loginEmail:TextView? = null
    var loginPassword:TextView? = null
    var create_acc:TextView? = null

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = login_btn
        loginEmail = login_email
        loginPassword = login_password
        create_acc = login_create_an_account

        mAuth = FirebaseAuth.getInstance()

        //login User
        loginBtn!!.setOnClickListener(){


        }

        // Switch to Create an account
        create_acc!!.setOnClickListener(){
            Log.d(Tag,"Switch to RegisterActivity")

            var createAcc = Intent(this,RegisterActivity::class.java)
            startActivity(createAcc)


        }
    }

    private fun signIn(){

        val email = loginEmail!!.text.toString()
        val password = loginPassword!!.text.toString()

        mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(){
            if(!it.isSuccessful) return@addOnCompleteListener


            Log.d(Tag, "Log in: Success")
        }  .addOnFailureListener(){
            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
            Log.d(Tag, "Failed to log in: ${it.message}")
        }

    }


}