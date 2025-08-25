@file:Suppress("ktlint:standard:class-signature", "ktlint:standard:function-signature")

package dev.sdkforge.nfc.data

import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import dev.sdkforge.nfc.domain.MifareNFCTag
import dev.sdkforge.nfc.domain.NFCMifareFamily

actual class NativeMifareNFCTag(
    internal val mifareTag: Tag,
) : NativeNFCTag(mifareTag),
    MifareNFCTag {

    private val mifareUltralight: MifareUltralight? = MifareUltralight.get(tag)
    private val mifareClassic: MifareClassic? = MifareClassic.get(tag)

    actual override val identifier: ByteArray
        get() = tag.id

    actual override val family: NFCMifareFamily
        get() = when {
            mifareUltralight != null -> NFCMifareFamily.ULTRALIGHT
            mifareClassic != null -> when (mifareClassic.type) {
                MifareClassic.TYPE_PLUS -> NFCMifareFamily.PLUS
                else -> NFCMifareFamily.UNKNOWN
            }

            else -> NFCMifareFamily.UNKNOWN
        }

    internal companion object {
        internal fun get(tag: Tag): MifareNFCTag? = when {
            MifareUltralight.get(tag) != null -> NativeMifareNFCTag(tag)
            MifareClassic.get(tag) != null -> NativeMifareNFCTag(tag)
            else -> null
        }
    }

    actual override fun send(data: ByteArray, onResponse: (ByteArray) -> Unit) {
        if (mifareUltralight != null) {
            mifareUltralight.connect()
            onResponse.invoke(mifareUltralight.transceive(data))
            mifareUltralight.close()
        }
        if (mifareClassic != null) {
            mifareClassic.connect()
            onResponse.invoke(mifareClassic.transceive(data))
            mifareClassic.close()
        }
    }
}
