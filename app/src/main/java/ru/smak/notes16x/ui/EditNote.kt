package ru.smak.notes16x.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.smak.notes16x.NoteData
import ru.smak.notes16x.R
import ru.smak.notes16x.ui.theme.Notes16xTheme
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditor(
    note: NoteData,
    modifier: Modifier = Modifier,
){
    val time = note.time.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
    val priorityColor = when{
        note.priority < 0 -> colorResource(id = R.color.low_priority)
        note.priority > 0 -> colorResource(id = R.color.high_priority)
        else -> colorResource(id = R.color.standart_priority)
    }
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = note.title,
                onValueChange = {note.title = it},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.e_title)) },
                singleLine = true,
            )
            OutlinedTextField(
                value = note.text,
                onValueChange = {note.text = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                label = { Text(stringResource(R.string.e_text)) },
                singleLine = false,
            )
            Slider(
                value = note.priority.toFloat(),
                onValueChange = { note.priority = it.toInt() },
                valueRange = -1f..1f,
                steps = 1,
                colors = SliderDefaults.colors(
                    thumbColor = priorityColor,
                    activeTrackColor = priorityColor,
                )
            )
            OutlinedTextField(
                value = time,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.e_time)) },
                singleLine = false,
                readOnly = true,
            )
        }
    }
}

@Preview
@Composable
fun NoteEditorPreview(){
    Notes16xTheme {
        NoteEditor(
            NoteData(),
            Modifier.fillMaxSize()
        )
    }
}