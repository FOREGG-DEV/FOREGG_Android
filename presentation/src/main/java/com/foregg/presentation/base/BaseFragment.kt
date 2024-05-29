package com.foregg.presentation.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.foregg.data.base.StatusCode
import com.foregg.presentation.PageState
import com.foregg.presentation.R
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.common.LoadingDialog
import com.foregg.presentation.util.ForeggToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, STATE: PageState, VM: BaseViewModel<STATE>>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
) : Fragment() {

    @Inject
    lateinit var commonErrorDialog: CommonDialog

    private var backPressedOnce = false
    private lateinit var navController: NavController
    protected abstract val viewModel: VM

    private var _binding: B? = null
    protected val binding
        get() = _binding!!

    private var loadingView: LoadingDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        loadingView = LoadingDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflater(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        navController = findNavController()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
        initView()
        initStates()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (navController.currentDestination?.id != navController.graph.startDestinationId) {
                navController.popBackStack()
                return
            }
            if(backPressedOnce){
                requireActivity().finish()
                return
            }
            backPressedOnce = true
            ForeggToast.createToast(requireContext(), "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({ backPressedOnce = false }, 2000)
        }
    }

    protected abstract fun initView()

    protected open fun initStates() {
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                    if (isLoading) {
                        loadingView?.show()
                    } else {
                        loadingView?.dismiss()
                    }
                }
            }
            launch {
                viewModel.commonError.observe(viewLifecycleOwner) { error ->
                    showCommonDialog(error)
                }
            }
        }
    }

    protected fun LifecycleOwner.repeatOnStarted(viewLifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showCommonDialog(error : String){
        val title = when(error){
            StatusCode.ERROR_404 -> "시스템 오류가 발생했어요"
            StatusCode.NETWORK_ERROR -> "서버에 오류가 발생했습니다."
            else -> {"알 수 없는 오류가 발생했습니다."}
        }
        val btnText =when(error){
            StatusCode.ERROR_404 -> "새로고침"
            else -> getString(R.string.word_confirm)
        }
        commonErrorDialog
            .setTitle(title)
            .showOnlyPositiveBtn()
            .setPositiveButton(btnText){
                if(error == StatusCode.ERROR_404) initView()
                commonErrorDialog.dismiss()
            }
            .show()
    }
}
