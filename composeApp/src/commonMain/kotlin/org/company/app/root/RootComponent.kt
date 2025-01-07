package org.company.app.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.company.app.Person
import org.company.app.person.create.PersonCreateComponent
import org.company.app.person.detail.PersonDetailComponent
import org.company.app.person.list.PersonsComponent

class RootComponent(
    context: ComponentContext
) : ComponentContext by context {

    init {
        println("root init 🦄")
    }

    private val navigation = StackNavigation<Config>()
    val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::createStack,
        initialConfiguration = Config.Persons
    )

    private fun createStack(
        config: Config,
        childContext: ComponentContext
    ) = when (config) {
        is Config.Persons -> Child.Persons(PersonsComponent(childContext, ::addNewPerson, ::clickOnPerson))
        is Config.AddPerson -> Child.AddPerson(PersonCreateComponent(
            childContext, ::onCreateFinish, ::onCreatePersonDismiss)
        )
        is Config.DetailPerson ->
            Child.DetailPerson(PersonDetailComponent(childContext, config.person, ::onPersonOutput))
    }

    private fun onCreatePersonDismiss() {
        navigation.pop()
    }

    private fun onPersonOutput(output: PersonDetailComponent.Output) {
        when(output){
            is PersonDetailComponent.Output.Delete -> {
                navigation.pop()
                val child = stack.value.active.instance
                if(child is Child.Persons){
                    child.component.deletePerson(output.person)
                }
            }
        }
    }

    private fun addNewPerson() {
        navigation.pushNew(Config.AddPerson)
    }

    private fun onCreateFinish(person: Person) {
        navigation.pop()
        val child = stack.value.active.instance
        if(child is Child.Persons){
            child.component.updatePersons(person)
        }
    }

    private fun clickOnPerson(person: Person) {
        navigation.pushNew(Config.DetailPerson(person))
    }


    @Serializable
    sealed interface Config {
        @Serializable
        data object Persons : Config

        @Serializable
        data object AddPerson : Config

        @Serializable
        class DetailPerson(val person: Person) : Config
    }

    sealed interface Child {
        class Persons(val component: PersonsComponent) : Child
        class AddPerson(val component: PersonCreateComponent) : Child
        class DetailPerson(val component: PersonDetailComponent) : Child
    }
}