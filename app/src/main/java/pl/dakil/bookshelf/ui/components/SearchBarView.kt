package pl.dakil.bookshelf.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import pl.dakil.bookshelf.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarView(
    searchValue: String,
    onSearchInput: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchField(
            searchValue = searchValue,
            onSearchInput = onSearchInput,
            onSearch = onSearch,
            placeholder = placeholder,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier
                .padding(top = SearchBarDefaults.InputFieldHeight + dimensionResource(R.dimen.search_view_additional_padding))
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    searchValue: String,
    onSearchInput: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit = {},
) {
    var active by rememberSaveable { mutableStateOf(false) }
    val history =
        remember { mutableStateListOf<String>() } // TODO: Make search history save locally

    val barHorizontalPadding by animateDpAsState(
        targetValue = if (active) {
            dimensionResource(R.dimen.search_bar_active_horizontal_padding)
        } else {
            dimensionResource(R.dimen.search_bar_inactive_horizontal_padding)
        },
        animationSpec = tween(durationMillis = 500)
    )

    val search = {
        if (searchValue.trim() != "") {
            history.remove(searchValue)
            history.add(searchValue)
            onSearch()
        }
        active = false
    }

    SearchBar(
        modifier = modifier
            .padding(horizontal = barHorizontalPadding)
            .semantics { traversalIndex = -1f },
        query = searchValue,
        onQueryChange = onSearchInput,
        onSearch = { search() },
        active = active,
        onActiveChange = { active = it },
        leadingIcon = {
            IconButton(
                onClick = { active = !active }
            ) {
                Icon(
                    imageVector = if (active) Icons.Default.ArrowBack else Icons.Default.Search,
                    contentDescription = if (active) stringResource(R.string.back) else null
                )
            }
        },
        placeholder = placeholder
    ) {
        SearchFieldHistory(
            history = history,
            onHistoryItemClick = {
                onSearchInput(it)
                search()
            }
        )
    }
}

@Composable
fun SearchFieldHistory(
    history: List<String>,
    onHistoryItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        history.reversed().forEach { historyRecord ->
            ListItem(
                headlineContent = { Text(historyRecord) },
                leadingContent = { Icon(Icons.Filled.History, contentDescription = null) },
                modifier = Modifier
                    .clickable { onHistoryItemClick(historyRecord) }
                    .fillMaxWidth()
            )
        }
    }
}