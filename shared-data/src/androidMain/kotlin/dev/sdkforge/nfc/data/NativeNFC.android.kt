@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.nfc.data

import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.tech.Ndef
import dev.sdkforge.nfc.domain.NDEFTag
import dev.sdkforge.nfc.domain.NFC
import dev.sdkforge.nfc.domain.NFCTag

actual class NativeNFC(internal val context: Context) : NFC {

    private val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(context)

    actual override val isEnabled: Boolean
        get() = nfcAdapter?.isEnabled == true

    actual override fun startNFCTagReading(
        onRead: (NFCTag) -> Unit,
        onError: (Throwable) -> Unit,
    ): Boolean {
        if (!isEnabled) return false

        nfcAdapter?.enableReaderMode(
            context as Activity,
            { tag ->
                val tag = when (val mifareTag = NativeMifareNFCTag.get(tag)) {
                    null -> NativeNFCTag(tag = tag)
                    else -> mifareTag
                }
                onRead.invoke(tag)
            },
            0,
            null,
        )

        return true
    }

    actual override fun stopNFCTagReading() {
        nfcAdapter?.disableReaderMode(context as Activity)
    }

    actual override fun startNDEFReading(
        onRead: (NDEFTag) -> Unit,
        onError: (Throwable) -> Unit,
    ): Boolean {
        if (!isEnabled) return false

        nfcAdapter?.enableReaderMode(
            context as Activity,
            { tag ->
                Ndef.get(tag)?.run {
                    onRead.invoke(NativeNDEFTag(this))
                }
            },
            0,
            null,
        )

        return true
    }

    actual override fun stopNDEFReading() {
        if (!isEnabled) return

        nfcAdapter?.disableReaderMode(context as Activity)
    }
}
