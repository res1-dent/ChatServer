package com.sometime.data

import com.sometime.data.model.User
import com.sometime.room.Member

interface UsersDataSource {

    suspend fun getAllUsers(username:String): List<User>

    suspend fun insertUser(user: User): Boolean


}