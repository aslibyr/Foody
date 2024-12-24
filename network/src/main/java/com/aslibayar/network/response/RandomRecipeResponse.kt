import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RandomRecipeResponse(

    @SerialName("recipes")
    val recipes: List<RecipesItem?>? = null
)

@Serializable
data class Metric(

    @SerialName("amount")
    val amount: Double? = null,

    @SerialName("unitShort")
    val unitShort: String? = null,

    @SerialName("unitLong")
    val unitLong: String? = null
)

@Serializable
data class Measures(

    @SerialName("metric")
    val metric: Metric? = null,

    @SerialName("us")
    val us: Us? = null
)

@Serializable
data class IngredientsItem(

    @SerialName("image")
    val image: String? = null,

    @SerialName("localizedName")
    val localizedName: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("id")
    val id: Int? = null
)

@Serializable
data class Us(

    @SerialName("amount")
    val amount: Double? = null,

    @SerialName("unitShort")
    val unitShort: String? = null,

    @SerialName("unitLong")
    val unitLong: String? = null
)

@Serializable
data class StepsItem(

    @SerialName("number")
    val number: Int? = null,

    @SerialName("ingredients")
    val ingredients: List<String?>? = null,

    @SerialName("equipment")
    val equipment: List<EquipmentItem?>? = null,

    @SerialName("step")
    val step: String? = null,

    @SerialName("length")
    val length: Length? = null
)

@Serializable
data class RecipesItem(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,

//    @SerialName("instructions")
//    val instructions: String? = null,
//
//    @SerialName("sustainable")
//    val sustainable: Boolean? = null,
//
//    @SerialName("analyzedInstructions")
//    val analyzedInstructions: List<AnalyzedInstructionsItem?>? = null,
//
    @SerialName("glutenFree")
    val glutenFree: Boolean? = null,
//
//    @SerialName("veryPopular")
//    val veryPopular: Boolean? = null,
//
//    @SerialName("healthScore")
//    val healthScore: Int? = null,

//    @SerialName("diets")
//    val diets: List<String?>? = null,
//
//    @SerialName("aggregateLikes")
//    val aggregateLikes: Int? = null,
//
//    @SerialName("creditsText")
//    val creditsText: String? = null,
//
    @SerialName("readyInMinutes")
    val readyInMinutes: Int? = null,
//
//    @SerialName("sourceUrl")
//    val sourceUrl: String? = null,
//
//    @SerialName("dairyFree")
//    val dairyFree: Boolean? = null,
//
//    @SerialName("servings")
//    val servings: Int? = null,
//
    @SerialName("vegetarian")
    val vegetarian: Boolean? = null,

    @SerialName("preparationMinutes")
    val preparationMinutes: Double? = null,
//
//    @SerialName("imageType")
//    val imageType: String? = null,
//
//    @SerialName("summary")
//    val summary: String? = null,
//
    @SerialName("cookingMinutes")
    val cookingMinutes: Double? = null,

    @SerialName("image")
    val image: String? = null,
//
//    @SerialName("veryHealthy")
//    val veryHealthy: Boolean? = null,
//
    @SerialName("vegan")
    val vegan: Boolean? = null,
//
//    @SerialName("cheap")
//    val cheap: Boolean? = null,
//
//    @SerialName("extendedIngredients")
//    val extendedIngredients: List<ExtendedIngredientsItem?>? = null,
//
//    @SerialName("dishTypes")
//    val dishTypes: List<String?>? = null,
//
//    @SerialName("gaps")
//    val gaps: String? = null,
//
//    @SerialName("cuisines")
//    val cuisines: List<String?>? = null,
//
//    @SerialName("lowFodmap")
//    val lowFodmap: Boolean? = null,
//
//    @SerialName("license")
//    val license: String? = null,
//
//    @SerialName("weightWatcherSmartPoints")
//    val weightWatcherSmartPoints: Int? = null,
//
//    @SerialName("occasions")
//    val occasions: List<String?>? = null,
//
//    @SerialName("pricePerServing")
//    val pricePerServing: Double? = null,
//
//    @SerialName("spoonacularScore")
//    val spoonacularScore: Double? = null,
//
//    @SerialName("sourceName")
//    val sourceName: String? = null,
//
//    @SerialName("originalId")
//    val originalId: Double? = null,
//
//    @SerialName("spoonacularSourceUrl")
//    val spoonacularSourceUrl: String? = null
)

@Serializable
data class Length(

    @SerialName("number")
    val number: Int? = null,

    @SerialName("unit")
    val unit: String? = null
)

@Serializable
data class EquipmentItem(

    @SerialName("image")
    val image: String? = null,

    @SerialName("localizedName")
    val localizedName: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("temperature")
    val temperature: Temperature? = null,

    @SerialName("id")
    val id: Int? = null
)

@Serializable
data class AnalyzedInstructionsItem(

    @SerialName("name")
    val name: String? = null,

    @SerialName("steps")
    val steps: List<StepsItem?>? = null
)

@Serializable
data class Temperature(

    @SerialName("number")
    val number: Double? = null,

    @SerialName("unit")
    val unit: String? = null
)

@Serializable
data class ExtendedIngredientsItem(

    @SerialName("originalName")
    val originalName: String? = null,

    @SerialName("image")
    val image: String? = null,

    @SerialName("amount")
    val amount: Double? = null,

    @SerialName("unit")
    val unit: String? = null,

    @SerialName("measures")
    val measures: Measures? = null,

    @SerialName("nameClean")
    val nameClean: String? = null,

    @SerialName("original")
    val original: String? = null,

    @SerialName("meta")
    val meta: List<String?>? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("aisle")
    val aisle: String? = null,

    @SerialName("consistency")
    val consistency: String? = null
)

