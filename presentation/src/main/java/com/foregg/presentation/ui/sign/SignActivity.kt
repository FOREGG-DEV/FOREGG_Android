package com.foregg.presentation.ui.sign

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.foregg.presentation.PageState
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseActivity
import com.foregg.presentation.databinding.ActivitySignBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : BaseActivity<ActivitySignBinding, PageState.Default, SignViewModel>(ActivitySignBinding::inflate) {

    override val viewModel: SignViewModel by viewModels()
    private lateinit var navController: NavController

    override fun initView() {

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