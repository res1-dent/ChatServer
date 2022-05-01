package com.sometime.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@kotlinx.serialization.Serializable
data class User(
    @BsonId
    val id: String = ObjectId().toString(),
    val name: String
)
