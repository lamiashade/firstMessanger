package com.lamia.firstmessanger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    val Tag = "RegisterActivity"

    var reg_btn:Button? = null
    var regUserName:TextView? = null
    var regEmail:TextView? = null
    var regPassword:TextView? = null
    var already_registered: TextView?= null

    var mAuth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        reg_btn = register_btn_main
        already_registered = already_have_account_textView
        mAuth = FirebaseAuth.getInstance()

        reg_btn!!.setOnClickListener() {

            performRegister()
        }

        already_registered!!.setOnClickListener(){
            Log.d(Tag,"Switch to LoginActivity")

            //Launch login Activity
            var launchLogin = Intent(this,LoginActivity::class.java)
            startActivity(launchLogin)

        }

    }

    private fun performRegister(){

        var email = regEmail!!.text.toString()
        var password = regPassword!!.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter an email or Password", Toast.LENGTH_LONG).show()
            return
        }

        Log.d(Tag,"Attempting to Log in Username: $email and Password: $password")

        mAuth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if (!it.isSuccessful) return@addOnCompleteListener

                    Log.d(Tag, "Created user: Success")
                }
                .addOnFailureListener(){
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                    Log.d(Tag, "Failed to create user: ${it.message}")
                }
    }
}
