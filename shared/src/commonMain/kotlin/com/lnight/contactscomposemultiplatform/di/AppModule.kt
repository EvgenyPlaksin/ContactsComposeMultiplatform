package com.lnight.contactscomposemultiplatform.di

import com.lnight.contactscomposemultiplatform.contacts.domain.ContactDataSource

expect class AppModule {
    val contactDataSource: ContactDataSource
}