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

    override fun create(salon: SalonModel) {
        salon.id = getNextId()
        salonList.add(salon)
        logAll()
    }

    override fun update(salon: SalonModel) {
        val foundSalon: SalonModel? = salonList.find { s -> s.id == salon.id }
        if (foundSalon != null) {
            foundSalon.name = salon.name
            foundSalon.description = salon.description
            foundSalon.image = salon.image
            foundSalon.lat = salon.lat
            foundSalon.lng = salon.lng
            foundSalon.zoom = salon.zoom
            logAll()
        }
    }

    override fun delete(salon: SalonModel) {
        salonList.remove(salon)
        logAll()
    }

    override fun deleteAll() {
        salonList.clear()
        logAll()
    }

    private fun logAll() {
        salonList.forEach { i("$it") }
    }
}
