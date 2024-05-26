package com.foregg.presentation.ui.main.home.challenge

import android.graphics.Rect
import android.view.View
import androidx.core.view.children
import androidx.core.view.marginStart
import androidx.core.view.setPadding
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
        binding.apply {
            vm = viewModel
            viewPagerChallenge.apply {
                adapter = challengeListAdapter
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    private var previousPosition = viewPagerChallenge.currentItem
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        viewModel.swipeItem(position, previousPosition)
                        previousPosition = position
                    }
                })
                setPadding(10, 0, 10, 0)
                clipToPadding = false
                offscreenPageLimit = 2
            }
        }
        bindTab()
        viewModel.setView()
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
            ChallengeEvent.OnClickBtnComplete -> TODO()
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
}