package pl.dakil.bookshelf.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import pl.dakil.bookshelf.R

@Composable
fun ErrorScreen(
    errorText: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    retryText: String = stringResource(R.string.retry)
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Outlined.Error, contentDescription = null)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_padding)))
        Text(text = errorText)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_padding)))
        Button(onClick = onRetry) {
            Text(text = retryText)
        }
    }
}