package com.hugg.presentation.ui.dailyRecord.createSideEffect

import androidx.fragment.app.viewModels
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hugg.presentation.R
import com.hugg.presentation.base.BaseFragment
import com.hugg.presentation.databinding.FragmentCreateSideEffectBinding
import com.hugg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateSideEffectFragment : BaseFragment<FragmentCreateSideEffectBinding, CreateSideEffectPageState, CreateSideEffectViewModel>(
    FragmentCreateSideEffectBinding::inflate
) {

    override val viewModel: CreateSideEffectViewModel by viewModels()

    private val arg : CreateSideEffectFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewModel.initView(arg)
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
            CreateSideEffectEvent.PopCreateSideFragment -> findNavController().popBackStack(R.id.dailyRecordFragment, false)
            CreateSideEffectEvent.InSufficientTextEvent -> ForeggToast.createToast(requireContext(), R.string.toast_error_empty_side_effect, Toast.LENGTH_SHORT).show()
        }
    }
}