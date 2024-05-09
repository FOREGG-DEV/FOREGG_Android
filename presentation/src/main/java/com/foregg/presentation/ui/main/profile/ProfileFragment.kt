package com.foregg.presentation.ui.main.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfilePageState, ProfileViewModel>(
    FragmentProfileBinding::inflate
) {

    override val viewModel: ProfileViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            viewModel.getMyInfo()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as ProfileEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: ProfileEvent){
        when(event){
            ProfileEvent.GoToEditProfileEvent -> goToEdit()
            ProfileEvent.GoToMyMedicineInjectionEvent -> goToMyMedicineInjection()
            ProfileEvent.GoToAskEvent -> goToAsk()
            ProfileEvent.GoToFAQEvent -> {}
            ProfileEvent.GoToNoticeEvent -> {}
            ProfileEvent.GoToPolicyEvent -> {}
            ProfileEvent.GoToAccountSettingEvent -> goToAccount()
        }
    }

    private fun goToEdit(){
        val action = ProfileFragmentDirections.actionProfileToEdit()
        findNavController().navigate(action)
    }

    private fun goToMyMedicineInjection(){
        val action = ProfileFragmentDirections.actionProfileToMyMedical()
        findNavController().navigate(action)
    }

    private fun goToAsk(){
        val action = ProfileFragmentDirections.actionProfileToAsk()
        findNavController().navigate(action)
    }

    private fun goToAccount(){
        val action = ProfileFragmentDirections.actionProfileToAccount()
        findNavController().navigate(action)
    }
}