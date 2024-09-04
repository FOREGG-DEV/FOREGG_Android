package com.hugg.presentation.ui.sign.signUp.male

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hugg.presentation.R
import com.hugg.presentation.base.BaseFragment
import com.hugg.presentation.databinding.FragmentSignUpMaleBinding
import com.hugg.presentation.ui.MainActivity
import com.hugg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpMaleFragment : BaseFragment<FragmentSignUpMaleBinding, SignUpMalePageState, SignUpMaleViewModel>(
    FragmentSignUpMaleBinding::inflate
) {

    override val viewModel: SignUpMaleViewModel by viewModels()

    private val signUpMaleFragmentArgs : SignUpMaleFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewModel.setMaleInfo(signUpMaleFragmentArgs)
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
            SignUpMaleEvent.ErrorShareCode -> ForeggToast.createToast(requireContext(), R.string.toast_error_not_correct_share_code, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToMain(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}