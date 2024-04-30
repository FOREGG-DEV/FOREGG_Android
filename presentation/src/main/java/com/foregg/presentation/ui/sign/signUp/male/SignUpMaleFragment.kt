package com.foregg.presentation.ui.sign.signUp.male

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSignUpMaleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpMaleFragment : BaseFragment<FragmentSignUpMaleBinding, SignUpMalePageState, SignUpMaleViewModel>(
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
            SignUpMaleEvent.GoToMainEvent -> goToMain()
        }
    }

    private fun goToMain(){
        //TODO 메인 화면으로 이동
        Log.d("TAG", binding.editTextShareCode.text.toString())
    }
}