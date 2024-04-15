package com.foregg.presentation.ui.sign.signUp.female

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSignUpFemaleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFemaleFragment : BaseFragment<FragmentSignUpFemaleBinding, PageState.Default, SignUpFemaleViewModel>(
    FragmentSignUpFemaleBinding::inflate
) {

    override val viewModel: SignUpFemaleViewModel by viewModels()

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
                    inspectEvent(it as SignUpFemaleEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: SignUpFemaleEvent){
        when(event){
            SignUpFemaleEvent.GoToBackEvent -> findNavController().popBackStack()
        }
    }
}