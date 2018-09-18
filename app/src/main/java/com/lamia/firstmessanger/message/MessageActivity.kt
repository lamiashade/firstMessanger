package com.lamia.firstmessanger.message

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.lamia.firstmessanger.Register.LoginActivity
import com.lamia.firstmessanger.R
import com.lamia.firstmessanger.models.ChatMessage
import com.lamia.firstmessanger.models.MessageRow
import com.lamia.firstmessanger.models.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity() {
    val Tag = "MessageActivity"

    var mAuth:FirebaseAuth? = FirebaseAuth.getInstance()

    companion object {
        var currentUser: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        messages_recycler_view.adapter = adapter
        messages_recycler_view.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        mAuth = FirebaseAuth.getInstance()

        loginCheck()
        fetchCurrentUser()
        listenForLatestMessages()

        }

    val adapter = GroupAdapter<ViewHolder>()

    val latestMsgMap = HashMap<String, ChatMessage>()

    private fun refreshRecyclerView(){
        adapter.clear()
        latestMsgMap.values.forEach{
            adapter.add(MessageRow(it))
        }
    }

    private fun listenForLatestMessages(){
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/lates-message/$fromId")
        ref.addChildEventListener(object:ChildEventListener{
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMsg = p0.getValue(ChatMessage::class.java) ?: return
                latestMsgMap[p0.key!!] = chatMsg
                refreshRecyclerView()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMsg = p0.getValue(ChatMessage::class.java) ?: return
                latestMsgMap[p0.key!!] = chatMsg
                refreshRecyclerView()
            }
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }
            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
    }

    private fun fetchCurrentUser(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
                Log.d(Tag, "Current usr ${currentUser?.userName}")
            }

        })

    }

    private fun loginCheck(){
        Log.d(Tag,"Login Check")

       var  logUser = mAuth?.currentUser

        if(logUser == null){
            val loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(loginIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.menu_new_msg ->{

                val newMsgIntent = Intent(this, NewMessageActivity::class.java)
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
