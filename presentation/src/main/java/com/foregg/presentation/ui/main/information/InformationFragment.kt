package com.foregg.presentation.ui.main.information

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentInformationBinding
import com.foregg.presentation.ui.main.information.adapter.InformationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InformationFragment : BaseFragment<FragmentInformationBinding, InformationPageState, InformationViewModel>(
    FragmentInformationBinding::inflate
) {
    override val viewModel: InformationViewModel by viewModels()

    private val informationAdapter: InformationAdapter by lazy {
        InformationAdapter(mapOf(), requireContext(), object : InformationAdapter.InformationAdapterDelegate {
            override fun onClickBtnDetail(position: Int) {
                viewModel.onClickBtnDetail(position)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            infoRecyclerView.adapter = informationAdapter
            infoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.infoList.collect {
                    informationAdapter.updateData(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as InformationEvent)
                }
            }
        }
    }

    private fun sortEvent(event: InformationEvent) {
        when(event) {
            InformationEvent.GoToPregnancyDetailEvent -> goToSubsidyDetail()
            InformationEvent.GoToInfertilityDetailEvent -> goToInfertilityDetail()
            InformationEvent.GoBackEvent -> goToBack()
        }
    }

    private fun goToSubsidyDetail() {
        val action = InformationFragmentDirections.actionInformationToSubsidyDetail(0)
        findNavController().navigate(action)
    }

    private fun goToInfertilityDetail() {
        val action = InformationFragmentDirections.actionInformationToSubsidyDetail(1)
        findNavController().navigate(action)
    }

    private fun goToBack() {
        findNavController().popBackStack()
    }
}