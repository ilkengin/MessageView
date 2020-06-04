package com.ilkengin.messageview

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ilkengin.messageview.model.Message

class MessageListAdapter(private val context: Activity, private val messages: Array<Message>) : ArrayAdapter<Message>(context, R.layout.message, messages) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.message, null, true)

        val senderTextView = rowView.findViewById(R.id.sender) as TextView
        val messageTextView = rowView.findViewById(R.id.message) as TextView

        senderTextView.text = messages[position].senderName
        messageTextView.text = messages[position].text

        return rowView
    }
}