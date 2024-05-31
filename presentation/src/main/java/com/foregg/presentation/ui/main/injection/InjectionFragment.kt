package com.foregg.presentation.ui.main.injection

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentInjectionBinding
import com.foregg.presentation.util.ForeggToast
import com.foregg.presentation.util.PendingExtraValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InjectionFragment : BaseFragment<FragmentInjectionBinding, InjectionPageState, InjectionViewModel>(
    FragmentInjectionBinding::inflate
) {

    override val viewModel: InjectionViewModel by viewModels()


    override fun initView() {
        binding.apply {
            vm = viewModel
            viewModel.initView(requireActivity().intent.getLongExtra(PendingExtraValue.TARGET_ID_KEY, -1))
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as InjectionEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: InjectionEvent){
        when(event){
            InjectionEvent.GoToHomeEvent -> goToHome()
            InjectionEvent.ErrorShareToast -> ForeggToast.createToast(requireContext(), R.string.toast_error_no_exist_spouse, Toast.LENGTH_SHORT).show()
            InjectionEvent.SuccessShareToast -> ForeggToast.createToast(requireContext(), R.string.toast_success_share_injection, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHome(){
        val action = InjectionFragmentDirections.actionInjectionToHome()
        findNavController().navigate(action)
    }
}