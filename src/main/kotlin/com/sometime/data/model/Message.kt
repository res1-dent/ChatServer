package com.sometime.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.sql.Timestamp

@kotlinx.serialization.Serializable
data class Message(
    val text: String,
    val username: String,
    val timestamp: Long,
    @BsonId
    val id: String = ObjectId().toString(),
)
