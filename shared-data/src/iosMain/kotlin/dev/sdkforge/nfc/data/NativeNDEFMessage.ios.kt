package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.NDEFMessage
import dev.sdkforge.nfc.domain.NDEFRecord
import platform.CoreNFC.NFCNDEFMessage
import platform.CoreNFC.NFCNDEFPayload

actual class NativeNDEFMessage(internal val message: NFCNDEFMessage) : NDEFMessage {
    actual override val length: Long
        get() = message.length.toLong()

    actual override val records: List<NDEFRecord>
        get() = message.records.map { NativeNDEFRecord(ndefPayload = it as NFCNDEFPayload) }
}
