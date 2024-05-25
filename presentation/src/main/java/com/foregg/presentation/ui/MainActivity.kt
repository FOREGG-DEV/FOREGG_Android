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
                viewModel.uiState.pageType.collect{
                    navigatePage(it)
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

    private fun navigatePage(type : BottomNavType){
        when(type){
            BottomNavType.CALENDAR -> navController.navigate(R.id.calendarFragment)
            BottomNavType.ACCOUNT -> navController.navigate(R.id.accountFragment)
            BottomNavType.HOME -> navController.navigate(R.id.homeFragment)
            BottomNavType.INFO -> {}
            BottomNavType.PROFILE -> navController.navigate(R.id.profileFragment)
            BottomNavType.OTHER -> {}
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