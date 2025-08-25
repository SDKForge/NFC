@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.MifareNFCTag
import dev.sdkforge.nfc.domain.NFCMifareFamily

expect class NativeMifareNFCTag : MifareNFCTag {
    override val identifier: ByteArray
    override val family: NFCMifareFamily

    override fun send(data: ByteArray, onResponse: (ByteArray) -> Unit)
}
