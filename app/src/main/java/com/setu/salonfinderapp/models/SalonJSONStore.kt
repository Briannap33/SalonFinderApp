package com.setu.salonfinderapp.models

import android.content.Context
import android.net.Uri
import android.service.credentials.CreateEntry
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.setu.salonfinderapp.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "salons.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<SalonModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class SalonJSONStore(private val context: Context) : SalonStore {

    var salonList = mutableListOf<SalonModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<SalonModel> {
        logAll()
        return salonList
    }

    override fun create(salonEntry: SalonModel) {
        salonEntry.id = generateRandomId()
        salonList.add(salonEntry)
        serialize()
    }


    override fun update(salonEntry: SalonModel) {
        val salonsList = findAll() as ArrayList<SalonModel>
        var foundSalon: SalonModel? = salonsList.find { p -> p.id == salonEntry.id }
        if (foundSalon != null) {
            foundSalon.name = salonEntry.name
            foundSalon.description = salonEntry.description
            foundSalon.image = salonEntry.image
            foundSalon.lat = salonEntry.lat
            foundSalon.lng = salonEntry.lng
            foundSalon.zoom = salonEntry.zoom
        }
        serialize()
    }

    override fun delete(salon: SalonModel) {
    }

    override fun deleteAll() {
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(salonList, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        salonList = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        salonList.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}