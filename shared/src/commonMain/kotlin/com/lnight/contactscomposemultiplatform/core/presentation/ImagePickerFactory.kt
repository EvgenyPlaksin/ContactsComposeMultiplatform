package com.lnight.contactscomposemultiplatform.core.presentation

import androidx.compose.runtime.Composable

expect class ImagePickerFactory {

    @Composable
    fun createPicker(): ImagePicker
}