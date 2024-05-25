package com.foregg.presentation.ui

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.foregg.domain.model.enums.BottomNavType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseActivity
import com.foregg.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, MainActivityPageState, MainActivityViewModel>(
        ActivityMainBinding::inflate
    ) {
    override val viewModel: MainActivityViewModel by viewModels()
    private lateinit var navController: NavController

    override fun initView() {

        binding.apply {
            vm = viewModel
            initNavigation()
        }
    }

    override fun initState() {
        repeatOnStarted {
            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as MainActivityEvent)
                }
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            changeBottomNavigationView(destination.id)
        }
    }

    private fun inspectEvent(event: MainActivityEvent) {
        when (event) {
            MainActivityEvent.GoToCalendar -> navController.navigate(R.id.calendarFragment)
            MainActivityEvent.GoToMain -> navController.navigate(R.id.homeFragment)
            MainActivityEvent.GoToAccount -> navController.navigate(R.id.accountFragment)
            MainActivityEvent.GoToProfile -> navController.navigate(R.id.profileFragment)
            MainActivityEvent.GoToInfo -> {}
        }
    }

    private fun changeBottomNavigationView(id: Int) {
        when (id) {
            R.id.homeFragment -> viewModel.updatePageType(BottomNavType.HOME)
            R.id.calendarFragment -> viewModel.updatePageType(BottomNavType.CALENDAR)
            R.id.profileFragment -> viewModel.updatePageType(BottomNavType.PROFILE)
            R.id.accountFragment -> viewModel.updatePageType(BottomNavType.ACCOUNT)
            else -> viewModel.updatePageType(BottomNavType.OTHER)
        }
    }
}