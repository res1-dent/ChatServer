package com.sometime.room

import com.sometime.data.MessageDataSource
import com.sometime.data.model.Message
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap


val members: ConcurrentHashMap<String, Member> = ConcurrentHashMap()

class RoomController(
    private val messageDataSource: MessageDataSource
) {

    fun onJoin(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (members.contains(username)) {
            throw MemberAlreadyExistsException()
        }
        members[username] = Member(
            username, sessionId, socket
        )
    }

    suspend fun sendMessage(senderUsername: String, message: String, chatId: String) {
        members.values.forEach { member: Member ->
            val messageEntity = Message(
                text = message,
                username = senderUsername,
                timestamp = System.currentTimeMillis(),
                chatId = chatId
            )
            messageDataSource.insertMessage(messageEntity)
            val parsedMessage = Json.encodeToString(messageEntity)
            member.socket.send(Frame.Text(parsedMessage))
        }
    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun tryDisconnect(username: String) {
        members[username]?.socket?.close()
        if (members.containsKey(username)) {
            members.remove(username)
        }
    }

}

