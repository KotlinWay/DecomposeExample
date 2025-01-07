package org.company.app.person.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.company.app.person.delete.DeletePersonDialog
import org.company.app.person.detail.PersonDetailComponent.*

@Composable
fun PersonDetailUi(component: PersonDetailComponent) {

    val countState by component.counterState.collectAsState()
    val state by component.state.collectAsState()
    val slots by component.slots.subscribeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = state.title,
            modifier = Modifier.clickable { component.onEvent(UiEvent.IncCounter) },
            fontSize = 22.sp
        )
        Text(text = countState.toString(), fontSize = 30.sp)
        Button(onClick = { component.onEvent(UiEvent.Delete) }){
            Text("Удалить")
        }
    }

    when(slots.child?.instance){
        Child.Delete -> DeletePersonDialog(
            onDismiss = { component.onEvent(UiEvent.DismissDelete) },
            onConfirm = { component.onEvent(UiEvent.ConfirmDelete) }
        )
        null -> Unit
    }
}