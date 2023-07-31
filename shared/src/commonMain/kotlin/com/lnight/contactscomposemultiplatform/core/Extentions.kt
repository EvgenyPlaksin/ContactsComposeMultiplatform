package com.lnight.contactscomposemultiplatform.core

fun String.truncateToLengthAndRemoveParagraphs(maxLength: Int = 48): String {
    val noParagraphs = this.replace("\n", "")
    return if (noParagraphs.length > maxLength) {
        noParagraphs.substring(0, maxLength)
    } else {
        noParagraphs
    }
}

fun String.truncateToLength(maxLength: Int): String {
    return if (this.length > maxLength) {
        this.substring(0, maxLength) + "..."
    } else {
        this
    }
}

fun String.filterLettersAndTruncate(maxLength: Int = 15): String {
    val filtered = this.filter { it.isDigit() || it == '+' }
    return filtered.truncateToLengthAndRemoveParagraphs(maxLength)
}

fun ByteArray.getImageType(): String? {
    return when {
        this.size < 8 -> null
        this[0] == 0xFF.toByte() && this[1] == 0xD8.toByte() && this[2] == 0xFF.toByte() -> ".jpg"
        this[0] == 0x89.toByte() && this[1] == 0x50.toByte() && this[2] == 0x4E.toByte() && this[3] == 0x47.toByte() && this[4] == 0x0D.toByte() && this[5] == 0x0A.toByte() && this[6] == 0x1A.toByte() && this[7] == 0x0A.toByte() -> ".png"
        this[0] == 0x47.toByte() && this[1] == 0x49.toByte() && this[2] == 0x46.toByte() -> ".gif"
        else -> null
    }
}
