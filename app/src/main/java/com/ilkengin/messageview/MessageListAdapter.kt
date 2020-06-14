package com.ilkengin.messageview

import android.app.Activity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.text.style.ImageSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ilkengin.messageview.model.Message
import com.ilkengin.messageview.model.MessageDeliveryStatus
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

            senderTextView.text = message.from
            messageTextView.text = message.text
        } else {
            val messageTextView = rowView.findViewById<TextView>(R.id.sent_message_text)

            val sentIconImageResourceId = when (message.status) {
                MessageDeliveryStatus.SENDING -> {
                    R.drawable.loading_icon
                }
                MessageDeliveryStatus.SENT -> {
                    R.drawable.ic_done
                }
                MessageDeliveryStatus.DELIVERED -> {
                    R.drawable.ic_done_all
                }
                else -> {
                    R.drawable.ic_done_all_blue
                }
            }

            val ss = SpannableString(message.text)
            val d = ContextCompat.getDrawable(context, sentIconImageResourceId)
            d?.setBounds(0, 0, d?.intrinsicWidth, d?.intrinsicHeight)
            val span = ImageSpan(d!!, ImageSpan.ALIGN_BOTTOM)
            ss.setSpan(span, message.text.length, message.text.length + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            messageTextView.setText(ss)
        }
        return rowView
    }
}