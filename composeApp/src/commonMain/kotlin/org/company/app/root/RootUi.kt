package org.company.app.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.company.app.person.create.PersonCreateUi
import org.company.app.person.detail.PersonDetailUi
import org.company.app.person.list.PersonsUi

@Composable
fun RootUi(component: RootComponent) {

    val stack by component.stack.subscribeAsState()

    Children(
        stack = stack,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(16.dp)
    ){ child ->
        when(val instance = child.instance){
            is RootComponent.Child.Persons -> PersonsUi(instance.component)
            is RootComponent.Child.AddPerson -> PersonCreateUi(instance.component)
            is RootComponent.Child.DetailPerson -> PersonDetailUi(instance.component)
        }
    }
}