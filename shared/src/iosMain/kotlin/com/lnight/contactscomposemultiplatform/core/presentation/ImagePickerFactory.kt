package com.lnight.contactscomposemultiplatform.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

actual class ImagePickerFactory(
    private val rootController: UIViewController
) {

    @Composable
    actual fun createPicker(): ImagePicker {
        return remember {
            ImagePicker(rootController)
        }
    }
}