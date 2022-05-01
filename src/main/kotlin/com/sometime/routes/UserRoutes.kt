package com.sometime.routes

import com.sometime.data.UsersDataSource
import com.sometime.data.model.User
import com.sometime.room.Member
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.getAllUsers(usersDataSource: UsersDataSource) {
    get("/users") {
        val userName = call.request.queryParameters["username"]
        if (userName!=null) {
            call.respond(
                HttpStatusCode.OK,
                usersDataSource.getAllUsers(userName)
            )
        }
    }
}


fun Route.insertUser(usersDataSource: UsersDataSource) {
    get("/insert_user") {
        val userName = call.request.queryParameters["username"]
        if (userName == null) call.respond(HttpStatusCode.BadRequest)
        else {
            val user = User(
                name = userName
            )
            if (usersDataSource.insertUser(user))
                call.respond(HttpStatusCode.OK, "Cool")
            else {
                call.respond(HttpStatusCode.OK, "UserExist")
            }
        }
    }
}