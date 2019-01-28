package ir.easazade.dailynotes.businesslogic.database

import io.realm.Realm

interface RealmProvider {
    fun get(): Realm
}