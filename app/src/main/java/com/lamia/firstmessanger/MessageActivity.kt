package com.lamia.firstmessanger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MessageActivity : AppCompatActivity() {

    val mAuth:FirebaseAuth? = null
    var currentUser:FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

       loginCheck()

    }

    private fun loginCheck(){
        currentUser = mAuth?.currentUser

        if(currentUser == null){
            val loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(loginIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)

        when(item?.itemId){
            R.id.menu_new_msg->{

                val newMsgIntent = Intent(this, NewMessage::class.java)
                newMsgIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(newMsgIntent)

            }

            R.id.menu_sign_out->{
                mAuth?.signOut()

                val loginIntent = Intent(this, LoginActivity::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(loginIntent)

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
