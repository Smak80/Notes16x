package ru.smak.notes16x.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.smak.notes16x.R
import ru.smak.notes16x.ui.ViewMode

class MainViewModel : ViewModel() {

    var viewMode by mutableStateOf(ViewMode.LIST)

    val titleId: Int
        get() = when(viewMode){
            ViewMode.LIST -> R.string.app_name
            ViewMode.NOTE -> R.string.edit_note
        }
}