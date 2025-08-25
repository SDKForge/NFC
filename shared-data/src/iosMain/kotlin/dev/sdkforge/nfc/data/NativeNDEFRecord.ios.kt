package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.NDEFRecord
import dev.sdkforge.nfc.domain.TypeNameFormat
import platform.CoreNFC.NFCNDEFPayload
import platform.CoreNFC.NFCTypeNameFormatAbsoluteURI
import platform.CoreNFC.NFCTypeNameFormatEmpty
import platform.CoreNFC.NFCTypeNameFormatMedia
import platform.CoreNFC.NFCTypeNameFormatNFCExternal
import platform.CoreNFC.NFCTypeNameFormatNFCWellKnown
import platform.CoreNFC.NFCTypeNameFormatUnchanged

actual class NativeNDEFRecord(internal val ndefPayload: NFCNDEFPayload) : NDEFRecord {

    actual override val identifier: ByteArray
        get() = ndefPayload.identifier.toByteArray()

    actual override val type: ByteArray
        get() = ndefPayload.type.toByteArray()

    actual override val payload: ByteArray
        get() = ndefPayload.payload.toByteArray()

    actual override val typeNameFormat: TypeNameFormat
        get() = when (ndefPayload.typeNameFormat) {
            NFCTypeNameFormatAbsoluteURI -> TypeNameFormat.ABSOLUTE_URI
            NFCTypeNameFormatEmpty -> TypeNameFormat.EMPTY
            NFCTypeNameFormatMedia -> TypeNameFormat.MEDIA
            NFCTypeNameFormatNFCExternal -> TypeNameFormat.NFC_EXTERNAL
            NFCTypeNameFormatNFCWellKnown -> TypeNameFormat.NFC_WELL_KNOWN
            NFCTypeNameFormatUnchanged -> TypeNameFormat.UNCHANGED
            else -> TypeNameFormat.UNKNOWN
        }
}
