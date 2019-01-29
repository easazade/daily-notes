package ir.easazade.dailynotes.di

import ir.easazade.dailynotes.businesslogic.database.AppDatabase

interface IDatabaseModule {
    fun appDatabase():AppDatabase
}