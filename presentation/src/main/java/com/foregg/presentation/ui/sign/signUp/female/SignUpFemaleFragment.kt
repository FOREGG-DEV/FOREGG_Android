package com.foregg.presentation.ui.sign.signUp.female

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.SurgeryType
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSignUpFemaleBinding
import com.foregg.presentation.ui.sign.signUp.female.adapter.SurgeryTypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFemaleFragment : BaseFragment<FragmentSignUpFemaleBinding, SignUpFemalePageState, SignUpFemaleViewModel>(
    FragmentSignUpFemaleBinding::inflate
) {

    override val viewModel: SignUpFemaleViewModel by viewModels()

    private val surgeryTypeAdapter : SurgeryTypeAdapter by lazy {
        SurgeryTypeAdapter(object : SurgeryTypeAdapter.SurgeryTypeDelegate{
            override fun onClickType(type: SurgeryType) {
                setTextSurgeryType(type.type)
                viewModel.onClickSpinner()
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerSugeryType.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = surgeryTypeAdapter
            }

            viewModel.getSurgeryType()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.surgeryTypeList.collect{
                    surgeryTypeAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as SignUpFemaleEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: SignUpFemaleEvent){
        when(event){
            SignUpFemaleEvent.GoToBackEvent -> findNavController().popBackStack()
        }
    }

    private fun setTextSurgeryType(type : String){
        binding.apply {
            textSurgeryType.text = type
        }
    }
}