package com.hugg.presentation.ui.sign.splash

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hugg.presentation.PageState
import com.hugg.presentation.base.BaseFragment
import com.hugg.presentation.databinding.FragmentSplashBinding
import com.hugg.presentation.ui.MainActivity
import com.hugg.presentation.util.PendingExtraValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, PageState.Default, SplashViewModel>(
    FragmentSplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
            viewModel.checkLogin()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as SplashEvent)
                }
            }
        }
    }

    private fun sortEvent(event: SplashEvent){
        when(event){
            SplashEvent.GoToSignEvent -> goToOnboarding()
            SplashEvent.GoToMainEvent -> goToMain()
        }
    }

    private fun goToMain(){
        val pendingIntent = requireActivity().intent.getStringExtra(PendingExtraValue.KEY) ?: ""
        val targetIdIntent = requireActivity().intent.getLongExtra(PendingExtraValue.TARGET_ID_KEY, -1)
        val timeIntent = requireActivity().intent.getStringExtra(PendingExtraValue.INJECTION_TIME_KEY) ?: ""
        val intent = Intent(requireActivity(), MainActivity::class.java).apply {
            if(pendingIntent.isNotEmpty()) putExtra(PendingExtraValue.KEY, pendingIntent)
            if(targetIdIntent != (-1).toLong()) putExtra(PendingExtraValue.TARGET_ID_KEY, targetIdIntent)
            if(timeIntent.isNotEmpty()) putExtra(PendingExtraValue.INJECTION_TIME_KEY, timeIntent)
        }
        startActivity(intent)
        requireActivity().finish()
    }

    private fun goToOnboarding(){
        val action = SplashFragmentDirections.actionSplashToOnboarding()
        findNavController().navigate(action)
    }
}