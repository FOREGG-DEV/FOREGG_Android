package com.foregg.presentation.ui.main.profile.account

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.PageState
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentProfileAccountBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.sign.SignActivity
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.ForeggToast
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileAccountFragment : BaseFragment<FragmentProfileAccountBinding, PageState.Default, ProfileAccountViewModel>(
    FragmentProfileAccountBinding::inflate
) {

    @Inject
    lateinit var commonDialog: CommonDialog

    override val viewModel: ProfileAccountViewModel by viewModels()

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
                    inspectEvent(it as ProfileAccountEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: ProfileAccountEvent){
        when(event){
            ProfileAccountEvent.GoToBackEvent -> findNavController().popBackStack()
            ProfileAccountEvent.OnClickLogoutEvent -> logoutDialog()
            ProfileAccountEvent.OnClickUnregisterEvent -> unRegisterDialog()
            ProfileAccountEvent.CompleteLogoutEvent -> logoutCompleteDialog()
            ProfileAccountEvent.CompleteUnregisterEvent -> unRegisterCompleteDialog()
        }
    }

    private fun logoutDialog(){
        commonDialog
            .setTitle(R.string.profile_logout_dialog)
            .setNegativeButton(R.string.word_yes){
                kakaoLogout()
                viewModel.logout()
                commonDialog.dismiss()
            }
            .setPositiveButton(R.string.word_no){
                commonDialog.dismiss()
            }
            .show()
    }

    private fun unRegisterDialog(){
        commonDialog
            .setTitle(R.string.profile_unregister_dialog)
            .setNegativeButton(R.string.word_yes){
                kakaoUnregister()
                viewModel.unregister()
                commonDialog.dismiss()
            }
            .setPositiveButton(R.string.word_no){
                commonDialog.dismiss()
            }
            .show()
    }

    private fun logoutCompleteDialog(){
        commonDialog
            .setTitle(R.string.profile_logout_complete_dialog)
            .setPositiveButton(R.string.word_confirm){
                commonDialog.dismiss()
                startActivity(Intent(requireActivity(), SignActivity::class.java))
                requireActivity().finish()
            }
            .showOnlyPositiveBtn()
            .show()
    }

    private fun unRegisterCompleteDialog(){
        commonDialog
            .setTitle(R.string.profile_unregister_complete_dialog)
            .setPositiveButton(R.string.word_confirm){
                commonDialog.dismiss()
                startActivity(Intent(requireActivity(), SignActivity::class.java))
                requireActivity().finish()
            }
            .showOnlyPositiveBtn()
            .show()
    }

    private fun kakaoLogout(){
        UserApiClient.instance.logout { error ->
            if (error != null) {
                ForeggToast.createToast(requireContext(), R.string.toast_error_kakao_logout, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun kakaoUnregister(){
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                ForeggToast.createToast(requireContext(), R.string.toast_error_kakao_unregister, Toast.LENGTH_SHORT).show()
            }
        }
    }
}