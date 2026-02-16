package com.example.project01group03

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project01group03.API.VinylRelease
import com.example.project01group03.API.getYearString

@Composable
fun VinylReleaseItem(
    release: VinylRelease,
    isInCollection: Boolean = false,
    onAddToCollection: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Album cover or placeholder
        val thumbUrl = release.thumb?.takeIf { it.isNotBlank() }

        if (thumbUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Album cover",
                modifier = Modifier.size(64.dp)
            )
        } else {
            // Simple placeholder when there's no image
            Spacer(modifier = Modifier.size(64.dp))
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Release info
        Column(modifier = Modifier.weight(1f)) {
            val yearString = release.getYearString()?.let { " ($it)" } ?: ""
            Text(
                text = "${release.title}$yearString",
                style = MaterialTheme.typography.bodyLarge
            )

            // Show format info
            release.format?.let { format ->
                Text(
                    text = format,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Add button
        if (onAddToCollection != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onAddToCollection,
                enabled = !isInCollection,
                colors = if (isInCollection) {
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    ButtonDefaults.buttonColors()
                }
            ) {
                Text(if (isInCollection) "Added" else "Add")
            }
        }
    }
}