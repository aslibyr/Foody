import com.aslibayar.network.response.RecipeAIResponse

object AIResponseMapper {
    private const val RECIPE_NAME_PREFIX = "Recipe Name:"
    private const val INGREDIENTS_PREFIX = "Ingredients:"
    private const val INSTRUCTIONS_PREFIX = "Instructions:"
    private const val COOKING_TIME_PREFIX = "Cooking Time:"
    private const val SERVINGS_PREFIX = "Servings:"

    fun mapAIResponseToRecipe(aiResponse: String): RecipeAIResponse {
        val sections = extractSections(aiResponse)

        return RecipeAIResponse(
            recipeName = sections.getValueAfterPrefix(RECIPE_NAME_PREFIX),
            ingredients = sections.getListAfterPrefix(INGREDIENTS_PREFIX),
            instructions = sections.getListAfterPrefix(INSTRUCTIONS_PREFIX),
            cookingTime = sections.getValueAfterPrefix(COOKING_TIME_PREFIX),
            servings = sections.getValueAfterPrefix(SERVINGS_PREFIX)
        )
    }

    private fun extractSections(response: String): Map<String, List<String>> {
        val sections = mutableMapOf<String, MutableList<String>>()
        var currentSection = ""

        response.split("\n").forEach { line ->
            when {
                line.startsWith(RECIPE_NAME_PREFIX, ignoreCase = true) -> {
                    currentSection = RECIPE_NAME_PREFIX
                    sections.getOrPut(currentSection) { mutableListOf() }
                        .add(line.substringAfter(":").trim())
                }

                line.startsWith(INGREDIENTS_PREFIX, ignoreCase = true) -> {
                    currentSection = INGREDIENTS_PREFIX
                    sections[currentSection] = mutableListOf()
                }

                line.startsWith(INSTRUCTIONS_PREFIX, ignoreCase = true) -> {
                    currentSection = INSTRUCTIONS_PREFIX
                    sections[currentSection] = mutableListOf()
                }

                line.startsWith(COOKING_TIME_PREFIX, ignoreCase = true) -> {
                    currentSection = COOKING_TIME_PREFIX
                    sections.getOrPut(currentSection) { mutableListOf() }
                        .add(line.substringAfter(":").trim())
                }

                line.startsWith(SERVINGS_PREFIX, ignoreCase = true) -> {
                    currentSection = SERVINGS_PREFIX
                    sections.getOrPut(currentSection) { mutableListOf() }
                        .add(line.substringAfter(":").trim())
                }

                line.isNotBlank() -> {
                    sections.getOrPut(currentSection) { mutableListOf() }
                        .add(line.trim())
                }
            }
        }
        return sections
    }

    private fun Map<String, List<String>>.getValueAfterPrefix(prefix: String): String {
        return this[prefix]?.firstOrNull() ?: ""
    }

    private fun Map<String, List<String>>.getListAfterPrefix(prefix: String): List<String> {
        return this[prefix]?.filter { it.isNotBlank() } ?: emptyList()
    }
} 