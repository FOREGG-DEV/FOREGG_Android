package com.foregg.presentation.ui.main.information.subsidyDetail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.InfoCategoryType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSubsidyDetailBinding
import com.foregg.presentation.ui.main.information.adapter.InformationDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubsidyDetailFragment : BaseFragment<FragmentSubsidyDetailBinding, SubsidyDetailPageState, SubsidyDetailViewModel>(
    FragmentSubsidyDetailBinding::inflate
) {
    override val viewModel: SubsidyDetailViewModel by viewModels()

    private val arg : SubsidyDetailFragmentArgs by navArgs()

    private val infoAdapter: InformationDetailAdapter by lazy {
        InformationDetailAdapter(true, object : InformationDetailAdapter.InformationDetailAdapterDelegate {
            override fun onClickCard(url: String) {

            }
        })
    }

    override fun initView() {
        binding.apply {
            setTitle()
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
            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as SubsidyDetailEvent)
                }
            }
        }
    }

    private fun setTitle() {
        binding.textTitle.text = when(arg.type) {
            InfoCategoryType.ESSENTIAL -> getString(R.string.info_pregnancy)
            InfoCategoryType.HUGG_PICK -> getString(R.string.info_infertility)
            InfoCategoryType.NOTHING -> ""
        }
    }

    private fun inspectEvent(event : SubsidyDetailEvent){
        when(event){
            SubsidyDetailEvent.OnClickBack -> findNavController().popBackStack()
        }
    }
}