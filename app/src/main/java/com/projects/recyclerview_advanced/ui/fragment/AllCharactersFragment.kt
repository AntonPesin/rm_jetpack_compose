package com.projects.recyclerview_advanced.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.projects.recyclerview_advanced.databinding.AllCharactersBinding
import com.projects.recyclerview_advanced.ui.adapter.CharactersAdapter
import com.projects.recyclerview_advanced.ui.viewmodel.MyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AllCharactersFragment : Fragment() {

    private lateinit var binding: AllCharactersBinding
    private val viewModel: MyViewModel by viewModels()
    private lateinit var charactersAdapter: CharactersAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = AllCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersAdapter = CharactersAdapter()
        binding.recyclerView.adapter = charactersAdapter

        lifecycleScope.launch {
            start()
        }

        binding.button.setOnClickListener {
            charactersAdapter.refresh()
        }

    }

    private suspend fun start() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagedCharacters.collect {
                charactersAdapter.submitData(it)
            }
        }

        charactersAdapter.addLoadStateListener { loadState ->
            if (loadState.append is LoadState.Loading) {
                lifecycleScope.launch {
                    delay(2000)
                    binding.progressBar.visibility = View.VISIBLE
                }
            } else if (loadState.refresh is LoadState.Error) {
                binding.progressBar.visibility = View.INVISIBLE
                binding.button.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

    }
}

