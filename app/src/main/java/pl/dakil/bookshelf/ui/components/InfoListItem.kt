package pl.dakil.bookshelf.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import pl.dakil.bookshelf.R

@Composable
fun InfoListItem(
    headlineText: String,
    supportingText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            horizontal = dimensionResource(R.dimen.info_list_item_horizontal_padding),
            vertical = dimensionResource(R.dimen.info_list_item_vertical_padding),
        )
    ) {
        Text(
            text = headlineText,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.info_list_item_space_between_text)))
        Text(
            text = supportingText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}