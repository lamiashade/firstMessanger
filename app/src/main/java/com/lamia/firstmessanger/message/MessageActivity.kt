package com.lamia.firstmessanger.message

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.lamia.firstmessanger.Register.LoginActivity
import com.lamia.firstmessanger.R

class MessageActivity : AppCompatActivity() {
    val Tag = "MessageActivity"

    var mAuth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        mAuth = FirebaseAuth.getInstance()

       loginCheck()
    }

    private fun loginCheck(){
        Log.d(Tag,"Login Check")

       var  currentUser = mAuth?.currentUser

        if(currentUser == null){
            val loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(loginIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.menu_new_msg ->{

                val newMsgIntent = Intent(this, NewMessage::class.java)
                startActivity(newMsgIntent)

            }

            R.id.menu_sign_out ->{
                mAuth?.signOut()

                val loginIntent = Intent(this, LoginActivity::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(loginIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
