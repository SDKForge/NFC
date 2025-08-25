package dev.sdkforge.nfc.data

import android.nfc.NdefRecord
import android.nfc.NdefRecord.TNF_ABSOLUTE_URI
import android.nfc.NdefRecord.TNF_EMPTY
import android.nfc.NdefRecord.TNF_EXTERNAL_TYPE
import android.nfc.NdefRecord.TNF_MIME_MEDIA
import android.nfc.NdefRecord.TNF_UNCHANGED
import android.nfc.NdefRecord.TNF_WELL_KNOWN
import dev.sdkforge.nfc.domain.NDEFRecord
import dev.sdkforge.nfc.domain.TypeNameFormat

actual class NativeNDEFRecord(internal val record: NdefRecord) : NDEFRecord {

    actual override val identifier: ByteArray
        get() = record.id

    actual override val type: ByteArray
        get() = record.type

    actual override val payload: ByteArray
        get() = record.payload

    actual override val typeNameFormat: TypeNameFormat
        get() = when (record.tnf) {
            TNF_ABSOLUTE_URI -> TypeNameFormat.ABSOLUTE_URI
            TNF_EMPTY -> TypeNameFormat.EMPTY
            TNF_MIME_MEDIA -> TypeNameFormat.MEDIA
            TNF_EXTERNAL_TYPE -> TypeNameFormat.NFC_EXTERNAL
            TNF_WELL_KNOWN -> TypeNameFormat.NFC_WELL_KNOWN
            TNF_UNCHANGED -> TypeNameFormat.UNCHANGED
            else -> TypeNameFormat.UNKNOWN
        }
}
