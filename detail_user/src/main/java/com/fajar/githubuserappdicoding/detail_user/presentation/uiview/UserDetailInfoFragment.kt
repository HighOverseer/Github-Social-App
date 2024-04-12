package com.fajar.githubuserappdicoding.detail_user.presentation.uiview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.githubuserappdicoding.core.di.util.GenericSavedStateViewModelFactory
import com.fajar.githubuserappdicoding.core.domain.model.UserDetailInfo
import com.fajar.githubuserappdicoding.core.presentation.UIEvent
import com.fajar.githubuserappdicoding.core.presentation.collectChannelFlowOnLifecycleStarted
import com.fajar.githubuserappdicoding.core.presentation.collectLatestOnLifeCycleStarted
import com.fajar.githubuserappdicoding.core.presentation.showToast
import com.fajar.githubuserappdicoding.detail_user.databinding.FragmentUserDetailInfoBinding
import com.fajar.githubuserappdicoding.detail_user.di.initDI
import com.fajar.githubuserappdicoding.detail_user.presentation.adapter.UserDetailInfoAdapter
import com.fajar.githubuserappdicoding.detail_user.presentation.uistate.UserDetailInfoUiState
import com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel.UserDetailInfoVM
import com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel.UserDetailInfoViewModelFactory
import javax.inject.Inject


class UserDetailInfoFragment : Fragment() {
    private var binding: FragmentUserDetailInfoBinding? = null

    @Inject
    lateinit var factory: UserDetailInfoViewModelFactory

    private val vm: UserDetailInfoVM by viewModels {
        GenericSavedStateViewModelFactory(factory, this, arguments)
    }

    companion object {
        const val EXTRA_POSITION = "position"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDI(requireActivity()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.collectLatestOnLifeCycleStarted(vm.uiState) {
            setLayout(it)
        }
        viewLifecycleOwner.collectChannelFlowOnLifecycleStarted(vm.uiEvent) {
            if (it is UIEvent.ToastMessageEvent) showToast(requireActivity(), it.message)
        }
    }

    private fun setLayout(uiState: UserDetailInfoUiState) {
        binding?.apply {
            uiState.apply {
                setUpRv(listItems)
                progressBar.isVisible = isLoading
                tvInfo.isVisible = listItems.isEmpty() && !isLoading
            }
        }
    }

    private fun setUpRv(items: List<UserDetailInfo>) {
        val adapter = UserDetailInfoAdapter(items)
        binding?.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun onDestroyView() {
        binding?.recyclerView?.adapter = null
        super.onDestroyView()
        binding = null
    }

}