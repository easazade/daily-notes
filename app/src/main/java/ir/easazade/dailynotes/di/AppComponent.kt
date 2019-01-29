package ir.easazade.dailynotes.di

import androidx.fragment.app.FragmentActivity
import ir.easazade.dailynotes.businesslogic.database.IAppDatabase
import ir.easazade.dailynotes.businesslogic.repos.IUserRepository
import ir.easazade.dailynotes.businesslogic.repos.UserRepository
import ir.easazade.dailynotes.screens.main.HomeFrag
import ir.easazade.dailynotes.sdk.DataBindings
import ir.easazade.dailynotes.sdk.navigation.Navigator
import ir.easazade.dailynotes.viewmodels.ViewModelFactory

class AppComponent(
  private val databaseModule: IDatabaseModule,
  private val serverModule: IServerModule,
  private val androidModule: IAndroidModule
) : IAppComponent {

  private var userRepo: UserRepository? = null
  private var viewModelFactory: ViewModelFactory? = null

  override fun viewModelFactory(activity: FragmentActivity): ViewModelFactory {
    return if (viewModelFactory != null)
      viewModelFactory!!
    else {
      viewModelFactory = ViewModelFactory(usersRepository())
      viewModelFactory!!
    }
  }

  override fun database(): IAppDatabase = databaseModule.appDatabase()

  override fun navigator(): Navigator<HomeFrag.State, HomeFrag.Args> = androidModule.navigator()

  override fun usersRepository(): IUserRepository {
    return if (userRepo != null)
      userRepo!!
    else {
      userRepo = UserRepository(serverModule.server())
      userRepo!!
    }
  }

  override fun dataBindings(): DataBindings = androidModule.databindings()

}