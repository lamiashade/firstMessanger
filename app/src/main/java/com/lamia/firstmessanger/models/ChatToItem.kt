package com.lamia.firstmessanger.models

import com.lamia.firstmessanger.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_to_row_msg.view.*

class ChatToItem(val text:String):Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_to_row_msg
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.chat_to_user_comment.text = text
    }

}