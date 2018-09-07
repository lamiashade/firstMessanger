package com.lamia.firstmessanger.message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lamia.firstmessanger.R
import com.lamia.firstmessanger.models.User
import com.lamia.firstmessanger.models.UserItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessage : AppCompatActivity() {
    val Tag = "NewMessage Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "select User"

        fetchUsers()
    }

    private fun fetchUsers(){
        val ref = FirebaseDatabase.getInstance().getReference("/users")

        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach{
                   Log.d(Tag, it.toString())
                    val user = it.getValue(User::class.java)

                   if(user != null) {
                       adapter.add(UserItem(user))
                   }
               }
                new_msg_recycler_view.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })

    }
}
