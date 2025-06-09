package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.Right

interface IRight {
    fun create(right: Right): Right

    fun delete(rightId: Int): Boolean

    fun update(right: Right): Right?
}