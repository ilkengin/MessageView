package com.ilkengin.messageview.model

import java.util.*

class Message {
    var id: Int
    var text: String
    var sentTime: Date
    var from: String
    var type: MessageType
    var status: MessageDeliveryStatus

    constructor(_id: Int, _text: String, _sentTime: Date = Calendar.getInstance().time, _status: MessageDeliveryStatus = MessageDeliveryStatus.SENDING) {
        this.id = _id
        this.text = _text
        this.sentTime = _sentTime
        this.from = ""
        this.type = MessageType.SENT
        this.status = _status
    }

    constructor(_id: Int, _text: String, _sentTime: Date, _from: String) {
        this.id = _id
        this.text = _text
        this.sentTime = _sentTime
        this.from = _from
        this.type = MessageType.RECEIEVED
        this.status = MessageDeliveryStatus.NA
    }
}