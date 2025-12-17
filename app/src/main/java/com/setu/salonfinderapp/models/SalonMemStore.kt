package com.setu.salonfinderapp.models

import timber.log.Timber.Forest.i

private var lastId = 0L

private fun getNextId(): Long {
    return lastId++
}

class SalonMemStore : SalonStore {

    val salonList = ArrayList<SalonModel>()

    override fun findAll(): List<SalonModel> {
        return salonList
    }

    override fun create(salonEntry: SalonModel) {
        salonEntry.id = getNextId()
        salonList.add(salonEntry)
        logAll()
    }

    override fun update(salonEntry: SalonModel) {
        val foundSalon: SalonModel? = salonList.find { s -> s.id == salonEntry.id }
        if (foundSalon != null) {
            foundSalon.name = salonEntry.name
            foundSalon.description = salonEntry.description
            foundSalon.image = salonEntry.image
            foundSalon.lat = salonEntry.lat
            foundSalon.lng = salonEntry.lng
            foundSalon.zoom = salonEntry.zoom
            logAll()
        }
    }

    override fun delete(salonEntry: SalonModel) {
        salonList.remove(salonEntry)
    }

    override fun deleteAll() {
        salonList.clear()
        logAll()
    }

    override fun findById(id: Long): SalonModel? {
        val foundSalon: SalonModel? = salonList.find { it.id == id }
        return foundSalon
    }

    private fun logAll() {
        salonList.forEach { i("$it") }
    }
}
