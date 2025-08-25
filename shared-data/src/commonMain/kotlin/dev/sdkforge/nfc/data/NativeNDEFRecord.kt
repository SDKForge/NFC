package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.NDEFRecord
import dev.sdkforge.nfc.domain.TypeNameFormat

expect class NativeNDEFRecord : NDEFRecord {
    override val identifier: ByteArray
    override val type: ByteArray
    override val payload: ByteArray
    override val typeNameFormat: TypeNameFormat
}
