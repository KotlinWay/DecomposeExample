package org.company.app.person.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.statekeeper.saveable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import org.company.app.Person

class PersonCreateComponent(
    context: ComponentContext,
    private val onFinish: (Person) -> Unit,
    private val onDismiss: () -> Unit
) : ComponentContext by context {

    init {
//        backHandler.register(BackCallback {
//            if(state.value == State.DEFAULT) onDismiss() else _state.update { State.DEFAULT }
//        })
    }

    private val _state: MutableStateFlow<State> by stateKeeper.saveable(
        serializer = State.serializer(),
        state = { state.value }
    ) { MutableStateFlow(it ?: State.DEFAULT) }

    val state = _state.asStateFlow()

    fun updateName(name: String) { _state.update { it.copy(name = name) }}

    fun updateOld(old: String) { _state.update { it.copy(old = old.toInt()) }}

    fun finish() = onFinish(Person(state.value.name, state.value.old))

    @Serializable
    data class State(
        val name: String,
        val old: Int
    ) {
        companion object {
            val DEFAULT = State("", 0)
        }
    }
}