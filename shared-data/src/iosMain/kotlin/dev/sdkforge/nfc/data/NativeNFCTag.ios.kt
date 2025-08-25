package dev.sdkforge.nfc.data

import dev.sdkforge.nfc.domain.NFCTag
import platform.CoreNFC.NFCTagProtocol

actual open class NativeNFCTag(internal val tag: NFCTagProtocol) : NFCTag
