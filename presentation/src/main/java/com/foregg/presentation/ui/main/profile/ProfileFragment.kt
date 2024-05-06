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
            ProfileEvent.GoToMyMedicineInjectionEvent -> {}
            ProfileEvent.GoToAskEvent -> {}
            ProfileEvent.GoToFAQEvent -> {}
            ProfileEvent.GoToNoticeEvent -> {}
            ProfileEvent.GoToPolicyEvent -> {}
        }
    }

    private fun goToEdit(){
        val action = ProfileFragmentDirections.actionProfileToEdit()
        findNavController().navigate(action)
    }
}