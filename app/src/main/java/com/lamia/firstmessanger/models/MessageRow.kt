package com.lamia.firstmessanger.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lamia.firstmessanger.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.message_row.view.*


class MessageRow(val chatMsg:ChatMessage): Item<ViewHolder>(){

    var chatPartner:User? = null

    override fun getLayout(): Int {
        return R.layout.message_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        val chatFriend:String

        if(chatMsg.fromId == FirebaseAuth.getInstance().uid){
            chatFriend = chatMsg.toId
        }else{
            chatFriend = chatMsg.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatFriend")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                chatPartner = p0.getValue(User::class.java)
                viewHolder.itemView.message_row_user_name.text = chatPartner?.userName

                val userImg = viewHolder.itemView.message_row_user_pic
                Picasso.get().load(chatPartner?.profilePic).into(userImg)
            }


        })



        viewHolder.itemView.message_row_user_txt.text = chatMsg.text
    }

}