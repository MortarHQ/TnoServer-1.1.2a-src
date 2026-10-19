package de.cristelknight.cristellib.config.client.simple;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record SimpleConfigScreen(Class<?> simpleConfig, String screenName, Runnable onScreenSave) {
}
