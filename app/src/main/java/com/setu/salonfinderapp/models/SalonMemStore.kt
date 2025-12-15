package com.setu.salonfinderapp.models

import timber.log.Timber.Forest.i

private var lastId = 0L

private fun getNextId(): Long {
    return lastId++
}

class SalonMemStore {

    val salonList = ArrayList<SalonModel>()

    fun findAll(): List<SalonModel> {
        return salonList
    }

    fun create(salon: SalonModel) {
        salon.id = getNextId()
        salonList.add(salon)
        logAll()
    }

    fun update(salon: SalonModel) {
        val foundSalon: SalonModel? = salonList.find { s -> s.id == salon.id }
        if (foundSalon != null) {
            foundSalon.name = salon.name
            foundSalon.description = salon.description
            foundSalon.image = salon.image
            logAll()
        }
    }

    fun delete(salon: SalonModel) {
        salonList.remove(salon)
        logAll()
    }

    fun deleteAll() {
        salonList.clear()
        logAll()
    }

    private fun logAll() {
        salonList.forEach { i("$it") }
    }
}
