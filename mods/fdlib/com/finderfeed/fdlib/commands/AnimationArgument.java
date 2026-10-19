package com.finderfeed.fdlib.commands;

import com.finderfeed.fdlib.systems.FDRegistries;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.resources.ResourceLocation;

public class AnimationArgument implements ArgumentType<String> {
   private static List<String> locations;

   public AnimationArgument() {
      if (locations == null) {
         locations = FDRegistries.ANIMATIONS.keySet().stream().<String>map(ResourceLocation::toString).toList();
      }
   }

   public String parse(StringReader reader) throws CommandSyntaxException {
      return ResourceLocation.read(reader).toString();
   }

   public Collection<String> getExamples() {
      return locations;
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      String location = builder.getRemaining();
      locations.stream().filter(str -> str.contains(location)).forEach(builder::suggest);
      return builder.buildFuture();
   }
}
