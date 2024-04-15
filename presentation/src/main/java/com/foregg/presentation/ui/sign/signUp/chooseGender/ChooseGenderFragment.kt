package com.foregg.presentation.ui.sign.signUp.chooseGender

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSignUpChooseGenderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseGenderFragment : BaseFragment<FragmentSignUpChooseGenderBinding, PageState.Default, ChooseGenderViewModel>(
    FragmentSignUpChooseGenderBinding::inflate
) {

    override val viewModel: ChooseGenderViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as ChooseGenderEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: ChooseGenderEvent){
        when(event){
            ChooseGenderEvent.OnClickFemaleEvent -> goToFemaleSignUp()
            ChooseGenderEvent.OnClickMaleEvent -> goToMaleSignUp()
            ChooseGenderEvent.GoToBackEvent -> findNavController().popBackStack()
        }
    }

    private fun goToFemaleSignUp(){
        val action = ChooseGenderFragmentDirections.actionChooseGenderToFemale()
        findNavController().navigate(action)
    }

    private fun goToMaleSignUp(){
        val action = ChooseGenderFragmentDirections.actionChooseGenderToMale()
        findNavController().navigate(action)
    }
}