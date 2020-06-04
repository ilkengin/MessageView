package com.ilkengin.messageview.model

class Message {
    var id: Int = 0
    var senderName: String = ""
    var text: String = ""

    constructor(_id: Int, _senderName: String, _text: String) {
        this.id = _id
        this.senderName = _senderName
        this.text = _text
    }
}