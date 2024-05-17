package com.foregg.presentation.ui.main.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foregg.domain.model.enums.GenderType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentProfileBinding
import com.foregg.presentation.util.UserInfo
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
                viewModel.uiState.spouse.collect{
                    if(UserInfo.info.genderType == GenderType.MALE) changeText(it)
                }
            }
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
            ProfileEvent.GoToAccountSettingEvent -> {}
        }
    }

    private fun goToEdit(){
        val action = ProfileFragmentDirections.actionProfileToEdit()
        findNavController().navigate(action)
    }

    private fun changeText(spouse : String){
        binding.apply {
            btnMyMedicineInjection.text = getString(R.string.profile_spouse_medicine_injection, spouse)
        }
    }
}