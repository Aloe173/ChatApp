package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.ChatMember

interface IChatMember {

    fun create(member: ChatMember): ChatMember

    fun delete(member: ChatMember): ChatMember

}