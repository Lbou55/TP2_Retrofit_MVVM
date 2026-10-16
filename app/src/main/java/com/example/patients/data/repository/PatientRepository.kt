package com.example.patients.data.repository

import com.example.patients.data.model.ApiError
import com.example.patients.data.model.Patient
import com.example.patients.data.model.PatientRequest
import com.example.patients.data.remote.PatientApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import com.example.patients.data.model.Consultation

class PatientRepository(
    private val api: PatientApi,
    private val json: Json
) {

    suspend fun getPatients(statut: String? = null): Result<List<Patient>> =
        safeCall { api.getPatients(statut) }

    suspend fun createPatient(request: PatientRequest): Result<Patient> =
        safeCall { api.createPatient(request) }

    suspend fun updatePatient(id: String, request: PatientRequest): Result<Patient> =
        safeCall { api.updatePatient(id, request) }

    suspend fun deletePatient(id: String): Result<Unit> = safeCall {
        val response = api.deletePatient(id)
        if (!response.isSuccessful) throw HttpException(response)
    }

    suspend fun getConsultations(patientId: String): Result<List<Consultation>> =
        safeCall { api.getConsultations(patientId) }

    // Runs a network call and turns any error into a readable message
    private suspend fun <T> safeCall(call: suspend () -> T): Result<T> =
        try {
            Result.success(call())
        } catch (e: HttpException) { // server replied with 4xx or 5xx
            Result.failure(Exception(messageFrom(e)))
        } catch (e: IOException) { // no reply: server off, wrong IP...
            Result.failure(Exception("Serveur injoignable. Vérifiez l'adresse et le réseau."))
        } catch (e: SerializationException) { // JSON doesn't match the Kotlin class
            Result.failure(Exception("Réponse inattendue du serveur."))
        }

    // Reads the JSON error body sent by our Node API
    private fun messageFrom(e: HttpException): String {
        val body = e.response()?.errorBody()?.string()
        val apiError = body?.let {
            runCatching { json.decodeFromString(ApiError.serializer(), it) }.getOrNull()
        }
        return when {
            apiError == null -> "Erreur ${e.code()}"
            apiError.erreurs.isNotEmpty() -> apiError.erreurs.joinToString("\n")
            else -> apiError.message
        }
    }
}