package org.company.app.person.list

import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import org.company.app.Person

class PersonsComponent (
    context: ComponentContext,
    private val onAddNewPerson: () -> Unit,
    private val onPerson: (Person) -> Unit
) : ComponentContext by context {

    val persons = MutableStateFlow(
        stateKeeper.consume(
            key = this::class.qualifiedName.orEmpty(),
            strategy = State.serializer()
        ) ?: State()
    )

    init {
        stateKeeper.register(
            key = this::class.qualifiedName.orEmpty(),
            strategy = State.serializer()
        ){ persons.value }
    }

    fun clickOnPerson(person: Person) { onPerson(person) }

    fun updatePersons(person: Person) = persons.update {
        it.copy(items = it.items + person)
    }

    fun addNewPerson() { onAddNewPerson() }

    fun deletePerson(person: Person) = persons.update {
        it.copy(items = it.items - person)
    }

    @Serializable
    data class State(
        val items: List<Person> = emptyList()
    )
}