package com.lnight.contactscomposemultiplatform.contacts.data

import com.lnight.contactscomposemultiplatform.contacts.domain.Contact
import com.lnight.contactscomposemultiplatform.core.data.ImageStorage
import database.ContactEntity

suspend fun ContactEntity.toContact(imageStorage: ImageStorage): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        photoBytes = imagePath?.let { imageStorage.getImage(it) }
    )
}