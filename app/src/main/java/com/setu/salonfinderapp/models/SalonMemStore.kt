package com.setu.salonfinderapp.models

import timber.log.Timber.Forest.i
var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class SalonMemStore : SalonStore {

    val salons = ArrayList<SalonModel>()

    override fun findAll(): List<SalonModel> {
        return salons
    }

    override fun create(salon: SalonModel) {
        salon.id = getId()
        salons.add(salon)
        logAll()
    }

    override fun update(salon: SalonModel) {
        val foundSalon: SalonModel? = salons.find { it.id == salon.id }
        if (foundSalon != null) {
            foundSalon.name = salon.name
            foundSalon.description = salon.description
            logAll()
        }
    }

    fun delete(salon: SalonModel) {
        salons.removeIf { it.id == salon.id }
        logAll()
    }

    private fun logAll() {
        salons.forEach { i("$it") }
    }
}
