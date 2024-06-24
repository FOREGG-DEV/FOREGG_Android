package com.foregg.presentation.ui.main.home.challenge

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.foregg.domain.model.enums.ChallengeTapType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentChallengeBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.main.home.challenge.adapter.ChallengeListAdapter
import com.foregg.presentation.ui.main.home.challenge.adapter.MyChallengeListAdapter
import com.foregg.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class ChallengeFragment : BaseFragment<FragmentChallengeBinding, ChallengePageState, ChallengeViewModel> (
    FragmentChallengeBinding::inflate
) {
    override val viewModel: ChallengeViewModel by viewModels()

    @Inject
    lateinit var dialog: CommonDialog

    private val challengeListAdapter = ChallengeListAdapter()
    private val myChallengeListAdapter : MyChallengeListAdapter by lazy {
        MyChallengeListAdapter(object : MyChallengeListAdapter.DeleteMyChallengeDelegate {
            override fun showDialog(id: Long) {
                showDeleteDialog(id)
            }
        })
    }

    override fun initView() {
        bindTab()
        viewModel.getAllChallenge()

        binding.apply {
            vm = viewModel
            viewPagerChallenge.apply {
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                val pageMarginPx = 10.px
                val offsetPx = 6.px

                setPageTransformer { page, position ->
                    val viewPager = page.parent.parent as ViewPager2
                    val offset = position * -(2 * offsetPx + pageMarginPx)
                    if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                        if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                            page.translationX = -offset
                        } else {
                            page.translationX = offset
                        }
                    } else {
                        page.translationY = offset
                    }

                    val scale = 1 - abs(position)
                    page.scaleY = 0.85f + scale * 0.15f
                }

                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        outRect.right = 15.px
                        outRect.left = 15.px
                    }
                })

                adapter = challengeListAdapter
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    private var previousPosition = viewPagerChallenge.currentItem
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        viewModel.swipeItem(position, previousPosition)
                        previousPosition = position
                    }
                })

            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.challengeList.collect {
                    challengeListAdapter.submitList(it)
                }
            }

            launch {
                viewModel.uiState.myChallengeList.collect {
                    myChallengeListAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as ChallengeEvent)
                }
            }
        }
    }

    private fun sortEvent(event: ChallengeEvent) {
        when(event) {
            ChallengeEvent.OnClickBtnBack -> findNavController().popBackStack()
            ChallengeEvent.OnClickBtnComplete -> showCompleteDialog()
            is ChallengeEvent.ShowWeekEndDialog -> showCompleteChallengeDialog(event.isSuccess)
        }
    }

    private fun bindTab(){
        binding.customTabBar.apply {
            leftBtnClicked {
                viewModel.updateTabType(ChallengeTapType.ALL)
                viewModel.getAllChallenge()
                binding.viewPagerChallenge.adapter = challengeListAdapter
            }
            rightBtnClicked {
                viewModel.updateTabType(ChallengeTapType.MY)
                viewModel.getMyChallenge()
                binding.viewPagerChallenge.adapter = myChallengeListAdapter
            }
        }
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

    private fun showDeleteDialog(id: Long) {
        dialog
            .setTitle(R.string.challenge_stop)
            .setPositiveButton(R.string.word_yes) {
                viewModel.quitChallenge(id)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.word_no) {
                dialog.dismiss()
            }
            .show()
    }

    private fun showCompleteDialog() {
        dialog
            .setTitle(R.string.home_challenge_complete_dialog_text)
            .setPositiveButton(R.string.word_yes) {
                viewModel.completeChallenge()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.word_no) {
                dialog.dismiss()
            }
            .show()
    }
}