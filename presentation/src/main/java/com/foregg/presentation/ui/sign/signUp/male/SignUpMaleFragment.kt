package com.foregg.presentation.ui.sign.signUp.male

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSignUpMaleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpMaleFragment : BaseFragment<FragmentSignUpMaleBinding, PageState.Default, SignUpMaleViewModel>(
    FragmentSignUpMaleBinding::inflate
) {

    override val viewModel: SignUpMaleViewModel by viewModels()

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
                    inspectEvent(it as SignUpMaleEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: SignUpMaleEvent){
        when(event){
            SignUpMaleEvent.GoToBackEvent -> findNavController().popBackStack()
        }
    }
}