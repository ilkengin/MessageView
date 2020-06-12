package com.ilkengin.messageview

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import com.ilkengin.messageview.model.DeleteType
import com.ilkengin.messageview.model.Message
import com.ilkengin.messageview.model.MessageDeliveryStatus
import com.ilkengin.messageview.model.MessageType

class MessageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener, AdapterView.OnItemLongClickListener {

    private var messages: MutableList<Message> = mutableListOf()
    private var listViewAdapter: MessageListAdapter? = null
    private var onMessageSentListener: OnMessageSentListener? = null
    private var onMessageDeletedListener: OnMessageDeletedListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.message_view, this, true)
        orientation = VERTICAL

        val chatTitleView = findViewById<TextView>(R.id.chatTitle)
        val messagesListView = findViewById<ListView>(R.id.sentMessages)
        val rootView = findViewById<LinearLayout>(R.id.rootView)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.message_view_attributes, 0, 0)

            val chatTitle = typedArray.getString(R.styleable.message_view_attributes_chat_title)
            val background = typedArray.getDrawable(R.styleable.message_view_attributes_chat_background)
            if (!chatTitle.isNullOrEmpty()) {
                chatTitleView.text = chatTitle
            }
            if (background != null) {
                rootView.background = background
            }

            typedArray.recycle()
        }
        findViewById<ImageButton>(R.id.sendButton).setOnClickListener(this)
    }

    // onClick handler for send button
    override fun onClick(v: View?) {
        val sendText = findViewById<EditText>(R.id.messageToSend)
        if (sendText.text.isNotEmpty()) {
            this.messages.add(Message(messages.size, sendText.text.toString(), MessageDeliveryStatus.SENT))
            this.listViewAdapter?.notifyDataSetChanged()
            this.onMessageSentListener?.onMessageSent(sendText.text.toString())

            // clear the text after sending
            sendText.setText("")
        }
    }

    override fun onItemLongClick(parent: AdapterView<*>, view: View, position: Int, id: Long): Boolean {
        this.showDeleteMessageDialog(this.messages.get(position))
        return true
    }

    fun setOnMessageSentListener(_onMessageSentListener: OnMessageSentListener) {
        this.onMessageSentListener = _onMessageSentListener
    }

    fun setOnMessageDeletedListener(_onMessageDeletedListener: OnMessageDeletedListener) {
        this.onMessageDeletedListener = _onMessageDeletedListener
    }

    fun messageStatusChanged(messageId: Int, newStatus: MessageDeliveryStatus) {
        val message = this.messages.find { message -> message.id == messageId }
        message?.status = newStatus
        this.listViewAdapter?.notifyDataSetChanged()
    }

    fun setMessages(_messages: MutableList<Message>) {
        this.messages = _messages
        this.initListView()
    }

    private fun initListView() {
        val listView = findViewById<ListView>(R.id.sentMessages)
        this.listViewAdapter = MessageListAdapter(context as Activity, this.messages)
        listView.adapter = this.listViewAdapter

        listView.onItemLongClickListener = this
    }

    private fun showDeleteMessageDialog(deletedMsg: Message) {
        val dialog = Dialog(this.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        if (deletedMsg.type == MessageType.SENT) {
            dialog.setContentView(R.layout.confirmation_dialog_sent_message)
            val deleteForEveryoneBtn = dialog.findViewById(R.id.deleteForEverybodyBtn) as Button
            deleteForEveryoneBtn.setOnClickListener {
                this.onMessageDeletedListener?.onMessageDeleted(deletedMsg, DeleteType.DeleteForEveryone)
                this.messages.remove(deletedMsg)
                this.listViewAdapter?.notifyDataSetChanged()
                dialog.dismiss()
            }
        } else {
            dialog.setContentView(R.layout.confirmation_dialog_received_message)
        }

        val deleteForMeBtn = dialog.findViewById(R.id.deleteForMeBtn) as Button
        val cancelBtn = dialog.findViewById(R.id.cancelBtn) as TextView
        deleteForMeBtn.setOnClickListener {
            this.onMessageDeletedListener?.onMessageDeleted(deletedMsg, DeleteType.DeleteForMe)
            this.messages.remove(deletedMsg)
            this.listViewAdapter?.notifyDataSetChanged()
            dialog.dismiss()
        }

        cancelBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    interface OnMessageSentListener {
        fun onMessageSent(message: String)
    }

    interface OnMessageDeletedListener {
        fun onMessageDeleted(message: Message, deleteType: DeleteType)
    }
}