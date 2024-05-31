package com.foregg.presentation.ui.main.injection

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.foregg.domain.model.enums.GenderType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentInjectionBinding
import com.foregg.presentation.util.ForeggToast
import com.foregg.presentation.util.PendingExtraValue
import com.foregg.presentation.util.UserInfo
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
            val id = requireActivity().intent.getLongExtra(PendingExtraValue.TARGET_ID_KEY, -1)
            val time = requireActivity().intent.getStringExtra(PendingExtraValue.INJECTION_TIME_KEY) ?: ""
            viewModel.initView(id, time)
            if(UserInfo.info.genderType == GenderType.MALE) textMaleTitle.text = getString(R.string.injection_male_title, UserInfo.info.spouse)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.image.collect{
                    loadImage(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as InjectionEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: InjectionEvent){
        when(event){
            InjectionEvent.GoToHomeEvent -> findNavController().popBackStack()
            InjectionEvent.ErrorShareToast -> ForeggToast.createToast(requireContext(), R.string.toast_error_no_exist_spouse, Toast.LENGTH_SHORT).show()
            InjectionEvent.SuccessShareToast -> ForeggToast.createToast(requireContext(), R.string.toast_success_share_injection, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImage(url : String){
        if(url.isEmpty()) return
        Glide.with(requireContext())
            .load(url)
            .into(binding.imgInjection)
    }
}