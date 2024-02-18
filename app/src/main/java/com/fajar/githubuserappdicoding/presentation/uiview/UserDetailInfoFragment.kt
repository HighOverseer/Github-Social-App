package com.fajar.githubuserappdicoding.presentation.uiview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.githubuserappdicoding.databinding.FragmentUserDetailInfoBinding
import com.fajar.githubuserappdicoding.domain.model.UserDetailInfo
import com.fajar.githubuserappdicoding.presentation.adapter.UserDetailInfoAdapter
import com.fajar.githubuserappdicoding.presentation.uistate.UserDetailInfoUiState
import com.fajar.githubuserappdicoding.presentation.util.UIEvent
import com.fajar.githubuserappdicoding.presentation.util.collectChannelFlowOnLifecycleStarted
import com.fajar.githubuserappdicoding.presentation.util.collectLatestOnLifeCycleStarted
import com.fajar.githubuserappdicoding.presentation.util.showToast
import com.fajar.githubuserappdicoding.presentation.viewmodel.UserDetailInfoVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailInfoFragment : Fragment() {
    private var _binding: FragmentUserDetailInfoBinding? = null
    private val binding get() = _binding!!
    private val vm: UserDetailInfoVM by viewModels()

    companion object {
        const val EXTRA_POSITION = "position"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //vm = obtainVM(arguments)

        viewLifecycleOwner.collectLatestOnLifeCycleStarted(vm.uiState) {
            setLayout(it)
        }
        viewLifecycleOwner.collectChannelFlowOnLifecycleStarted(vm.uiEvent) {
            if (it is UIEvent.ToastMessageEvent) showToast(requireActivity(), it.message)
        }
    }

    private fun setLayout(uiState: UserDetailInfoUiState) {
        binding.apply {
            uiState.apply {
                setUpRv(listItems)
                progressBar.isVisible = isLoading
                tvInfo.isVisible = listItems.isEmpty() && !isLoading
            }
        }
    }

    private fun setUpRv(items: List<UserDetailInfo>) {
        val adapter = UserDetailInfoAdapter(items)
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        }
    }


    /*    private fun obtainVM(arguments: Bundle?): UserDetailInfoVM {
            val factory = ViewModelFactory.getInstance(
                requireActivity().applicationContext,
                arguments
            )
            return ViewModelProvider(this, factory)[UserDetailInfoVM::class.java]
        }*/


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}