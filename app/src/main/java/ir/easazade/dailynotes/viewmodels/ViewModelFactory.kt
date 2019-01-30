package ir.easazade.dailynotes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.easazade.dailynotes.businesslogic.repos.INotesRepository
import ir.easazade.dailynotes.businesslogic.repos.IUserRepository

class ViewModelFactory(
  private var userRepository: IUserRepository,
  private var notesRepository: INotesRepository
) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(viewModelClass: Class<T>): T {
    return when {
      (viewModelClass.isAssignableFrom(UserViewModel::class.java)) ->
        return UserViewModel(userRepository, Schedulers.io(), AndroidSchedulers.mainThread()) as T
      (viewModelClass.isAssignableFrom(NotesViewModel::class.java)) ->
        return NotesViewModel(notesRepository, Schedulers.io(), AndroidSchedulers.mainThread()) as T
      else -> ViewModel::class.java.newInstance() as T
    }
  }
}