package com.ilkengin.messageview.model

class Message {
    var id: Int = 0
    var text: String = ""
    var from: String = ""
    var type: MessageType = MessageType.RECEIEVED
    var status: MessageDeliveryStatus = MessageDeliveryStatus.NA


    constructor(_id: Int, _text: String, _status: MessageDeliveryStatus = MessageDeliveryStatus.SENDING) {
        this.id = _id
        this.text = _text
        this.from = ""
        this.type = MessageType.SENT
        this.status = _status
    }

    constructor(_id: Int, _text: String, _from: String) {
        this.id = _id
        this.text = _text
        this.from = _from
        this.type = MessageType.RECEIEVED
        this.status = MessageDeliveryStatus.NA
    }
}