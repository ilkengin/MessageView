package com.ilkengin.messageview

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.ilkengin.messageview.model.Message
import com.ilkengin.messageview.model.MessageType

class MessageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private var messages: MutableList<Message> = mutableListOf()
    private var listViewAdapter: MessageListAdapter? = null
    private var onMessageSentListener: OnMessageSentListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.message_view, this, true)
        orientation = VERTICAL

        val chatTitleView = findViewById<TextView>(R.id.chatTitle)
        val messagesListView = findViewById<ListView>(R.id.sentMessages)
        val rootView = findViewById<LinearLayout>(R.id.rootView)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.message_view_attributes, 0, 0)

            val chatTitle = resources.getText(typedArray.getResourceId(R.styleable.message_view_attributes_chat_title, R.string.chat_title)).toString()
            chatTitleView.text = chatTitle

            typedArray.recycle()
        }
        findViewById<ImageButton>(R.id.sendButton).setOnClickListener(this)
    }

    // onClick handler for send button
    override fun onClick(v: View?) {
        val sendText = findViewById<EditText>(R.id.messageToSend)
        if (sendText.text.isNotEmpty()) {
            this.messages.add(Message(messages.size, "Me", MessageType.SENT, sendText.text.toString()))
            this.listViewAdapter?.notifyDataSetChanged()
            this.onMessageSentListener?.onMessageSent(sendText.text.toString())

            // clear the text after sending
            sendText.setText("")
        }
    }

    fun setOnMessageSentListener(_onMessageSentListener: OnMessageSentListener) {
        this.onMessageSentListener = _onMessageSentListener
    }

    fun setMessages(_messages: MutableList<Message>) {
        this.messages = _messages
        this.initListView()
    }

    private fun initListView() {
        val listView = findViewById<ListView>(R.id.sentMessages)
        listView.transcriptMode = ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL
        listView.isStackFromBottom = true
        val messageListAdapter = MessageListAdapter(context as Activity, this.messages)
        listView.adapter = messageListAdapter
        this.listViewAdapter = messageListAdapter
        listView.setOnItemLongClickListener {adapterView, _, position, _ ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(this.context, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
            true
        }
    }

    interface OnMessageSentListener {
        fun onMessageSent(message: String)
    }
}