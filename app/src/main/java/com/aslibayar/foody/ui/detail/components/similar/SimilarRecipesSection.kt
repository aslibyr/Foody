import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.foody.ui.listing.RecipeItem
import com.aslibayar.foody.ui.theme.CustomTextStyle
import com.aslibayar.foody.ui.theme.Orange

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
                color = Orange,
                style = CustomTextStyle.regularBlackLarge,
            )

            LazyRow(
                contentPadding = PaddingValues(8.dp),
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
