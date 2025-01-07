package org.company.app

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val name: String,
    val old: Int,
)