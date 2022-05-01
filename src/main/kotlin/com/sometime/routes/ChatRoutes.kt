package com.sometime.routes

import com.sometime.data.model.Message
import com.sometime.room.MemberAlreadyExistsException
import com.sometime.room.RoomController
import com.sometime.room.members
import com.sometime.sessions.ChatSession
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.chatSocket(roomController: RoomController, ) {
    webSocket(path = "/chat-socket") {
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        try {
            roomController.onJoin(
                session.username,
                session.sessionId,
                this
            )
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    roomController.sendMessage(session.username, frame.readText(), session.sessionId)
                }
            }
        } catch (e: MemberAlreadyExistsException) {
            call.respond(HttpStatusCode.Conflict)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            roomController.tryDisconnect(session.username)
        }
    }
}



fun Route.getAllMessages(roomController: RoomController) {
    get("/messages") {
        val messages = roomController.getAllMessages()
        call.application.environment.log.error("messages = $messages")
        call.respond(
            HttpStatusCode.OK,
            roomController.getAllMessages()
        )
    }
}