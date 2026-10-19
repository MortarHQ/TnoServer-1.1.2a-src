package net.p3pp3rf1y.sophisticatedbackpacks.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.UuidArgument;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackAccessLogger;

public class BackpackUUIDArgumentType extends UuidArgument {
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      if (context.getSource() instanceof CommandSourceStack) {
         return SharedSuggestionProvider.suggest(BackpackAccessLogger.getBackpackUuids().stream().map(UUID::toString).toList(), builder);
      } else {
         return context.getSource() instanceof SharedSuggestionProvider sharedSuggestionProvider
            ? sharedSuggestionProvider.customSuggestion(context)
            : Suggestions.empty();
      }
   }

   public static BackpackUUIDArgumentType backpackUuid() {
      return new BackpackUUIDArgumentType();
   }
}
