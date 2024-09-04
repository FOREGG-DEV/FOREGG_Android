package com.hugg.presentation.ui.main.profile.myMedicineInjection

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hugg.domain.model.enums.CalendarType
import com.hugg.domain.model.enums.ProfileMedicineInjectionType
import com.hugg.presentation.base.BaseFragment
import com.hugg.presentation.databinding.FragmentMyMedicineInjectionBinding
import com.hugg.presentation.ui.main.profile.myMedicineInjection.adapter.MedicineInjectionCardAdapter
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
                goToDetail(id)
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
            leftBtnClicked { viewModel.onClickTab(ProfileMedicineInjectionType.MEDICINE) }
            rightBtnClicked { viewModel.onClickTab(ProfileMedicineInjectionType.INJECTION) }
        }
    }

    private fun goToDetail(id : Long){
        val recordType = viewModel.getRecordType()
        val action = MedicineInjectionFragmentDirections.actionMyMedicalToCalendarDetail(id = id, type = CalendarType.EDIT, scheduleType = recordType, isProfile = true)
        findNavController().navigate(action)

    }
}