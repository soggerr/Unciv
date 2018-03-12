package com.unciv.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.unciv.logic.civilization.CivilizationInfo
import com.unciv.models.linq.Counter
import com.unciv.ui.pickerscreens.PickerScreen
import com.unciv.ui.utils.CameraStageBaseScreen

class ScienceVictoryScreen(internal val civInfo: CivilizationInfo) : PickerScreen() {

    init {
        val scienceVictory = civInfo.scienceVictory
        val builtSpaceshipParts = scienceVictory.currentParts.clone()

        for (key in scienceVictory.requiredParts.keys)
        // can't take the keyset because we would be modifying it!
            for (i in 0 until scienceVictory.requiredParts[key]!!)
                addPartButton(key, builtSpaceshipParts)

        rightSideButton.isVisible = false

        if (!civInfo.buildingUniques.contains("ApolloProgram"))
            descriptionLabel.setText("You must build the Apollo Program before you can build spaceship parts!")
        else
            descriptionLabel.setText("Apollo program is built - you may construct spaceship parts in your cities!")
        displayTutorials("ScienceVictoryScreenEntered")
    }

    private fun addPartButton(partName: String, parts: Counter<String>) {
        topTable.row()
        val button = TextButton(partName, CameraStageBaseScreen.skin)
        button.touchable = Touchable.disabled
        if (!civInfo.buildingUniques.contains("ApolloProgram"))
            button.color = Color.GRAY
        else if (parts[partName]!! > 0) {
            button.color = Color.GREEN
            parts.add(partName, -1)
        }
        topTable.add<TextButton>(button).pad(10f)
    }
}


