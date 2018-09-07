package com.lamia.firstmessanger.models

import com.lamia.firstmessanger.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class UserItem(val user: User): Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.new_msg_user_name.text = user.userName
        Picasso.get().load(user.profilePic).into(viewHolder.itemView.new_msg_user_pic)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}