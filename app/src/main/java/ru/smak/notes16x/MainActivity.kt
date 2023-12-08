package ru.smak.notes16x

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import ru.smak.notes16x.ui.NoteEditor
import ru.smak.notes16x.ui.NotesList
import ru.smak.notes16x.ui.ViewMode
import ru.smak.notes16x.ui.theme.Notes16xTheme
import ru.smak.notes16x.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

    private val mvm: MainViewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Notes16xTheme {
                MainUI(
                    modifier = Modifier.fillMaxSize(),
                    title = stringResource(id = mvm.titleId),
                    viewMode = mvm.viewMode,
                ){
                    if (mvm.viewMode == ViewMode.LIST){
                        NotesList(
                            listOf()
                        )
                    } else {
                        NoteEditor(
                            NoteData()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    modifier: Modifier = Modifier,
    title: String = "",
    viewMode: ViewMode = ViewMode.LIST,
    content: @Composable ()->Unit
){
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title
                    )
                },
                navigationIcon = { if (viewMode == ViewMode.NOTE) {
                    FilledTonalIconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.twotone_arrow_back_ios_24),
                            contentDescription = null
                        )
                    }
                } },
                actions = {if (viewMode == ViewMode.NOTE){
                    FilledTonalIconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.twotone_check_24),
                            contentDescription = null
                        )
                    }
                } },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        },
        bottomBar = {

        },
        snackbarHost = {

        },
        floatingActionButton = {
            if (viewMode == ViewMode.LIST) {
                OutlinedIconButton(
                    modifier = Modifier.defaultMinSize(48.dp, 48.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.twotone_playlist_add_circle_48),
                        contentDescription = null,
                        tint = colorResource(id = R.color.add)
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ) {
        Column(Modifier.padding(it)) {
            content()
        }
    }
}

@Preview
@Composable
fun MainUIPreview(){
    Notes16xTheme {
        MainUI(
            Modifier.fillMaxSize(),
            title = "Заголовок активности",
            viewMode = ViewMode.NOTE,
        ){

        }
    }
}