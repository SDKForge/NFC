package dev.sdkforge.nfc.domain

interface NDEFRecord {
    val identifier: ByteArray
    val type: ByteArray
    val payload: ByteArray
    val typeNameFormat: TypeNameFormat
}
