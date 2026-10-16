package com.example.patients.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Consultation(
    val id: String,
    val date: String? = null,
    val motif: String? = null,
    val notes: String? = null
)