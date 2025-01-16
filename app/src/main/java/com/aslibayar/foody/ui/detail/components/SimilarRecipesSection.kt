import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.foody.ui.listing.RecipeItem

@Composable
fun SimilarRecipesSection(
    recipes: List<RecipeUIModel>,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (recipes.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Similar Recipes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recipes) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        onRecipeClick = { onRecipeClick(recipe.id) },
                        modifier = Modifier.width(160.dp)
                    )
                }
            }
        }
    }
}
