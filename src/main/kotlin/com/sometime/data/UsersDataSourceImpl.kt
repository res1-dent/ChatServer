package com.sometime.data

import com.sometime.data.model.Message
import com.sometime.data.model.User
import com.sometime.room.Member
import org.litote.kmongo.coroutine.CoroutineDatabase

class UsersDataSourceImpl(
    private val db: CoroutineDatabase
) : UsersDataSource {

    private val users = db.getCollection<User>()

    override suspend fun getAllUsers(username: String): List<User> {
        return users
            .find()
            .toList()
            .filter { it.name != username }
    }

    override suspend fun insertUser(user: User): Boolean {
        return if (users.findOne(user.name) != null) {
            users.insertOne(user)
            true
        } else {
            false
        }
    }
}