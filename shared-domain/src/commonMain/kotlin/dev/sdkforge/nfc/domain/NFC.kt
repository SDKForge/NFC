@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.nfc.domain

interface NFC {
    val isEnabled: Boolean

    fun startNFCTagReading(onRead: (NFCTag) -> Unit, onError: (Throwable) -> Unit = {}): Boolean
    fun stopNFCTagReading()
    fun startNDEFReading(onRead: (NDEFTag) -> Unit, onError: (Throwable) -> Unit = {}): Boolean
    fun stopNDEFReading()
}
