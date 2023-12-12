package ru.smak.notes16x.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import ru.smak.notes16x.Note
import ru.smak.notes16x.NoteData
import ru.smak.notes16x.R
import ru.smak.notes16x.database.NotesDao
import ru.smak.notes16x.ui.ViewMode

class MainViewModel(app: Application) : AndroidViewModel(app) {

    var viewMode by mutableStateOf(ViewMode.LIST)

    val dao: NotesDao by lazy {
        NotesDao(getApplication<Application>().applicationContext)
    }

    var currentNote: Note? = null
    var noteData: NoteData = NoteData()

    val titleId: Int
        get() = when(viewMode){
            ViewMode.LIST -> R.string.app_name
            ViewMode.NOTE -> R.string.edit_note
        }

    fun toNoteView(note: Note) {
        currentNote = note
        noteData.fillFromNote(currentNote ?: Note())
        viewMode = ViewMode.NOTE
    }

    fun toListView() {
        viewMode = ViewMode.LIST
    }

    fun addNewNote() {
        currentNote = noteData.toNote().also{
            dao.addNote(it)
        }
    }
}