package com.lnight.contactscomposemultiplatform

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController = ComposeUIViewController {
    val isDarkTheme = UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
            UIUserInterfaceStyle.UIUserInterfaceStyleDark
    App(
        darkTheme = isDarkTheme,
        dynamicColor = false
    )
}