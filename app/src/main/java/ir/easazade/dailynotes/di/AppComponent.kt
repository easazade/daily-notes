package ir.easazade.dailynotes.di

import android.content.Context
import android.net.ConnectivityManager
import ir.easazade.dailynotes.App
import ir.easazade.dailynotes.businesslogic.database.IAppDatabase
import ir.easazade.dailynotes.businesslogic.repos.INotesRepository
import ir.easazade.dailynotes.businesslogic.repos.IUserRepository
import ir.easazade.dailynotes.businesslogic.repos.NotesRepository
import ir.easazade.dailynotes.businesslogic.repos.UserRepository
import ir.easazade.dailynotes.screens.HomeFrag
import ir.easazade.dailynotes.sdk.DataBindings
import ir.easazade.dailynotes.sdk.navigation.Navigator
import ir.easazade.dailynotes.viewmodels.ViewModelFactory

class AppComponent(
  private val app: App,
  private val databaseModule: IDatabaseModule,
  private val serverModule: IServerModule,
  private val androidModule: IAndroidModule
) : IAppComponent {

  private var userRepo: UserRepository? = null
  private var notesRepo: NotesRepository? = null
  private var viewModelFactory: ViewModelFactory? = null

  override fun viewModelFactory(): ViewModelFactory {
    return if (viewModelFactory != null)
      viewModelFactory!!
    else {
      viewModelFactory = ViewModelFactory(usersRepository(), notesRepository())
      viewModelFactory!!
    }
  }

  override fun database(): IAppDatabase = databaseModule.appDatabase()

  override fun navigator(): Navigator<HomeFrag.State, HomeFrag.Args> = androidModule.navigator()

  override fun usersRepository(): IUserRepository {
    return if (userRepo != null)
      userRepo!!
    else {
      userRepo = UserRepository(serverModule.server(), {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
      }, {
        usersRepository().isLoggedIn()
      }, database())
      userRepo!!
    }
  }

  override fun notesRepository(): INotesRepository {
    return if (notesRepo != null)
      notesRepo!!
    else {
      notesRepo = NotesRepository(database(),serverModule.server(), {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
      }, {
        usersRepository().isLoggedIn()
      })
      notesRepo!!
    }
  }

  override fun dataBindings(): DataBindings = androidModule.databindings()

}