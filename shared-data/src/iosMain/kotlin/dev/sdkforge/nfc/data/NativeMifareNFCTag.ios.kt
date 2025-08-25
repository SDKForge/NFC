@file:Suppress("ktlint:standard:function-signature", "ktlint:standard:class-signature")

package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.MifareNFCTag
import dev.sdkforge.nfc.domain.NFCMifareFamily
import platform.CoreNFC.NFCMiFarePlus
import platform.CoreNFC.NFCMiFareTagProtocol
import platform.CoreNFC.NFCMiFareUltralight
import platform.CoreNFC.NFCTagProtocol
import platform.CoreNFC.NFCTagTypeMiFare

actual class NativeMifareNFCTag(
    internal val mifareTag: NFCMiFareTagProtocol,
) : NativeNFCTag(mifareTag),
    MifareNFCTag {

    actual override val identifier: ByteArray
        get() = mifareTag.identifier.toByteArray()

    actual override val family: NFCMifareFamily
        get() = when (mifareTag.mifareFamily) {
            NFCMiFareUltralight -> NFCMifareFamily.ULTRALIGHT
            NFCMiFarePlus -> NFCMifareFamily.PLUS
            else -> NFCMifareFamily.UNKNOWN
        }

    internal companion object {
        internal fun get(tag: NFCTagProtocol): MifareNFCTag? = when {
            tag.type == NFCTagTypeMiFare -> NativeMifareNFCTag(tag.asNFCMiFareTag()!!)
            else -> null
        }
    }

    actual override fun send(data: ByteArray, onResponse: (ByteArray) -> Unit) {
        mifareTag.sendMiFareCommand(
            command = data.toNSData(),
            completionHandler = { data, _ ->
                if (data != null) {
                    onResponse.invoke(data.toByteArray())
                }
            },
        )
    }
}
