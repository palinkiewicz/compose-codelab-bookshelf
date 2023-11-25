package pl.dakil.bookshelf.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    var active by rememberSaveable { mutableStateOf(false) }
    val history =
        remember { mutableStateListOf<String>() } // TODO: Make search history save locally

    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f },
            query = searchValue,
            onQueryChange = onSearchInput,
            onSearch = {
                if (searchValue.trim() != "") {
                    history.remove(searchValue)
                    history.add(searchValue)
                    onSearch()
                }
                active = false
            },
            active = active,
            onActiveChange = { active = it },
            leadingIcon = {
                IconButton(
                    onClick = { if (active) active = false }
                ) {
                    Icon(
                        imageVector = if (active) Icons.Default.ArrowBack else Icons.Default.Search,
                        contentDescription = if (active) stringResource(R.string.back) else null
                    )
                }
            },
            placeholder = placeholder
        ) {
            history.reversed().forEach { historyRecord ->
                ListItem(
                    headlineContent = { Text(historyRecord) },
                    leadingContent = { Icon(Icons.Filled.History, contentDescription = null) },
                    modifier = Modifier
                        .clickable {
                            onSearchInput(historyRecord)
                            active = false
                        }
                        .fillMaxWidth()
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = SearchBarDefaults.InputFieldHeight + dimensionResource(R.dimen.search_view_additional_padding))
        ) { content() }
    }
}