package org.company.app.person.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PersonCreateUi(component: PersonCreateComponent) {

    val state by component.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.name,
            onValueChange = component::updateName,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = state.old.toString(),
            onValueChange = component::updateOld,
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = component::finish) {
            Text(text = "Finish")
        }
    }
}