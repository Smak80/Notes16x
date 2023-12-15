package ru.smak.notes16x.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.smak.notes16x.Note
import ru.smak.notes16x.R
import ru.smak.notes16x.ui.theme.Notes16xTheme
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesList(
    notes: List<Note>,
    modifier: Modifier = Modifier,
    onEditNoteRequest: (Note)->Unit = {},
    onRemoveNoteRequest: (Note)->Unit = {},
){
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(180.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalItemSpacing = 6.dp,
        content = {
            items(notes) {
                NoteCard(
                    it,
                    onEditClick = onEditNoteRequest,
                    onRemoveClick = onRemoveNoteRequest,
                )
            }
        },
        modifier = modifier,
    )

}

@Preview
@Composable
fun NotesListPreview(){
    Notes16xTheme {
        NotesList(
            listOf(
                Note(id = 1, title="Заметка 1", text = "Текст заметки 1"),
                Note(id = 2, title="Заметка 2", text = "Текст заметки 2 это очень очень очень очень очень очень очень очень очень длииииииииииииииинный текст"),
                Note(id = 3, title="Заметка 3", priority = -1),
                Note(id = 4, title="Заметка 4", text = "Текст заметки 4"),
                Note(id = 5, text = "Текст заметки 5", priority = 1),
            ),
            Modifier
                .fillMaxSize()
                .padding(6.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier = Modifier,
    onEditClick: (Note)->Unit = {},
    onRemoveClick: (Note)->Unit = {},
){
    ElevatedCard(onClick = {
        onEditClick(note)
    }){
        val time = note.time.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
        val priorityColor = when{
            note.priority < 0 -> colorResource(id = R.color.low_priority)
            note.priority > 0 -> colorResource(id = R.color.high_priority)
            else -> colorResource(id = R.color.standart_priority)
        }
        Surface(modifier = modifier) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (note.title.isNotBlank()) {
                    Text(
                        text = note.title,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                if (note.title.isNotBlank() && note.text.isNotBlank()) {
                    Divider()
                }
                if (note.text.isNotBlank()) {
                    Text(
                        text = note.text,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_circle_24),
                        contentDescription = null,
                        tint = priorityColor,
                    )
                    Text(
                        text = time,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    IconButton(
                        onClick = { onRemoveClick(note) },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.twotone_delete_forever_24),
                            contentDescription = null,
                            tint = colorResource(id = R.color.remove),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NoteCardPreview(){
    NoteCard(
        Note(id = 2, title="Заметка 2", text = "Текст заметки 2 это очень очень очень очень очень очень очень очень очень длииииииииииииииинный текст"),
    )
}

@Composable
fun DeleteRequest(
    modifier: Modifier = Modifier,
    showRequest: Boolean = false,
    onDismiss: ()->Unit = {},
    onConfirm: ()->Unit = {},
){
    if (showRequest) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text(text = stringResource(id = R.string.no))
                }
            },
            icon = {
                Icon(
                    painterResource(id = R.drawable.twotone_delete_forever_24),
                    contentDescription = null,
                    tint = colorResource(id = R.color.remove)
                )
            },
            title = { Text(stringResource(id = R.string.remove_dlg_title)) },
            text = { Text(stringResource(id = R.string.remove_dlg_text)) },
        )
    }
}

@Preview
@Composable
fun DeleteRequestPreview(){
    DeleteRequest(showRequest = true)
}