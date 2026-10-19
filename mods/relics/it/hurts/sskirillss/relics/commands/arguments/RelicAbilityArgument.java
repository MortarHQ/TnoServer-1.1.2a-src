package it.hurts.sskirillss.relics.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.SharedSuggestionProvider;

public class RelicAbilityArgument implements ArgumentType<String> {
   public static RelicAbilityArgument ability() {
      return new RelicAbilityArgument();
   }

   public static String getAbility(CommandContext<?> context, String name) {
      return (String)context.getArgument(name, String.class);
   }

   public String parse(StringReader reader) throws CommandSyntaxException {
      return reader.readString();
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      try {
         LocalPlayer player = Minecraft.getInstance().player;
         if (player != null && player.getMainHandItem().getItem() instanceof IRelicItem relic) {
            List<String> result = new ArrayList<>(relic.getRelicData().getAbilities().getAbilities().keySet());
            result.add("all");
            return SharedSuggestionProvider.suggest(result, builder);
         } else {
            return Suggestions.empty();
         }
      } catch (Throwable $ex) {
         throw $ex;
      }
   }
}
