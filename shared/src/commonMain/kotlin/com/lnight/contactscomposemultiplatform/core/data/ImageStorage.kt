package com.lnight.contactscomposemultiplatform.core.data

expect class ImageStorage {
    suspend fun saveImage(bytes: ByteArray): String
    suspend fun getImage(filename: String): ByteArray?
    suspend fun deleteImage(filename: String)
}