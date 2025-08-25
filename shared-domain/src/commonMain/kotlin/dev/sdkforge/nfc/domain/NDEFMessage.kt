package dev.sdkforge.nfc.domain

interface NDEFMessage {
    val length: Long
    val records: List<NDEFRecord>
}
