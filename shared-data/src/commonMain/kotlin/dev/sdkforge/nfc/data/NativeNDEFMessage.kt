package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.NDEFMessage
import dev.sdkforge.nfc.domain.NDEFRecord

expect class NativeNDEFMessage : NDEFMessage {
    override val length: Long
    override val records: List<NDEFRecord>
}
