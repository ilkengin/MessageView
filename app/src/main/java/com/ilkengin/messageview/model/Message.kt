package com.ilkengin.messageview.model

class Message {
    var id: Int = 0
    var type: MessageType = MessageType.RECEIEVED
    var senderName: String = ""
    var text: String = ""

    constructor(_id: Int, _senderName: String, _type: MessageType, _text: String) {
        this.id = _id
        this.type = _type
        this.senderName = _senderName
        this.text = _text
    }
}