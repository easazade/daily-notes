package ir.easazade.dailynotes.businesslogic.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import ir.easazade.dailynotes.App

class ConnectivityChangeReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context?, intent: Intent?) {
    context?.let { App.connected.accept(isConnected(context)) }
  }

  private fun isConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
  }

}