package com.sometime.plugins

import com.sometime.data.UsersDataSource
import com.sometime.data.UsersDataSourceImpl
import com.sometime.room.RoomController
import com.sometime.routes.chatSocket
import com.sometime.routes.getAllMessages
import com.sometime.routes.getAllUsers
import com.sometime.routes.insertUser
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.insertOne

fun Application.configureRouting() {
    install(Routing) {
        val roomController by inject<RoomController>()
        val usersDataSource: UsersDataSource by inject<UsersDataSourceImpl>()
        chatSocket(roomController)
        getAllMessages(roomController)
        getAllUsers(usersDataSource)
        insertUser(usersDataSource)
    }
}
