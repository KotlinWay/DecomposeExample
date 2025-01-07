package org.company.app

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Counter: InstanceKeeper.Instance {

    private val _count = MutableStateFlow(0)
    val countFlow = _count.asStateFlow()

    fun increment() = _count.update { it + 1 }
}