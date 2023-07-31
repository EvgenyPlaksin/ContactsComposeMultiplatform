package com.lnight.contactscomposemultiplatform.contacts.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lnight.contactscomposemultiplatform.contacts.domain.Contact
import com.lnight.contactscomposemultiplatform.core.truncateToLength

@Composable
fun ContactPreviewItem(
    contact: Contact,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10))
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactPhoto(
            contact = contact,
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = contact.firstName.truncateToLength(10)
        )
    }
}