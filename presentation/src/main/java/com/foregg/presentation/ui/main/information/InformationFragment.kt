package com.foregg.presentation.ui.main.information

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentInformationBinding
import com.foregg.presentation.ui.main.information.adapter.InformationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InformationFragment : BaseFragment<FragmentInformationBinding, InformationPageState, InformationViewModel>(
    FragmentInformationBinding::inflate
) {
    override val viewModel: InformationViewModel by viewModels()

    private val informationAdapter: InformationAdapter by lazy {
        InformationAdapter(mapOf(), requireContext(), object : InformationAdapter.InformationAdapterDelegate {
            override fun onClickBtnSubsidyDetail() {
                viewModel.onClickBtnSubsidyDetail()
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
            InformationEvent.GoToSubsidyDetailEvent -> goToSubsidyDetail()
            InformationEvent.GoBackEvent -> goToBack()
        }
    }

    private fun goToSubsidyDetail() {
        val action = InformationFragmentDirections.actionInformationToSubsidyDetail()
        findNavController().navigate(action)
    }

    private fun goToBack() {
        findNavController().popBackStack()
    }
}