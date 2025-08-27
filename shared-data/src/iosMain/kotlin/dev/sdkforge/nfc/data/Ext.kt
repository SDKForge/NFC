package dev.sdkforge.nfc.data

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
internal fun ByteArray.toNSData(): NSData = usePinned {
    NSData.create(bytes = it.addressOf(0), length = this.size.convert())
}

@OptIn(ExperimentalForeignApi::class)
internal fun NSData.toByteArray(): ByteArray = ByteArray(length.toInt()).apply {
    usePinned { memcpy(it.addressOf(0), bytes, length) }
}
