package dev.sdkforge.nfc.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.uikit.LocalUIViewController
import dev.sdkforge.nfc.data.NativeNFC
import dev.sdkforge.nfc.domain.NFC

actual val LocalNFC: CompositionLocal<NFC>
    @Composable get() {
        val uiViewController = LocalUIViewController.current
        return staticCompositionLocalOf { NativeNFC(viewController = uiViewController) }
    }
