@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.NDEFMessage
import dev.sdkforge.nfc.domain.NDEFTag

expect class NativeNDEFTag : NDEFTag {
    override val isWritable: Boolean

    override fun readMessage(onRead: (NDEFMessage?) -> Unit)
    override fun writeMessage(message: NDEFMessage)
}
