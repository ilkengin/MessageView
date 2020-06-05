package com.ilkengin.messageview

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import com.ilkengin.messageview.model.Message

class MessageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private var parentActivity: Activity
    private var chatTitle: String = ""
    private var messages: Array<Message> = emptyArray()
    private var onMessageSentListener: OnMessageSentListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.message_view, this, true)
        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.message_view_attributes, 0, 0)
            this.chatTitle = resources.getText(typedArray.getResourceId(R.styleable.message_view_attributes_chat_title, R.string.chat_title)).toString()
            typedArray.recycle()
        }
        val sendTextView = findViewById<EditText>(R.id.messageToSend)
        findViewById<ImageButton>(R.id.sendButton).setOnClickListener {  view ->
            if (!sendTextView.text.isEmpty()) {
                onMessageSentListener?.onMessageSent(sendTextView.text.toString())
            }
        }
        this.parentActivity = context as Activity
    }

    fun setOnMessageSentListener(_onMessageSentListener: OnMessageSentListener) {
        this.onMessageSentListener = _onMessageSentListener
    }

    fun setMessages(_messages: Array<Message>) {
        this.messages = _messages
        this.initListView()
    }

    private fun initListView() {
        if (this.parentActivity != null) {
            val listView = findViewById<ListView>(R.id.sentMessages)
            val messageListAdapter = MessageListAdapter(this.parentActivity, this.messages)
            listView.adapter = messageListAdapter

            listView.setOnItemClickListener(){adapterView, view, position, id ->
                val itemAtPos = adapterView.getItemAtPosition(position)
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                Toast.makeText(this.context, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
            }
        }
    }

    interface OnMessageSentListener {
        fun onMessageSent(message: String)
    }
}