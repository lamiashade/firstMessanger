package com.lamia.firstmessanger.models

import com.lamia.firstmessanger.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_message.view.*


class MessageRow: Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.message_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}