package com.pos.everlastingthoughts.ui.thought

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pos.everlastingthoughts.database.ThoughtDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThoughtViewModel @Inject constructor(
    private val thoughtDao: ThoughtDAO
): ViewModel() {
    val thoughts = thoughtDao.getThoughts().asLiveData()
}