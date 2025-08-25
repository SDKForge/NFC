@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.nfc.domain

interface NDEFTag {
    val isWritable: Boolean

    fun readMessage(onRead: (NDEFMessage?) -> Unit)
    fun writeMessage(message: NDEFMessage)
}
