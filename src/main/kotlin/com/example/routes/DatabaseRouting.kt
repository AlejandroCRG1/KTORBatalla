package com.example.routes

import com.example.Mongo
import com.example.models.Palabras
import com.example.models.Participante
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.bson.Document
import kotlin.random.Random

fun Route.databaseRouting() {
    val mongo = Mongo()
    route("/Participantes") {
        get {
            mongo.connexioBD()
            val list = mongo.retrieve("Participantes", Document(), "Participantes")
            if (list.isNotEmpty()) {
                // Serialize the list to JSON
                val jsonList = Json.encodeToString(ListSerializer(Participante.serializer()), list as List<Participante>)
                call.respondText(jsonList, status = HttpStatusCode.OK, contentType = ContentType.Application.Json)
            } else {
                call.respondText("No s'ha trobat res", status = HttpStatusCode.OK)
            }
            mongo.desconnexioBD()
        }
        post {
            mongo.connexioBD()
            val participante = call.receive<Participante>()
            val list = mongo.retrieve("Participantes", Document(), "Participantes")
            val jsonList = Json.encodeToString(ListSerializer(Participante.serializer()), list as List<Participante>)
            val palabrasList : List<Participante> = Json.decodeFromString(jsonList)
            var trobat = false
            for (i in palabrasList) {
                if (i.nombre == participante.nombre) {
                    trobat = true
                }
            }
            if (trobat) {
                call.respondText(
                    "Participante ya existe",
                    status = HttpStatusCode.BadRequest
                )
            }
            else {
                mongo.insereix("Participantes", participante)
                call.respondText(
                    "Participante añadido",
                    status = HttpStatusCode.OK
                )
            }
            mongo.desconnexioBD()
        }
    }
    route("/Palabras") {
        get {
            mongo.connexioBD()
            var list = mongo.retrieve("Palabras", Document(), "Palabras")
            if (list.isNotEmpty()) {
                val jsonList = Json.encodeToString(ListSerializer(Palabras.serializer()), list as List<Palabras>)
                call.respondText(jsonList, status = HttpStatusCode.OK, contentType = ContentType.Application.Json)
            } else {
                call.respondText("No s'ha trobat res", status = HttpStatusCode.OK)
            }
            mongo.desconnexioBD()
        }
        post {
            mongo.connexioBD()
            mongo.deletePalabrasCollection()
            val palabras = call.receive<List<Palabras>>()
            palabras.forEach { palabra ->
                mongo.insereix("Palabras", palabra)
            }
            call.respondText(
                "Paraules posades",
                status = HttpStatusCode.OK
            )
            mongo.desconnexioBD()
        }
    }
    route("/Gritos") {
        get {
            mongo.connexioBD()
            var nombre = Random.nextInt(0, 101)
            call.respond(nombre)
            mongo.desconnexioBD()
        }
    }
}