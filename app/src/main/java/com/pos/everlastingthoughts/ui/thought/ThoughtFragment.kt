package com.pos.everlastingthoughts.ui.thought

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pos.everlastingthoughts.R
import com.pos.everlastingthoughts.databinding.ContentMainBinding
import com.pos.everlastingthoughts.ui.MainActivity
import com.pos.everlastingthoughts.ui.addthought.ThoughtDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThoughtFragment: Fragment(R.layout.content_main) {
    private val viewModel: ThoughtViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ContentMainBinding.bind(view)
        val thoughtAdapter = ThoughtAdapter()

        binding.apply {
            recyclerViewThoughts.apply {
                adapter = thoughtAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }


            extendedFab.setOnClickListener {
                ThoughtDialogFragment().show(parentFragmentManager, "Hmmm...")
            }
        }

        viewModel.thoughts.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) binding.noThought.visibility = View.INVISIBLE
            thoughtAdapter.submitList(it)
        }
    }
}