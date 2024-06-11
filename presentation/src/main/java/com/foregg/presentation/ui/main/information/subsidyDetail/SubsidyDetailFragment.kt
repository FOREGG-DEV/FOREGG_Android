package com.foregg.presentation.ui.main.information.subsidyDetail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSubsidyDetailBinding
import com.foregg.presentation.ui.main.information.adapter.GridSpacingItemDecoration
import com.foregg.presentation.ui.main.information.adapter.InformationAdapter
import com.foregg.presentation.ui.main.information.adapter.InformationDetailAdapter
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubsidyDetailFragment : BaseFragment<FragmentSubsidyDetailBinding, SubsidyDetailPageState, SubsidyDetailViewModel>(
    FragmentSubsidyDetailBinding::inflate
) {
    override val viewModel: SubsidyDetailViewModel by viewModels()

    private val infoAdapter = InformationDetailAdapter(true)

    override fun initView() {
        binding.apply {
            val position = arguments?.getLong("position") ?: 0L
            setTitle(position)
            ForeggLog.D(position.toString())
            gridRecyclerView.adapter = infoAdapter
            gridRecyclerView.layoutManager = LinearLayoutManager(requireContext())
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

    private fun setTitle(position: Long) {
        when(position) {
            0L -> binding.textTitle.text = getString(R.string.info_pregnancy)
            1L -> binding.textTitle.text = getString(R.string.info_infertility)
        }
    }
}