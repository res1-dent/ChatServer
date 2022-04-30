package com.sometime.room

class MemberAlreadyExistsException : Exception(
    "There is already a member with that username it the room"
)