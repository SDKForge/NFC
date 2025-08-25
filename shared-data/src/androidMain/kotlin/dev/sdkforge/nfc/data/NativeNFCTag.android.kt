package dev.sdkforge.nfc.data

import android.nfc.Tag
import dev.sdkforge.nfc.domain.NFCTag

actual open class NativeNFCTag(internal val tag: Tag) : NFCTag
