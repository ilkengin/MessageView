package com.ilkengin.messageview

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ilkengin.messageview.model.Message
import com.ilkengin.messageview.model.MessageType

class MessageListAdapter(private val context: Activity, private val messages: MutableList<Message>) : ArrayAdapter<Message>(context, R.layout.message_item_received, messages) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val message = messages[position]
        val layout = if (message.type == MessageType.RECEIEVED) {
            R.layout.message_item_received
        } else {
            R.layout.message_item_sent
        }
        val rowView: View = inflater.inflate(layout, null, true)

        if (message.type == MessageType.RECEIEVED) {
            val senderTextView = rowView.findViewById<TextView>(R.id.received_message_sender)
            val messageTextView = rowView.findViewById<TextView>(R.id.received_message_text)

            senderTextView.text = messages[position].senderName
            messageTextView.text = messages[position].text
        } else {
            val messageTextView = rowView.findViewById<TextView>(R.id.sent_message_text)

            messageTextView.text = messages[position].text
        }
        return rowView
    }
}