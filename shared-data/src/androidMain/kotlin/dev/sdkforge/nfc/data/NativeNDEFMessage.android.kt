package dev.sdkforge.nfc.data

import android.nfc.NdefMessage
import dev.sdkforge.nfc.domain.NDEFMessage
import dev.sdkforge.nfc.domain.NDEFRecord

actual class NativeNDEFMessage(internal val message: NdefMessage) : NDEFMessage {

    actual override val length: Long
        get() = message.byteArrayLength.toLong()

    actual override val records: List<NDEFRecord>
        get() = message.records.map { NativeNDEFRecord(record = it) }
}
