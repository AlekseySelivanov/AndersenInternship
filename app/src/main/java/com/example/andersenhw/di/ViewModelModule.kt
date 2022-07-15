package com.example.andersenhw.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andersenhw.ui.ActivityViewModel
import com.example.andersenhw.ui.authorization.screens.AuthenticationViewModel
import com.example.andersenhw.ui.authorization.screens.ViewModelFactory
import com.example.andersenhw.ui.home.LoggedInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthenticationViewModel::class)
    fun bindLoginViewModel(viewModel: AuthenticationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoggedInViewModel::class)
    fun bindLoggedViewModel(loggedInViewModel: LoggedInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ActivityViewModel::class)
    fun bindActivityViewModel(activityInViewModel: ActivityViewModel): ViewModel

}