package com.lamia.firstmessanger.models

import com.lamia.firstmessanger.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_to_row_msg.view.*

class ChatToItem(val text:String, val toUser:User):Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_to_row_msg
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        //get user message
        viewHolder.itemView.chat_to_user_comment.text = text

        //load user porfile picture
        val uri = toUser.profilePic
        val targetImg = viewHolder.itemView.chat_to_user_image
        Picasso.get().load(uri).into(targetImg)
    }

}