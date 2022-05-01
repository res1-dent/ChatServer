package com.sometime.plugins

import com.sometime.data.UsersDataSourceImpl
import com.sometime.room.RoomController
import com.sometime.routes.chatSocket
import com.sometime.routes.getAllMessages
import com.sometime.routes.getAllUsers
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.insertOne

fun Application.configureRouting() {
    install(Routing){
        val roomController by inject<RoomController>()
        val usersDataSource by inject<UsersDataSourceImpl>()
        chatSocket(roomController)
        getAllMessages(roomController)
        getAllUsers(usersDataSource)

    }
}
