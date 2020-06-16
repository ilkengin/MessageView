# MessageView
Android library written in Kotlin for showing and sending messages

## Currently available features (v0.1.2)
- Showing list of messages
- Sending messages
- Removing messages
- Setting the title of the messaging screen
- Updating the status of a message
- Customizing background

## How to integrate the library in your app?

```
 <com.ilkengin.messageview.MessageView
        android:id="@+id/messageView"
        android:layout_width="0dp"
        android:layout_height="250dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        mv:chat_title="Custom Chat Title"
        mv:chat_background="@drawable/my_custom_chat_bg"/>
```

## How to provide data to the view.

```
class MainActivity : AppCompatActivity(), MessageView.OnMessageSentListener, MessageView.OnMessageDeletedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val messageView = findViewById<MessageView>(R.id.messageView)
        messageView.setMessages(
            mutableListOf(
                Message(0, "Hello, World!", Calendar.getInstance().time, "Sender"),
                Message(1, "What's up?", Calendar.getInstance().time, MessageDeliveryStatus.READ)
            )
        )
        messageView.setOnMessageSentListener(this)
        messageView.setOnMessageDeletedListener(this)
    }
    
    // This method is called whenever a new message is sent by the user
    override fun onMessageSent(message: String) {
        Toast.makeText(this,"Message to send: ${message}", Toast.LENGTH_LONG).show()
    }
    // This method is called whenever a message is deleted by the user
    override fun onMessageDeleted(message: Message, deleteType: DeleteType) {
        val deletedFor = if (deleteType == DeleteType.DeleteForEveryone) { 
            "everyone"
        } else { 
            "me"
        }
        Toast.makeText(this,"Message deleted: ${message.text} for ${deletedFor}", Toast.LENGTH_LONG).show()
    }
}
```