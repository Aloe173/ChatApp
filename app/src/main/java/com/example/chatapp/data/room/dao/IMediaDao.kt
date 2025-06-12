package com.example.chatapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chatapp.data.room.entity.Media

@Dao
interface IMediaDao {

    @Insert
    suspend fun insert(media: Media)

    @Update
    suspend fun update(media: Media)

    @Delete
    suspend fun delete(media: Media)

    @Query("SELECT * FROM media")
    suspend fun getAllMedia(): List<Media>

    @Query("SELECT * FROM media WHERE id_message = :idMessage")
    suspend fun getMediaByMessage(idMessage: Int): Media
}