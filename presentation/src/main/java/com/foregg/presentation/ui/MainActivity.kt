package com.foregg.presentation.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.foregg.domain.model.enums.BottomNavType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseActivity
import com.foregg.presentation.databinding.ActivityMainBinding
import com.foregg.presentation.util.PendingExtraValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityPageState, MainActivityViewModel>(
        ActivityMainBinding::inflate
    ) {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 5000
    }

    override val viewModel: MainActivityViewModel by viewModels()
    private lateinit var navController: NavController

    override fun initView() {
        binding.apply {
            vm = viewModel
            permissionCheck()
            initNavigation()
        }
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            handleIntent(it)
        }
    }

    private fun handleIntent(intent: Intent) {
        val targetFragment = intent.getStringExtra(PendingExtraValue.KEY)
        val targetId =  intent.getLongExtra(PendingExtraValue.TARGET_ID_KEY, -1)
        targetFragment?.let {
            navigateToTargetFragment(it, targetId)
        }
    }

    private fun navigateToTargetFragment(targetFragment: String, targetId : Long) {
        when (targetFragment) {
            PendingExtraValue.INJECTION -> {
                navController.navigate(R.id.homeFragment)
                //navController.navigate(R.id.)
            }
            PendingExtraValue.TODAY_RECORD-> {
                navController.navigate(R.id.homeFragment)
                navController.navigate(R.id.dailyRecordFragment)
            }
            else -> navController.navigate(R.id.homeFragment)
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

    private fun permissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionCheck = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }
}