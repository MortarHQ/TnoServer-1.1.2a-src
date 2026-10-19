package org.popcraft.chunky.command.suggestion;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;

public final class SuggestionProviders {
   public static final SuggestionProvider<CommandSourceStack> PATTERNS = new PatternSuggestionProvider();
   public static final SuggestionProvider<CommandSourceStack> SHAPES = new ShapeSuggestionProvider();
   public static final SuggestionProvider<CommandSourceStack> TRIM_MODES = new TrimModeSuggestionProvider();

   private SuggestionProviders() {
   }
}
