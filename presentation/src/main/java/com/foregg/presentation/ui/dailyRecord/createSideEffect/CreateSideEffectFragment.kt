package com.foregg.presentation.ui.dailyRecord.createSideEffect

import androidx.fragment.app.viewModels
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentCreateSideEffectBinding
import com.foregg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateSideEffectFragment : BaseFragment<FragmentCreateSideEffectBinding, CreateSideEffectPageState, CreateSideEffectViewModel>(
    FragmentCreateSideEffectBinding::inflate
) {

    override val viewModel: CreateSideEffectViewModel by viewModels()

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
                    sortEvent(it as CreateSideEffectEvent)
                }
            }
        }
    }

    private fun sortEvent(event: CreateSideEffectEvent) {
        when(event) {
            CreateSideEffectEvent.PopCreateSideFragment -> findNavController().popBackStack()
            CreateSideEffectEvent.InSufficientTextEvent -> ForeggToast.createToast(requireContext(), "부작용을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}