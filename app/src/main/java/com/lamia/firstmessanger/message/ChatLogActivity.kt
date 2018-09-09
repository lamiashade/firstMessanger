package com.lamia.firstmessanger.message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.lamia.firstmessanger.R
import com.lamia.firstmessanger.models.ChatFromItem
import com.lamia.firstmessanger.models.ChatMessage
import com.lamia.firstmessanger.models.ChatToItem
import com.lamia.firstmessanger.models.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {

    val Tag = "ChatLogActivity"
    val adapter = GroupAdapter<ViewHolder>()
    var toUser:User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        chatlog_recylcler_view.adapter = adapter

        toUser = intent.getParcelableExtra<User>("USER_KEY")

        supportActionBar?.title = toUser?.userName

        //retrieves messages from other user
        listenForMessages()

        chatlog_btn.setOnClickListener {
            Log.d(Tag,"Subitmitted chat text...")
            performSendMessage()
        }
    }

    private fun listenForMessages(){
        val ref = FirebaseDatabase.getInstance().getReference("/messages")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatmesg  = p0.getValue(ChatMessage::class.java)

                if(chatmesg != null){
                    Log.d(Tag,chatmesg.text)

                    if(chatmesg.fromId == FirebaseAuth.getInstance().uid){
                        val currentUser = MessageActivity.currentUser ?: return
                        adapter.add(ChatFromItem(chatmesg.text,currentUser))
                    }else{
                        val toUser = intent.getParcelableExtra<User>("USER_KEY")
                        adapter.add(ChatToItem(chatmesg.text,toUser))

                    }
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }


        })
    }

    private fun performSendMessage(){
        val text = chatlog_enter_txt.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        toUser = intent.getParcelableExtra<User>("USER_KEY")
        val toId = toUser?.uid

        if (fromId == null) return

        val ref = FirebaseDatabase.getInstance().getReference("/messages").push()

        val chatmsg = ChatMessage(ref.key!!,text,fromId,toId!!,System.currentTimeMillis()/1000)
        ref.setValue(chatmsg)
                .addOnSuccessListener {
                    Log.d(Tag,"Successfully saved chat message: ${ref.key}")
                }
    }
}