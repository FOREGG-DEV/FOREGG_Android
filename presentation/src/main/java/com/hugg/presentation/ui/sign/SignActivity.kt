package com.hugg.presentation.ui.sign

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hugg.presentation.PageState
import com.hugg.presentation.R
import com.hugg.presentation.base.BaseActivity
import com.hugg.presentation.databinding.ActivitySignBinding
import com.hugg.presentation.util.AlarmService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : BaseActivity<ActivitySignBinding, PageState.Default, SignViewModel>(ActivitySignBinding::inflate) {

    override val viewModel: SignViewModel by viewModels()
    private lateinit var navController: NavController

    override fun initView() {
        AlarmService.stopAlarm()
        binding.apply {
            vm = viewModel
            initNavigation()
        }
    }

    override fun initState() {

    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
    }
}