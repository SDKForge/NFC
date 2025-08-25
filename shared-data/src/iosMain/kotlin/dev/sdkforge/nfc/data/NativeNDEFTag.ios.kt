@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.NDEFMessage
import dev.sdkforge.nfc.domain.NDEFTag
import platform.CoreNFC.NFCNDEFStatusReadWrite
import platform.CoreNFC.NFCNDEFTagProtocol

actual open class NativeNDEFTag : NDEFTag {

    internal open val isTagWritable: Boolean
        get() = false

    actual override val isWritable: Boolean
        get() = isTagWritable

    actual override fun readMessage(onRead: (NDEFMessage?) -> Unit) = Unit
    actual override fun writeMessage(message: NDEFMessage) = Unit
}

data class NativeNfcNDEFTag(internal val tag: NFCNDEFTagProtocol) : NativeNDEFTag() {

    override var isTagWritable: Boolean = false

    init {
        tag.queryNDEFStatusWithCompletionHandler { status, capacity, error ->
            isTagWritable = status == NFCNDEFStatusReadWrite
        }
    }

    override fun readMessage(onRead: (NDEFMessage?) -> Unit) {
        tag.readNDEFWithCompletionHandler { message, error ->
            onRead.invoke(message?.run { NativeNDEFMessage(this) })
        }
    }

    override fun writeMessage(message: NDEFMessage) {
        tag.writeNDEF(ndefMessage = (message as NativeNDEFMessage).message, completionHandler = {})
    }
}

data class NativeNDEFMessageTag(internal val message: NDEFMessage) : NativeNDEFTag() {
    override fun readMessage(onRead: (NDEFMessage?) -> Unit) {
        onRead.invoke(message)
    }
}
