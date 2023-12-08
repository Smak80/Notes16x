package ru.smak.notes16x

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDateTime

class NoteData {
    var id: Int by mutableStateOf(0)
    var title: String by mutableStateOf("")
    var text: String by mutableStateOf("")
    var priority: Int by mutableStateOf(0)
    var time: LocalDateTime by mutableStateOf(LocalDateTime.now())

    fun fillFromNote(note: Note){
        id = note.id
        title = note.title
        text = note.text
        priority = note.priority
        time = note.time
    }
}