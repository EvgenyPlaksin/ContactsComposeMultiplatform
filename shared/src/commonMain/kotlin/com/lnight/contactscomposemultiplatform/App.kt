package com.lnight.contactscomposemultiplatform

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.lnight.contactscomposemultiplatform.contacts.presentation.ContactsListScreen
import com.lnight.contactscomposemultiplatform.contacts.presentation.ContactsListViewModel
import com.lnight.contactscomposemultiplatform.core.presentation.ContactsTheme
import com.lnight.contactscomposemultiplatform.core.presentation.ImagePicker
import com.lnight.contactscomposemultiplatform.di.AppModule
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
    imagePicker: ImagePicker
) {
    ContactsTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        val viewModel = getViewModel(
            key = "contact-list-screen",
            factory = viewModelFactory {
                ContactsListViewModel(appModule.contactDataSource)
            }
        )
        val state by viewModel.state.collectAsState()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContactsListScreen(
                state = state,
                newContact = viewModel.newContact,
                onEvent = viewModel::onEvent,
                imagePicker = imagePicker
            )
        }
    }
}