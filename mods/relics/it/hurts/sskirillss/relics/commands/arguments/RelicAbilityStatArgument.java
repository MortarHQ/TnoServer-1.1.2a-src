package it.hurts.sskirillss.relics.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.SharedSuggestionProvider;

public class RelicAbilityStatArgument implements ArgumentType<String> {
   public static RelicAbilityStatArgument abilityStat() {
      return new RelicAbilityStatArgument();
   }

   public static String getAbilityStat(CommandContext<?> context, String name) {
      return (String)context.getArgument(name, String.class);
   }

   public String parse(StringReader reader) throws CommandSyntaxException {
      return reader.readString();
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      try {
         LocalPlayer player = Minecraft.getInstance().player;
         if (player != null && player.getMainHandItem().getItem() instanceof IRelicItem relic) {
            String ability = StringArgumentType.getString(context, "ability");
            List<String> result = new ArrayList<>();
            if (ability.equals("all")) {
               for (AbilityData abilityEntry : relic.getRelicData().getAbilities().getAbilities().values()) {
                  result.addAll(abilityEntry.getStats().keySet());
               }
            } else {
               AbilityData data = relic.getAbilityData(ability);
               if (data == null) {
                  return Suggestions.empty();
               }

               result.addAll(data.getStats().keySet());
            }

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
