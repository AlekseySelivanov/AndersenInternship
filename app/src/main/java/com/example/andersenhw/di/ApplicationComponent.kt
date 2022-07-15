package com.example.andersenhw.di

import android.content.Context
import com.example.andersenhw.ui.MainActivity
import com.example.andersenhw.ui.authorization.screens.AuthorizationFragment
import com.example.andersenhw.ui.authorization.screens.LoginFragment
import com.example.andersenhw.ui.authorization.screens.RegistrationFragment
import com.example.andersenhw.ui.home.LoggedInFragment
import com.example.data.local.db.di.DatabaseModule
import com.example.data.remote.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, DatabaseModule::class, DomainModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: AuthorizationFragment)
    fun inject(fragment: LoggedInFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}