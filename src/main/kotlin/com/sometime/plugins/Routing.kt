package com.sometime.plugins

import com.sometime.room.RoomController
import com.sometime.routes.chatSocket
import com.sometime.routes.getAllMessages
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    install(Routing){
        val roomController by inject<RoomController>()
        chatSocket(roomController)
        getAllMessages(roomController)
    }
}
