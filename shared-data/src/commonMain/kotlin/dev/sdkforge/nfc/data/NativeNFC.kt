@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.NDEFTag
import dev.sdkforge.nfc.domain.NFC
import dev.sdkforge.nfc.domain.NFCTag

expect class NativeNFC : NFC {
    override val isEnabled: Boolean

    override fun startNFCTagReading(onRead: (NFCTag) -> Unit, onError: (Throwable) -> Unit): Boolean
    override fun stopNFCTagReading()
    override fun startNDEFReading(onRead: (NDEFTag) -> Unit, onError: (Throwable) -> Unit): Boolean
    override fun stopNDEFReading()
}
