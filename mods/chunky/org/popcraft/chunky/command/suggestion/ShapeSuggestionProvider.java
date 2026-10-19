package org.popcraft.chunky.command.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import org.popcraft.chunky.shape.ShapeType;

public class ShapeSuggestionProvider implements SuggestionProvider<CommandSourceStack> {
   public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
      try {
         String input = (String)context.getArgument("shape", String.class);
         ShapeType.all().forEach(shape -> {
            if (shape.contains(input.toLowerCase())) {
               builder.suggest(shape);
            }
         });
      } catch (IllegalArgumentException e) {
         ShapeType.all().forEach(builder::suggest);
      }

      return builder.buildFuture();
   }
}
