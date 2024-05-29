package com.foregg.presentation.ui.main.information

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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

    private val subsidyAdapter = InformationAdapter()
    private val essentialInfoAdapter = InformationAdapter()
    private val womanInfoAdapter = InformationAdapter()
    private val husbandInfoAdapter = InformationAdapter()

    override fun initView() {
        binding.apply {
            vm = viewModel
            subsidyRecyclerView.adapter = subsidyAdapter
            subsidyRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            essentialInfoRecyclerView.adapter = essentialInfoAdapter
            essentialInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            womanContentsRecyclerView.adapter = womanInfoAdapter
            womanContentsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            husbandContentsRecyclerView.adapter = husbandInfoAdapter
            husbandContentsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.infoList.collect {
                    subsidyAdapter.submitList(it)
                    essentialInfoAdapter.submitList(it)
                    womanInfoAdapter.submitList(it)
                    husbandInfoAdapter.submitList(it)
                }
            }
        }
    }
}