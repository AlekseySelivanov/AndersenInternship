package com.example.andersenhw.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.andersenhw.FinalApp
import com.example.andersenhw.R
import com.example.andersenhw.ui.authorization.screens.AuthorizationFragment
import com.example.andersenhw.ui.splash.SplashFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ActivityViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as FinalApp).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            viewModel.logOut.onEach {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, AuthorizationFragment.newInstance())
                        .addToBackStack(AuthorizationFragment::class.java.name)
                        .commit()

            }.launchIn(this)
        }

        installSplashScreen().apply {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, SplashFragment.newInstance()) // Splash fragment
                    .addToBackStack(SplashFragment::class.java.name)
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is AuthorizationFragment) {
            finishAffinity()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}