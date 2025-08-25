@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.nfc.domain

interface MifareNFCTag : NFCTag {

    val identifier: ByteArray
    val family: NFCMifareFamily

    fun send(data: ByteArray, onResponse: (ByteArray) -> Unit)
}
