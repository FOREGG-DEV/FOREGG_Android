package com.foregg.presentation.ui.main.injection

import android.content.ActivityNotFoundException
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.foregg.domain.model.enums.GenderType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentInjectionBinding
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.ForeggToast
import com.foregg.presentation.util.PendingExtraValue
import com.foregg.presentation.util.SvgImageUtil
import com.foregg.presentation.util.UserInfo
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InjectionFragment : BaseFragment<FragmentInjectionBinding, InjectionPageState, InjectionViewModel>(
    FragmentInjectionBinding::inflate
) {

    override val viewModel: InjectionViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            val id = requireActivity().intent.getLongExtra(PendingExtraValue.TARGET_ID_KEY, -1)
            val time = requireActivity().intent.getStringExtra(PendingExtraValue.INJECTION_TIME_KEY) ?: ""
            viewModel.initView(id, time)
            if(UserInfo.info.genderType == GenderType.MALE) textMaleTitle.text = getString(R.string.injection_male_title, UserInfo.info.spouse)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.image.collect{
                    loadImage(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as InjectionEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: InjectionEvent){
        when(event){
            InjectionEvent.GoToHomeEvent -> findNavController().popBackStack()
            InjectionEvent.ErrorShareToast -> shareWithKakao()
            InjectionEvent.SuccessShareToast -> ForeggToast.createToast(requireContext(), R.string.toast_success_share_injection, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImage(url : String){
        SvgImageUtil.loadImageFromUrl(binding.imgInjection, url, requireContext(), loadingDrawable = R.drawable.img_injection_loading, errorDrawable = R.drawable.img_injection_loading)
    }

    private fun shareWithKakao(){
        val feed = getFeed()
        if (ShareClient.instance.isKakaoTalkSharingAvailable(requireContext())) {
            ShareClient.instance.shareDefault(requireContext(), feed) { sharingResult, error ->
                if (error != null) {
                    ForeggLog.D("카카오톡 공유 실패")
                }
                else if (sharingResult != null) {
                    startActivity(sharingResult.intent)
                }
            }
        } else {
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(feed)
            try {
                KakaoCustomTabsClient.openWithDefault(requireContext(), sharerUrl)
            } catch(e: UnsupportedOperationException) {
                ForeggLog.D("CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리")
            }
            try {
                KakaoCustomTabsClient.open(requireContext(), sharerUrl)
            } catch (e: ActivityNotFoundException) {
                ForeggLog.D("디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리")
            }
        }
    }

    private fun getFeed() : FeedTemplate{
        return FeedTemplate(
            content = Content(
                title = getString(R.string.kakao_share_title),
                description = getString(R.string.kakao_share_content),
                imageUrl = "https://drive.google.com/uc?export=download&id=1gOKA4dUyoZPayh5re4Gx9gISK0PyJUTj",
                link = Link(
                    mobileWebUrl = getString(R.string.kakao_share_url)
                )
            ),
            buttons = listOf(
                Button(
                    getString(R.string.kakao_share_button),
                    Link(
                        webUrl = getString(R.string.kakao_share_url),
                        mobileWebUrl = getString(R.string.kakao_share_url),
                    )
                )
            )
        )
    }
}