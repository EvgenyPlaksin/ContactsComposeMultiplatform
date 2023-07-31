package com.lnight.contactscomposemultiplatform.di

import android.content.Context
import com.lnight.contactscomposemultiplatform.contacts.data.SqlDelightContactDataSource
import com.lnight.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.lnight.contactscomposemultiplatform.core.data.DatabaseDriverFactory
import com.lnight.contactscomposemultiplatform.core.data.ImageStorage
import com.lnight.contactscomposemultiplatform.database.ContactDatabase

actual class AppModule(
    private val context: Context
) {

    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(
            db = ContactDatabase(
                driver = DatabaseDriverFactory(context).create()
            ),
            imageStorage = ImageStorage(context)
        )
    }
}