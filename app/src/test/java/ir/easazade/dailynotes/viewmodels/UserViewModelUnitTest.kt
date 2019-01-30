package ir.easazade.dailynotes.viewmodels

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import ir.easazade.dailynotes.businesslogic.entities.User
import ir.easazade.dailynotes.businesslogic.repos.IUserRepository
import ir.easazade.dailynotes.businesslogic.states.UState
import ir.easazade.dailynotes.helpers.FakeValues
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserViewModelUnitTest {
  lateinit var vm: UserViewModel
  lateinit var repo: IUserRepository
  lateinit var fakes: FakeValues

  lateinit var loginTaskProgress: TestObserver<Boolean>
  lateinit var loginTaskLoggedIn: TestObserver<User>
  lateinit var loginTaskFailure: TestObserver<Boolean>

  lateinit var signupProgress: TestObserver<Boolean>
  lateinit var signupSuccess: TestObserver<User>
  lateinit var signupFailure: TestObserver<Boolean>

  @Before
  fun setup() {
    repo = mockk()
    vm = UserViewModel(repo, Schedulers.trampoline(), Schedulers.trampoline())
    fakes = FakeValues()
    //test observers
    loginTaskProgress = vm.loginTask.progress.test()
    loginTaskLoggedIn = vm.loginTask.loggedIn.test()
    loginTaskFailure = vm.loginTask.failed.test()

    signupProgress = vm.signupTask.progress.test()
    signupSuccess = vm.signupTask.signedUp.test()
    signupFailure = vm.signupTask.failed.test()

  }

  @After
  fun tearDown() {

  }

  @Test
  fun dummy() {

  }

  @Test
  fun shouldLogin() {
    //with
    every { repo.login(fakes.email, fakes.pass) } returns Observable.just(
        UState.success(fakes.user))
    //when
    vm.login(fakes.email, fakes.pass)
    //
    loginTaskFailure.assertNoValues()
    loginTaskLoggedIn.assertValue(fakes.user)
  }

  @Test
  fun shouldSignup() {
    //when
    every { repo.signup(fakes.email, fakes.username, fakes.pass, fakes.passRepeat) } returns
        Observable.just(UState.success(fakes.user))
    //when
    vm.signup(fakes.email, fakes.username, fakes.pass, fakes.passRepeat)
    //then
    signupSuccess.assertValue(fakes.user)
    signupFailure.assertNoValues()
  }

}