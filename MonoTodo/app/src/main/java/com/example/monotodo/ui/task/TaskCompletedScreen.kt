package com.example.monotodo.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monotodo.MonoTodoTopAppBar
import com.example.monotodo.R
import com.example.monotodo.data.Task
import com.example.monotodo.ui.AppViewModelProvider
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
    canNavigateBack: Boolean = true,
    viewModel: TaskCompletedViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val taskCompletedUiState = viewModel.taskCompletedUiState.collectAsState()
    val taskCompletedMeigenUiState = viewModel.taskCompletedMeigenUiState.collectAsState()

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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TaskCompletedMeigenSection(
                meigenUiState = taskCompletedMeigenUiState.value,
                modifier = modifier
            )
            TaskCompletedBody(
                taskList = taskCompletedUiState.value.taskList,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(0.dp),
                onDelete = { task ->
                    viewModel.deleteTask(task)
                },
                onToggleCompletion = { task, isCompleted ->
                    viewModel.toggleTaskCompletion(task, isCompleted)
                }
            )
        }
    }
}

@Composable
fun TaskCompletedMeigenSection(
    meigenUiState: TaskCompletedMeigenUiState,
    modifier: Modifier = Modifier
) {
    when (meigenUiState) {
        TaskCompletedMeigenUiState.Loading -> {
            Text(
                text = stringResource(R.string.loading),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_extra_large))
            )
        }
        TaskCompletedMeigenUiState.Error -> {
            Text(
                text = stringResource(R.string.failed_to_retrieve_quote),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_extra_large))
            )
        }
        is TaskCompletedMeigenUiState.Success -> {
            val meigen = meigenUiState.meigen
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = meigen.meigen,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
                Text(
                    text = meigen.auther,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .align(Alignment.End)
                )
            }
        }
    }
}

@Composable
fun TaskCompletedBody(
    taskList: List<Task>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onDelete: (Task) -> Unit,
    onToggleCompletion: (Task, Boolean) -> Unit
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.padding_extra_large))
            )
        } else {
            TaskList(
                taskList = taskList,
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small)),
                onDelete = onDelete,
                onToggleCompletion = onToggleCompletion
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCompletedBodyPreview() {
    MonoTodoTheme {
        TaskCompletedBody(
            taskList = listOf(
                Task(0, "Buy Eggs"),
                Task(1, "Buy Apples"),
                Task(2, "Buy Eggs")
            ),
            onDelete = {},
            onToggleCompletion = { _, _ -> }
        )
    }
}