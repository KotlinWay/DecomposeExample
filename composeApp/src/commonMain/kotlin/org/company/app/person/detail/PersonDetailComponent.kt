package org.company.app.person.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import org.company.app.Counter
import org.company.app.Person

class PersonDetailComponent(
    context: ComponentContext,
    val person: Person,
    val output: (Output) -> Unit
) : ComponentContext by context {

    private val counter = instanceKeeper.getOrCreate { Counter() }
    val counterState = counter.countFlow
    val state = MutableStateFlow(State(person.toString()))

    private val navigation = SlotNavigation<Config>()
    val slots: Value<ChildSlot<*, Child>> = childSlot(
        source = navigation,
        serializer = Config.serializer(),
        childFactory = ::createChildSlots
    )

    fun onEvent(event: UiEvent) = when (event) {
        is UiEvent.Delete -> navigation.activate(Config.Delete)
        is UiEvent.ConfirmDelete -> output(Output.Delete(person))
        is UiEvent.DismissDelete -> navigation.dismiss()
        is UiEvent.IncCounter -> counter.increment()
    }

    private fun createChildSlots(
        config: Config,
        context: ComponentContext
    ): Child = when (config) {
        is Config.Delete -> Child.Delete
    }

    sealed interface UiEvent {
        data object Delete : UiEvent
        data object DismissDelete : UiEvent
        data object ConfirmDelete : UiEvent
        data object IncCounter : UiEvent
    }

    data class State(val title: String)

    @Serializable
    sealed interface Config {
        @Serializable
        data object Delete : Config
    }

    sealed interface Child {
        data object Delete : Child
    }

    sealed interface Output {
        class Delete(val person: Person): Output
    }
}