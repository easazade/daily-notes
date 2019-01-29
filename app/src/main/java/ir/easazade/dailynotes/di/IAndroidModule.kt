package ir.easazade.dailynotes.di

import ir.easazade.dailynotes.screens.main.HomeFrag
import ir.easazade.dailynotes.sdk.DataBindings
import ir.easazade.dailynotes.sdk.navigation.Navigator

interface IAndroidModule {

  fun navigator(): Navigator<HomeFrag.State, HomeFrag.Args>

  fun databindings(): DataBindings

}