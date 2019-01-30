package ir.easazade.dailynotes.di

import androidx.fragment.app.FragmentActivity
import ir.easazade.dailynotes.businesslogic.database.IAppDatabase
import ir.easazade.dailynotes.businesslogic.repos.INotesRepository
import ir.easazade.dailynotes.businesslogic.repos.IUserRepository
import ir.easazade.dailynotes.screens.main.HomeFrag
import ir.easazade.dailynotes.sdk.DataBindings
import ir.easazade.dailynotes.sdk.navigation.Navigator
import ir.easazade.dailynotes.viewmodels.ViewModelFactory

interface IAppComponent {

  fun database(): IAppDatabase

  fun navigator(): Navigator<HomeFrag.State, HomeFrag.Args>

  fun usersRepository(): IUserRepository

  fun notesRepository(): INotesRepository

  fun viewModelFactory(): ViewModelFactory

  fun dataBindings(): DataBindings

}