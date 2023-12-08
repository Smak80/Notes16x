package ru.smak.notes16x

import java.time.LocalDateTime

data class Note(
    var id: Int = 0,
    var title: String = "",
    var text: String = "",
    var priority: Int = 0,
    var time: LocalDateTime = LocalDateTime.now()
)