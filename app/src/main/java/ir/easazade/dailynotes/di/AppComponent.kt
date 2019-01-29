package ir.easazade.dailynotes.di

import androidx.fragment.app.FragmentActivity
import ir.easazade.dailynotes.businesslogic.database.IAppDatabase
import ir.easazade.dailynotes.businesslogic.repos.IUserRepository
import ir.easazade.dailynotes.screens.main.HomeFrag
import ir.easazade.dailynotes.sdk.DataBindings
import ir.easazade.dailynotes.sdk.navigation.Navigator
import ir.easazade.dailynotes.viewmodels.ViewModelFactory

class AppComponent(
    private val databaseModule: IDatabaseModule,
    private val serverModule: IServerModule,
    private val architectureModule: IArchitectureModule
) : IAppComponent {

    override fun database(): IAppDatabase = databaseModule.appDatabase()

    override fun navigator(): Navigator<HomeFrag.State, HomeFrag.Args> {

    }

    override fun usersRepository(): IUserRepository = architectureModule.userRepo(serverModule.server())

    override fun viewModelFactory(activity: FragmentActivity): ViewModelFactory = architectureModule.viewModelFactory()

    override fun dataBindings(): DataBindings {

    }


}