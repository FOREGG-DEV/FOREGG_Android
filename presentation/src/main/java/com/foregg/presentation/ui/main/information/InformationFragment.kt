package com.foregg.presentation.ui.main.information

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.InfoCategoryType
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
        InformationAdapter(object : InformationAdapter.InformationAdapterDelegate {
            override fun onClickBtnDetail(type : InfoCategoryType) {
                goToSubsidyDetail(type)
            }

            override fun onClickUrl(url: String) {
                goToWebLink(url)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            infoRecyclerView.apply {
                adapter = informationAdapter
                layoutManager = LinearLayoutManager(context)
            }

            viewModel.getInformation()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.infoList.collect {
                    informationAdapter.submitList(it)
                }
            }
        }
    }

    private fun goToSubsidyDetail(type : InfoCategoryType) {
        val action = InformationFragmentDirections.actionInformationToSubsidyDetail(type)
        findNavController().navigate(action)
    }

    private fun goToWebLink(url : String){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }
}