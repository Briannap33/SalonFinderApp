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
        // todo
    }

    override fun delete(salon: SalonModel) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
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