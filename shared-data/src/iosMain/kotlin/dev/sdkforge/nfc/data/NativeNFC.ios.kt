@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.NDEFMessage
import dev.sdkforge.nfc.domain.NDEFTag
import dev.sdkforge.nfc.domain.NFC
import dev.sdkforge.nfc.domain.NFCTag
import kotlinx.cinterop.ObjCSignatureOverride
import platform.CoreNFC.NFCNDEFReaderSession
import platform.CoreNFC.NFCNDEFReaderSessionDelegateProtocol
import platform.CoreNFC.NFCNDEFTagProtocol
import platform.CoreNFC.NFCPollingISO14443
import platform.CoreNFC.NFCPollingISO15693
import platform.CoreNFC.NFCPollingISO18092
import platform.CoreNFC.NFCReaderSession
import platform.CoreNFC.NFCTagProtocol
import platform.CoreNFC.NFCTagReaderSession
import platform.CoreNFC.NFCTagReaderSessionDelegateProtocol
import platform.Foundation.NSError
import platform.UIKit.UIViewController
import platform.darwin.NSObject

actual class NativeNFC(internal val viewController: UIViewController) : NFC {

    private var session: NFCReaderSession? = null

    actual override val isEnabled: Boolean
        get() = NFCNDEFReaderSession.readingAvailable || NFCTagReaderSession.readingAvailable

    actual override fun startNFCTagReading(
        onRead: (NFCTag) -> Unit,
        onError: (Throwable) -> Unit,
    ): Boolean {
        if (!isEnabled) return false

        val delegate = object : NSObject(), NFCTagReaderSessionDelegateProtocol {
            override fun tagReaderSession(session: NFCTagReaderSession, didDetectTags: List<*>) {
                for (tag in didDetectTags) {
                    val tag = when (val mifareTag = NativeMifareNFCTag.get(tag as NFCTagProtocol)) {
                        null -> NativeNFCTag(tag = tag)
                        else -> mifareTag
                    }
                    onRead.invoke(tag)
                }
            }

            override fun tagReaderSession(
                session: NFCTagReaderSession,
                didInvalidateWithError: NSError,
            ) {
                onError.invoke(Throwable(message = didInvalidateWithError.localizedDescription))
            }

            override fun tagReaderSessionDidBecomeActive(session: NFCTagReaderSession) = Unit
        }

        session?.invalidateSession()
        session = NFCTagReaderSession(
            pollingOption = NFCPollingISO14443 or NFCPollingISO15693 or NFCPollingISO18092,
            delegate = delegate,
            queue = null,
        )
        session?.beginSession()

        return true
    }

    actual override fun stopNFCTagReading() {
        session?.invalidateSession()
        session = null
    }

    actual override fun startNDEFReading(
        onRead: (NDEFTag) -> Unit,
        onError: (Throwable) -> Unit,
    ): Boolean {
        if (!isEnabled) return false

        val delegate = object : NSObject(), NFCNDEFReaderSessionDelegateProtocol {
            @ObjCSignatureOverride
            override fun readerSession(session: NFCNDEFReaderSession, didDetectTags: List<*>) {
                for (tag in didDetectTags) {
                    onRead.invoke(NativeNfcNDEFTag(tag = tag as NFCNDEFTagProtocol))
                }
            }

            @ObjCSignatureOverride
            override fun readerSession(session: NFCNDEFReaderSession, didDetectNDEFs: List<*>) {
                for (ndef in didDetectNDEFs) {
                    onRead.invoke(NativeNDEFMessageTag(message = ndef as NDEFMessage))
                }
            }

            override fun readerSession(
                session: NFCNDEFReaderSession,
                didInvalidateWithError: NSError,
            ) {
                onError.invoke(Throwable(didInvalidateWithError.localizedDescription))
            }

            override fun readerSessionDidBecomeActive(session: NFCNDEFReaderSession) = Unit
        }

        session?.invalidateSession()
        session = NFCNDEFReaderSession(
            delegate = delegate,
            queue = null,
            invalidateAfterFirstRead = false,
        )
        session?.beginSession()

        return true
    }

    actual override fun stopNDEFReading() {
        session?.invalidateSession()
        session = null
    }
}
