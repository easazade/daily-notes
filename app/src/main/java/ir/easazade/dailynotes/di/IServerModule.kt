package ir.easazade.dailynotes.di

import ir.easazade.dailynotes.businesslogic.server.IAppServer

interface IServerModule {

    fun server(): IAppServer

}