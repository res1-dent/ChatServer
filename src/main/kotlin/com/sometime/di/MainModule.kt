package com.sometime.di

import com.sometime.data.MessageDataSource
import com.sometime.data.MessageDataSourceImpl
import com.sometime.data.UsersDataSource
import com.sometime.data.UsersDataSourceImpl
import com.sometime.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient(System.getenv("MONGODB_URI"))
            .coroutine
            .getDatabase("message_db")
    }
   /* single {
        KMongo.createClient(System.getenv("MONGODB_URI"))
            .coroutine
            .getDatabase("chats")
    }*/
    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }
    single {
        RoomController(get())
    }
    single {
        UsersDataSourceImpl(get())
    }
}