package com.foregg.presentation.ui.main.information.subsidyDetail

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.InformationType
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
                goToWebLink(url)
            }
        })
    }

    override fun initView() {
        binding.apply {
            setTitle()
            gridRecyclerView.apply {
                adapter = infoAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }

        viewModel.getDetailList(arg.type)
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
            InformationType.ESSENTIAL -> getString(R.string.info_pregnancy)
            InformationType.HUGG_PICK -> getString(R.string.info_infertility)
            InformationType.NOTHING -> ""
        }
    }

    private fun inspectEvent(event : SubsidyDetailEvent){
        when(event){
            SubsidyDetailEvent.OnClickBack -> findNavController().popBackStack()
        }
    }

    private fun goToWebLink(url : String){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }
}