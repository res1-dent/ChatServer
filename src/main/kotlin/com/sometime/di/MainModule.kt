package com.sometime.di

import com.sometime.data.MessageDataSource
import com.sometime.data.MessageDataSourceImpl
import com.sometime.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("mongodb+srv://res1dent:4652726p@cluster0.zr1iq.mongodb.net/Cluster0?retryWrites=true&w=majority")
            //.getDatabase("message_db")
    }
    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }
    single {
        RoomController(get())
    }
}