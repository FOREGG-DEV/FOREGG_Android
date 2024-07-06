package com.foregg.presentation.ui.main.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.enums.HomeAdCardType
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentHomeBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.main.home.adapter.HomeChallengeAdapter
import com.foregg.presentation.ui.main.home.adapter.HomeIntroductionAdapter
import com.foregg.presentation.ui.main.home.adapter.HomeTodayScheduleAdapter
import com.foregg.presentation.util.ForeggToast
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var dialog: CommonDialog

    private val homeIntroductionAdapter : HomeIntroductionAdapter by lazy {
        HomeIntroductionAdapter(object : HomeIntroductionAdapter.HomeIntroductionDelegate {
            override fun onClickCard(type: HomeAdCardType) {
                inspectClickCard(type)
            }
        })
    }

    private val todayScheduleAdapter : HomeTodayScheduleAdapter by lazy {
        HomeTodayScheduleAdapter(object : HomeTodayScheduleAdapter.HomeTodayScheduleDelegate {
            override fun onClickRecordTreatment(id: Long, recordType: RecordType) {
                goToCreateEditSchedule(id, recordType)
            }

            override fun calculateNearestSchedulePosition(list: List<HomeRecordResponseVo>): Int {
                return viewModel.calculatePosition(list)
            }
        })
    }

    private val homeChallengeAdapter: HomeChallengeAdapter by lazy {
        HomeChallengeAdapter( object : HomeChallengeAdapter.HomeChallengeDelegate {
            override fun showDialog(id: Long, successDaysCount : Int) {
                showCompleteDialog(id, successDaysCount)
            }

            override fun deleteComplete(id: Long) {
                viewModel.deleteCompleteChallenge(id)
            }
        })
    }

    private var position = 0

    override fun initView() {
        binding.apply {
            vm = viewModel
            todayScheduleViewPager.apply {
                adapter = todayScheduleAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                    override fun onLayoutChange(
                        v: View?,
                        left: Int,
                        top: Int,
                        right: Int,
                        bottom: Int,
                        oldLeft: Int,
                        oldTop: Int,
                        oldRight: Int,
                        oldBottom: Int
                    ) {
                        updatePosition()
                        todayScheduleViewPager.removeOnLayoutChangeListener(this)
                    }
                })
            }
            challengeRecyclerView.apply {
                adapter = homeChallengeAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            advertiseViewPager.apply {
                adapter = homeIntroductionAdapter
            }
            indicatorView.attachTo(advertiseViewPager)
            viewModel.initScheduleStates()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.todayScheduleList.collect {
                    if (it.isNotEmpty()) todayScheduleAdapter.submitList(it as MutableList<HomeRecordResponseVo>?)
                    updatePosition()
                }
            }
            launch {
                viewModel.uiState.challengeList.collect {
                    homeChallengeAdapter.submitList(it)
                }
            }
            launch {
                viewModel.uiState.homeIntroductionItemList.collect {
                    homeIntroductionAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as HomeEvent)
                }
            }
        }
    }

    private fun sortEvent(event: HomeEvent) {
        when(event) {
            HomeEvent.GoToChallengeEvent -> goToChallenge()
            HomeEvent.GoToDailyRecordEvent -> goToDailyRecord()
            HomeEvent.GoToCalendarEvent -> goToCalendar()
            HomeEvent.GoToCreateEditScheduleEvent -> goToCreateEditSchedule(viewModel.uiState.medicalRecordId.value, RecordType.HOSPITAL)
            is HomeEvent.ShowWeekEndDialog -> showCompleteChallengeDialog(event.isSuccess)
        }
    }

    private fun goToChallenge() {
        val action = HomeFragmentDirections.actionHomeToChallege()
        findNavController().navigate(action)
    }

    private fun goToDailyRecord() {
        val action = HomeFragmentDirections.actionHomeToDailyRecord()
        findNavController().navigate(action)
    }

    private fun showCompleteDialog(id: Long, successDaysCount : Int) {
        dialog
            .setTitle(R.string.home_challenge_complete_dialog_text)
            .setPositiveButton(R.string.word_yes) {
                viewModel.completeChallenge(id, successDaysCount)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.word_no) {
                dialog.dismiss()
            }
            .show()
    }

    private fun showCompleteChallengeDialog(isSuccess : Boolean){
        val content = if(isSuccess) R.string.challenge_success_dialog else R.string.challenge_fail_dialog
        dialog
            .showOnlyPositiveBtn()
            .setTitle(content)
            .setPositiveButton(R.string.challenge_dialog_positive_btn) {
                dialog.dismiss()
            }
            .show()
    }

    private fun goToCreateEditSchedule(id: Long, recordType: RecordType) {
        val action = HomeFragmentDirections.actionHomeToCreateEditSchedule(id = id, type = CalendarType.EDIT, scheduleType = recordType, isHome = true)
        findNavController().navigate(action)
    }

    private fun goToCalendar() {
        val action = HomeFragmentDirections.actionHomeToCalendar()
        findNavController().navigate(action)
    }

    private fun updatePosition() {
        position = viewModel.calculatePosition(viewModel.uiState.todayScheduleList.value)
        if (position == -1) position = viewModel.uiState.todayScheduleList.value.size - 1
        (binding.todayScheduleViewPager.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
    }

    private fun inspectClickCard(type : HomeAdCardType){
        when(type){
            HomeAdCardType.DAILY -> goToDailyRecord()
            HomeAdCardType.SHARE -> shareWithKakao()
            HomeAdCardType.BLOG -> goToBlog()
        }
    }

    private fun shareWithKakao(){
        val feed = getFeed()
        if (ShareClient.instance.isKakaoTalkSharingAvailable(requireContext())) {
            ShareClient.instance.shareDefault(requireContext(), feed) { sharingResult, error ->
                if (error != null) {
                    ForeggToast.createToast(requireContext(), R.string.toast_error_kakao_share_fail, Toast.LENGTH_SHORT).show()
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
                ForeggToast.createToast(requireContext(), R.string.toast_error_kakao_share_web_fail, Toast.LENGTH_SHORT).show()
            }
            try {
                KakaoCustomTabsClient.open(requireContext(), sharerUrl)
            } catch (e: ActivityNotFoundException) {
                ForeggToast.createToast(requireContext(), R.string.toast_error_kakao_share_web_fail, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getFeed() : FeedTemplate {
        return FeedTemplate(
            content = Content(
                title = getString(R.string.kakao_share_title),
                description = getString(R.string.kakao_share_content),
                imageUrl = getString(R.string.kakao_share_image),
                link = Link(
                    webUrl = getString(R.string.kakao_share_url),
                    mobileWebUrl = getString(R.string.kakao_share_url),
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

    private fun goToBlog(){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(getString(R.string.foregg_blog))
        }
        startActivity(intent)
    }
}