package com.setu.salonfinderapp.models

interface SalonStore {
    fun findAll(): List<SalonModel>
    fun create(salonEntry: SalonModel)
    fun update(salonEntry: SalonModel)
    fun delete(salonEntry: SalonModel)
    fun deleteAll()

}