package dev.sdkforge.nfc.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sdkforge.nfc.domain.NFCTag
import dev.sdkforge.nfc.ui.LocalNFC

@Composable
fun App(
    modifier: Modifier = Modifier,
) = ApplicationTheme {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        val nfc = LocalNFC.current

        val nfcTags = remember { mutableStateListOf<NFCTag>() }
        var error by remember { mutableStateOf<Throwable?>(null) }

        Column(
            modifier = Modifier
                .systemBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Top,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    nfc.startNFCTagReading(
                        onRead = { nfcTags += it },
                        onError = { error = it },
                    )
                },
            ) {
                Text("Start NFC tag reading")
            }
            Button(
                onClick = {
                    nfc.stopNFCTagReading()
                    error = null
                },
            ) {
                Text("Stop NFC tag reading")
            }
            Text(
                text = "NFC isEnabled: ${nfc.isEnabled}",
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                items(
                    items = nfcTags,
                ) { nfcTag ->
                    Text(
                        text = nfcTag.toString(),
                        modifier = Modifier
                            .padding(8.dp),
                    )
                    HorizontalDivider()
                }
            }
            Text(
                text = "NFC error: $error",
            )
        }
    }
}
