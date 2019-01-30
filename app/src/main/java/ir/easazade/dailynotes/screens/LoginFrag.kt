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
import kotlinx.android.synthetic.main.frag_login.mLoginBtn
import kotlinx.android.synthetic.main.frag_login.mLoginEmail
import kotlinx.android.synthetic.main.frag_login.mLoginForgotPass
import kotlinx.android.synthetic.main.frag_login.mLoginPassword
import kotlinx.android.synthetic.main.frag_login.mLoginRegister
import java.util.concurrent.TimeUnit

class LoginFrag : BaseFrag<LoginFrag.State, LoginFrag.Args>() {

  private val progressDialog by lazy {
    // setup the alert builder
    val builder = AlertDialog.Builder(mActivity)
    builder.setTitle("Logging In")
    builder.setMessage("please wait")
    builder.create()
  }

  override fun getArgumentsAndSetProperties(args: Args) {

  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.frag_login, container, false)

  override fun initializeViews() {
    mLoginForgotPass.setOnClickListener { _ ->
      mActivity.showDevelopingMessageSnackBar()
    }
    mLoginRegister.setOnClickListener { _ ->
      App.component().navigator().destination(SignupFrag()).withArguments(SignupFrag.Args()).go()
    }
//    mLoginBtn.setOnClickListener { _ ->
//      mActivity.userVm().login(mLoginEmail.text.toString(), mLoginPassword.text.toString())
//    }
    fragSubscriptions.add(
        mLoginBtn.clicks()
            .debounce(200,TimeUnit.MILLISECONDS)
            .subscribe {
              mActivity.userVm().login(mLoginEmail.text.toString(), mLoginPassword.text.toString())
            }
    )
  }

  override fun onLoadNewState(args: Args) {
    args.email?.let { mLoginEmail.setText(it) }
    args.pass?.let { mLoginPassword.setText(it) }
  }

  override fun getCurrentState(): State = State(args)

  override fun observeViewModels() {
    fragSubscriptions.addAll(
        mActivity.userVm().loginTask.progress.observeOn(ui).subscribe { inProgress ->
          if (inProgress)
            progressDialog.show()
          else {
            if (progressDialog.isShowing)
              progressDialog.dismiss()
          }
        },
        mActivity.userVm().loginTask.failed.observeOn(ui).subscribe {
          mActivity.showSnackBarWithErrorMsg(it)
        },
        mActivity.userVm().loginTask.loggedIn.observeOn(ui).subscribe {
          App.component().navigator().destination(HomeFrag()).withArguments(HomeFrag.Args()).go()
        }
    )
  }

  class Args(
    val email: String? = null,
    val pass: String? = null
  ) : Arguments()

  class State(args: Arguments) : ViewState(args) {
    override fun createUpdate(): ViewState = this
  }

}