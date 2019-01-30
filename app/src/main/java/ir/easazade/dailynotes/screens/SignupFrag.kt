package ir.easazade.dailynotes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.jakewharton.rxbinding3.view.clicks
import ir.easazade.dailynotes.App
import ir.easazade.dailynotes.R
import ir.easazade.dailynotes.sdk.navigation.Arguments
import ir.easazade.dailynotes.sdk.navigation.BaseFrag
import ir.easazade.dailynotes.sdk.navigation.ViewState
import kotlinx.android.synthetic.main.frag_signup.mSignupBtn
import kotlinx.android.synthetic.main.frag_signup.mSignupEmail
import kotlinx.android.synthetic.main.frag_signup.mSignupPassword
import kotlinx.android.synthetic.main.frag_signup.mSignupRepeatPassword
import kotlinx.android.synthetic.main.frag_signup.mSignupUsername
import java.util.concurrent.TimeUnit

class SignupFrag : BaseFrag<SignupFrag.State, SignupFrag.Args>() {

  private val progressDialog by lazy {
    // setup the alert builder
    val builder = AlertDialog.Builder(mActivity)
    builder.setTitle("Creating new Account")
    builder.setMessage("please wait")
    builder.create()
  }

  override fun getArgumentsAndSetProperties(args: SignupFrag.Args) {}

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.frag_signup, container, false)

  override fun initializeViews() {
    fragSubscriptions.add(
        mSignupBtn.clicks()
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe {
              mActivity.userVm().signup(
                  mSignupEmail.text.toString(),
                  mSignupUsername.text.toString(),
                  mSignupPassword.text.toString(),
                  mSignupRepeatPassword.text.toString()
              )
            }
    )
  }

  override fun onLoadNewState(args: SignupFrag.Args) {}

  override fun getCurrentState(): SignupFrag.State = State(args)

  override fun observeViewModels() {
    fragSubscriptions.addAll(
        mActivity.userVm().signupTask.progress.observeOn(ui).subscribe { inProgress ->
          if (inProgress)
            progressDialog.show()
          else {
            if (progressDialog.isShowing)
              progressDialog.dismiss()
          }
        },
        mActivity.userVm().signupTask.failed.observeOn(ui).subscribe {
          mActivity.showSnackBarWithErrorMsg(it)
        },
        mActivity.userVm().signupTask.signedUp.observeOn(ui).subscribe {
          App.component().navigator().destination(LoginFrag()).withArguments(
              LoginFrag.Args(mSignupEmail.text.toString(), mSignupPassword.text.toString())).go()
        }
    )
  }

  class Args : Arguments()
  class State(args: Args) : ViewState(args) {
    override fun createUpdate(): ViewState = this
  }

}