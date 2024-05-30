package com.foregg.presentation.ui.main.information.subsidyDetail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSubsidyDetailBinding
import com.foregg.presentation.ui.main.information.adapter.GridSpacingItemDecoration
import com.foregg.presentation.ui.main.information.adapter.InformationAdapter
import com.foregg.presentation.util.px
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SubsidyDetailFragment : BaseFragment<FragmentSubsidyDetailBinding, SubsidyDetailPageState, SubsidyDetailViewModel>(
    FragmentSubsidyDetailBinding::inflate
) {
    override val viewModel: SubsidyDetailViewModel by viewModels()

    private val infoAdapter = InformationAdapter()

    override fun initView() {
        binding.apply {
            gridRecyclerView.adapter = infoAdapter
            gridRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            gridRecyclerView.addItemDecoration(
                GridSpacingItemDecoration(3, 20.px)
            )
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.subsidyList.collect {
                    infoAdapter.submitList(it)
                }
            }
        }
    }
}