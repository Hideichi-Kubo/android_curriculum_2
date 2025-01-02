package com.example.monotodo.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monotodo.MonoTodoTopAppBar
import com.example.monotodo.R
import com.example.monotodo.data.Task
import com.example.monotodo.ui.navigation.NavigationDestination
import com.example.monotodo.ui.theme.MonoTodoTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToTaskEntry: () -> Unit,
    navigateToTaskCompleted: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MonoTodoTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                navigateToTaskCompleted = navigateToTaskCompleted
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTaskEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.task_entry_title)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            taskList = listOf(),
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
    }
}

@Composable
fun HomeBody(
    taskList: List<Task>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (taskList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_task_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding)
            )
        } else {
            TaskList(
                taskList = taskList,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small)),
                contentPadding = contentPadding
            )
        }
    }
}

@Composable
fun TaskList(
    taskList: List<Task>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = taskList, key = { it.id }) { item ->
            TaskCard(
                task = item,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        border = BorderStroke(2.dp, Color.Black),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = dimensionResource(id = R.dimen.padding_small))
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.delete)
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                )
            }
            Switch(
                checked = task.isCompleted,
                onCheckedChange = { },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    MonoTodoTheme {
        HomeBody(listOf(
            Task(0, "Buy Eggs"),
            Task(1, "Buy Apples"),
            Task(2, "Buy Eggs")
        ))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    MonoTodoTheme {
        HomeBody(listOf())
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    MonoTodoTheme {
        TaskCard(
            Task(0, "Buy Eggs")
        )
    }
}