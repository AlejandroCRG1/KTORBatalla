package com.example

import com.example.models.Palabras
import com.example.models.Participante
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun getPalabras() {
    val client = HttpClient(CIO)
    runBlocking {
        val response: HttpResponse = client.get("http://localhost:8080/Palabras")
        val responseBody = response.bodyAsText()

        println("Resposta status: ${response.status}")
        if (responseBody.isNotEmpty()) {
            val palabras: List<Palabras> = Json.decodeFromString(responseBody)
            println("Las palabras y rimas registradas son:")
            palabras.forEach { participante ->
                println("Rimas: ${participante.rima} Palabras: ${participante.palabrasDisponibles}")
            }
        } else {
            println("The response body is empty.")
        }
    }
}

fun getParticipantes() {
    val client = HttpClient(CIO)
    runBlocking {
        val response: HttpResponse = client.get("http://localhost:8080/Participantes")
        val responseBody = response.bodyAsText()

        println("Resposta status: ${response.status}")
        if (responseBody.isNotEmpty()) {
            val participantes: List<Participante> = Json.decodeFromString(responseBody)
            println("Los participantes registrados son:")
            participantes.forEach { participante ->
                println("Nom: ${participante.nombre} Data: ${participante.dataNaixement}")
            }
        } else {
            println("The response body is empty.")
        }
    }
}

@OptIn(InternalAPI::class)
fun addParticipante() {
    val client = HttpClient(CIO)
    var participante = Participante("JUAN", "elqsigui", 23, "")
    runBlocking {
        var json = Json.encodeToString(participante)
        val response: HttpResponse = client.post("http://localhost:8080/Participantes") {
            contentType(ContentType.Application.Json)
            body = json
        }
        val responseBody = response.bodyAsText()

        println("Resposta status: ${response.status}")
        println(responseBody)
    }
}

@OptIn(InternalAPI::class)
fun addPalabras() {
    val client = HttpClient(CIO)
    var list = mutableListOf(Palabras("edqwds", listOf("pae", "rae")), Palabras("eo", listOf("peo", "reo")))
    runBlocking {
        var json = Json.encodeToString(list)
        val response: HttpResponse = client.post("http://localhost:8080/Palabras") {
            contentType(ContentType.Application.Json)
            body = json
        }
        val responseBody = response.bodyAsText()

        println("Resposta status: ${response.status}")
        println(responseBody)
    }
}

fun getGritos() {
    val client = HttpClient(CIO)
    runBlocking {
        val response: HttpResponse = client.get("http://localhost:8080/Gritos")
        val responseBody = response.bodyAsText()

        println("Resposta status: ${response.status}")
        println(responseBody.toInt())
    }
}

fun getBatallas() {
    val client = HttpClient(CIO)
    runBlocking {
        val response: HttpResponse = client.get("http://localhost:8080/Gritos")
        val responseBody = response.bodyAsText()

        println("Resposta status: ${response.status}")
        println(responseBody.toInt())
    }
}

fun addBatalla() {

}