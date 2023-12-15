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
import java.time.LocalDateTime

class MainViewModel(app: Application) : AndroidViewModel(app) {

    var viewMode by mutableStateOf(ViewMode.LIST)

    val dao: NotesDao by lazy {
        NotesDao(getApplication<Application>().applicationContext)
    }

    private var currentNote: Note? = null
    var noteData: NoteData = NoteData()
    var showRemoveRequest by mutableStateOf(false)

    val titleId: Int
        get() = when(viewMode){
            ViewMode.LIST -> R.string.app_name
            ViewMode.NOTE -> R.string.edit_note
        }

    val notes: List<Note>
        get() = dao.getAllNotes()

    fun toNoteView(note: Note) {
        currentNote = note
        noteData.fillFromNote(currentNote ?: Note())
        viewMode = ViewMode.NOTE
    }

    fun toListView() {
        viewMode = ViewMode.LIST
    }

    fun saveNote() {
        currentNote = noteData.toNote().also{
            it.time = LocalDateTime.now()
            if (it.id == 0)
                dao.addNote(it)
            else
                dao.editNote(it)
        }
        toListView()
    }

    fun removeNoteRequest(note: Note){
        currentNote = note
        showRemoveRequest = true
    }

    fun removeCurrentNote() = currentNote?.let{
        showRemoveRequest = false
        dao.deleteNote(it)
    }

}