package com.aslibayar.foody.ui.detail.components.instructions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.data.model.Step
import com.aslibayar.foody.ui.theme.CustomTextStyle
import com.aslibayar.foody.ui.theme.Orange

@Composable
fun InstructionSection(recipe: RecipeDetailUIModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "Instructions",
            color = Orange,
            style = CustomTextStyle.regularBlackLarge
        )
    }
    InstructionSteps(
        instructions = recipe.analyzedInstructions.firstOrNull()?.steps
            ?: emptyList()
    )
}

@Composable
fun InstructionSteps(instructions: List<Step>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        instructions.forEach { step ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "${step.number}.",
                    style = CustomTextStyle.regularBlackMedium,
                    modifier = Modifier.width(32.dp)
                )
                Text(
                    text = step.step,
                    style = CustomTextStyle.regularBlackMedium,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}