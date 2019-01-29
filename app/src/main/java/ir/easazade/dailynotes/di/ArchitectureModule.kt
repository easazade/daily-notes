package ir.easazade.dailynotes.di

import ir.easazade.dailynotes.businesslogic.repos.IUserRepository
import ir.easazade.dailynotes.businesslogic.server.IAppServer
import ir.easazade.dailynotes.viewmodels.ViewModelFactory

class ArchitectureModule : IArchitectureModule {
    override fun userRepo(server: IAppServer): IUserRepository {

    }

    override fun viewModelFactory(): ViewModelFactory {

    }
}