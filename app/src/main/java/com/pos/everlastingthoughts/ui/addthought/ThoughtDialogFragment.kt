package com.pos.everlastingthoughts.ui.addthought

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.pos.everlastingthoughts.R
import com.pos.everlastingthoughts.databinding.NewThoughtDialogBinding
import com.pos.everlastingthoughts.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ThoughtDialogFragment: DialogFragment(R.layout.new_thought_dialog) {
    private val viewModel: AddThoughtDialogViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val binding = NewThoughtDialogBinding.bind(view)

        binding.apply {
            editNewThought.setText(viewModel.thoughtText)

            editNewThought.addTextChangedListener {
                viewModel.thoughtText = it.toString()
            }

            btnThought.setOnClickListener {
                viewModel.onSaveClick()
                if(viewModel.thoughtText.isNotBlank()) dismiss()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addThoughtEvent.collect { event ->
                when (event) {
                    is AddThoughtDialogViewModel.AddThoughtEvent.ShowSuccessMessage -> {
                       Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is AddThoughtDialogViewModel.AddThoughtEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                    }
                }.exhaustive
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
    }
}