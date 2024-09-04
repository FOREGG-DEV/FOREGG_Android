package com.hugg.presentation.ui.main.profile.ask

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hugg.presentation.PageState
import com.hugg.presentation.R
import com.hugg.presentation.base.BaseFragment
import com.hugg.presentation.databinding.FragmentProfileAskBinding
import com.hugg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AskFragment : BaseFragment<FragmentProfileAskBinding, PageState.Default, AskViewModel>(
    FragmentProfileAskBinding::inflate
) {

    override val viewModel: AskViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as AskEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: AskEvent){
        when(event){
            AskEvent.GoToBackEvent -> findNavController().popBackStack()
            AskEvent.OnClickCopyEmailEvent -> copyClipBoard()
        }
    }

    private fun copyClipBoard(){
        val clipboard: ClipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", binding.textEmail.text)
        clipboard.setPrimaryClip(clip)
        ForeggToast.createToast(requireContext(), R.string.toast_clipboard_email, Toast.LENGTH_SHORT).show()
    }
}