package com.setu.salonfinderapp.models

interface SalonStore {
    fun findAll(): List<SalonModel>
    fun create(salon: SalonModel)
    fun update(salon: SalonModel)

}