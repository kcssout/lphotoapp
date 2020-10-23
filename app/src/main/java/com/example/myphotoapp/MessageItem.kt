package com.example.myphotoapp

class MessageItem {
    var name: String? = null
    var message: String? = null
    var time: String? = null
    var profileUri: String? = null

    constructor(name: String?, message: String?, time: String?, profileUri: String?) {
        this.name = name
        this.message = message
        this.time = time
        this.profileUri = profileUri
    }

    constructor() {}

}