/*
 * Copyright (C) 2020 The exTHmUI Open Source Project
 * Copyright (C) 2021 AOSP-Krypton Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.exthmui.game.qs

import android.content.Context
import android.provider.Settings

import org.exthmui.game.R

class AutoBrightnessTile(context: Context) : TileBase(context) {

    private val initialMode = Settings.System.getInt(
        context.contentResolver,
        Settings.System.SCREEN_BRIGHTNESS_MODE,
        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
    )

    init {
        setText(R.string.qs_auto_brightness)
        setIcon(R.drawable.ic_qs_auto_brightness)
        val isAuto = initialMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        if (isAuto) {
            val disable = Settings.System.getInt(
                context.contentResolver,
                Settings.System.GAMING_MODE_DISABLE_AUTO_BRIGHTNESS,
                1
            ) == 1
            isSelected = if (disable) {
                Settings.System.putInt(
                    context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                )
                false
            } else {
                true
            }
        }
    }

    override fun handleClick(isSelected: Boolean) {
        super.handleClick(isSelected)
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            if (isSelected) Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            else Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
    }

    override fun onDestroy() {
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            initialMode
        )
    }
}
