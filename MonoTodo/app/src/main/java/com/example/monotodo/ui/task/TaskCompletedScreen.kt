package com.example.monotodo.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monotodo.MonoTodoTopAppBar
import com.example.monotodo.R
import com.example.monotodo.data.Task
import com.example.monotodo.ui.home.TaskList
import com.example.monotodo.ui.navigation.NavigationDestination
import com.example.monotodo.ui.theme.MonoTodoTheme

object TaskCompletedDestination : NavigationDestination {
    override val route = "task_completed"
    override val titleRes = R.string.task_completed_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCompletedScreen(
    modifier: Modifier = Modifier,
    navigateToTaskEntry: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MonoTodoTopAppBar(
                title = stringResource(TaskCompletedDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
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
        TaskCompletedBody(
            taskList = listOf(),
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
    }
}

@Composable
fun TaskCompletedBody(
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
                text = stringResource(R.string.no_completed_task_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding)
            )
        } else {
            TaskList(
                taskList = taskList,
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCompletedScreenPreview() {
    MonoTodoTheme {
        TaskCompletedScreen(
            navigateToTaskEntry = {},
            onNavigateUp = {}
        )
    }
}