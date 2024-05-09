package com.foregg.presentation.ui.main.profile.myMedicineInjection

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.ProfileMedicineInjectionType
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentMyMedicineInjectionBinding
import com.foregg.presentation.ui.main.profile.myMedicineInjection.adapter.MedicineInjectionCardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MedicineInjectionFragment : BaseFragment<FragmentMyMedicineInjectionBinding, MedicineInjectionPageState, MedicineInjectionViewModel>(
    FragmentMyMedicineInjectionBinding::inflate
) {

    override val viewModel: MedicineInjectionViewModel by viewModels()

    private val medicineInjectionCardAdapter : MedicineInjectionCardAdapter by lazy {
        MedicineInjectionCardAdapter(object : MedicineInjectionCardAdapter.MedicineInjectionCardDelegate{
            override fun onClickDetail(id: Long) {
                //
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewMedicineInjection.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = medicineInjectionCardAdapter
            }
            bindTab()
            viewModel.getMyMedicineInjection()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.itemList.collect{
                    medicineInjectionCardAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as MedicineInjectionEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: MedicineInjectionEvent){
        when(event){
            MedicineInjectionEvent.GoToBackEvent -> findNavController().popBackStack()
        }
    }

    private fun bindTab(){
        binding.customTabBar.apply {
            leftTab.setOnClickListener {
                leftBtnClicked()
                viewModel.onClickTab(ProfileMedicineInjectionType.MEDICINE)

            }
            rightTab.setOnClickListener {
                rightBtnClicked()
                viewModel.onClickTab(ProfileMedicineInjectionType.INJECTION)
            }
        }
    }
}