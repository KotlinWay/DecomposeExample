package org.company.app.person.delete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
internal fun DeletePersonDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    Dialog(onDismissRequest = onDismiss){
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text("Do you confirm delete the user?")
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Button(onDismiss){ Text("Cancel") }
                Button(onConfirm){ Text("Confirm", color = Color.Red) }
            }
        }
    }
}