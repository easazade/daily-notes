package ir.easazade.dailynotes.di

import ir.easazade.dailynotes.businesslogic.repos.IUserRepository
import ir.easazade.dailynotes.businesslogic.server.IAppServer
import ir.easazade.dailynotes.viewmodels.ViewModelFactory

interface IArchitectureModule {
    fun userRepo(server: IAppServer): IUserRepository
    fun viewModelFactory(): ViewModelFactory
}