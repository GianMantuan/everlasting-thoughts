package com.pos.everlastingthoughts.ui.addthought


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pos.everlastingthoughts.database.Thought
import com.pos.everlastingthoughts.database.ThoughtDAO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat

@HiltViewModel
class AddThoughtDialogViewModel @Inject constructor(
    private val thoughtDao: ThoughtDAO,
    private val state: SavedStateHandle
): ViewModel() {
    val thoughtItem = state.get<Thought>("thought")

    var thoughtText = state.get<String>("thoughtText") ?: thoughtItem?.thought ?: ""
        set(value) {
            field = value
            state.set("thoughtText", value)
        }

    private val addThoughtEventChannel = Channel<AddThoughtEvent>()
    val addThoughtEvent = addThoughtEventChannel.receiveAsFlow()

    fun onSaveClick() {
        Log.d("DIALOG", thoughtText)

        if (thoughtText.isBlank()) {
            showInvalidInputMessage("You can\'t say that you are thinking in nothing...")
            return
        } else {
            val simpleDateFormat = SimpleDateFormat("d MMMM yyyy. EEEE HH:mm:ss", Locale.getDefault())

            val newThought = Thought(
                uid = UUID.randomUUID().toString(),
                thought = thoughtText,
                date = simpleDateFormat.format(Date()).toString()
            )
            createThought(newThought)
        }
    }

    private fun createThought(thought: Thought) = viewModelScope.launch {
        thoughtDao.insert(thought)
        addThoughtEventChannel.send(AddThoughtEvent.ShowSuccessMessage("Yes! Keep thinking my child..."))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addThoughtEventChannel.send(AddThoughtEvent.ShowInvalidInputMessage(text))
    }

    sealed class AddThoughtEvent {
        data class ShowInvalidInputMessage(val msg: String): AddThoughtEvent()
        data class ShowSuccessMessage(val msg: String): AddThoughtEvent()
    }
}