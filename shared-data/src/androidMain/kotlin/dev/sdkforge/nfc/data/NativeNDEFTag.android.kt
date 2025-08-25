@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.nfc.data

import android.nfc.tech.Ndef
import dev.sdkforge.nfc.domain.NDEFMessage
import dev.sdkforge.nfc.domain.NDEFTag

actual class NativeNDEFTag(internal val ndef: Ndef) : NDEFTag {

    actual override val isWritable: Boolean
        get() = ndef.isWritable

    actual override fun readMessage(onRead: (NDEFMessage?) -> Unit) {
        onRead.invoke(ndef.ndefMessage?.run { NativeNDEFMessage(this) })
    }

    actual override fun writeMessage(message: NDEFMessage) {
        ndef.connect()
        ndef.writeNdefMessage((message as NativeNDEFMessage).message)
        ndef.close()
    }
}
