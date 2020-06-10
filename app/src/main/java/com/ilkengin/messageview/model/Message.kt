package com.ilkengin.messageview.model

class Message {
    var id: Int = 0
    var type: MessageType = MessageType.RECEIEVED
    var status: MessageDeliveryStatus = MessageDeliveryStatus.SENT
    var senderName: String = ""
    var text: String = ""

    constructor(_id: Int, _senderName: String, _type: MessageType, _status: MessageDeliveryStatus, _text: String) {
        this.id = _id
        this.type = _type
        this.status = _status
        this.senderName = _senderName
        this.text = _text
    }
}