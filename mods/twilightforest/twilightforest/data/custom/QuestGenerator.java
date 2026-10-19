package twilightforest.data.custom;

import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import twilightforest.TwilightForestMod;
import twilightforest.entity.passive.quest.ram.QuestingRamContext;

public class QuestGenerator implements DataProvider {
   private final PackOutput output;

   public QuestGenerator(PackOutput output) {
      this.output = output;
   }

   public CompletableFuture<?> run(CachedOutput output) {
      Function<String, Path> questPath = s -> this.output
         .getOutputFolder()
         .resolve(String.format("data/%s/%s/%s/%s.json", "twilightforest", "twilight", "quests", s));
      Builder<CompletableFuture<?>> futuresBuilder = new Builder();
      futuresBuilder.add(
         DataProvider.saveStable(
            output,
            (JsonElement)QuestingRamContext.CODEC
               .encodeStart(JsonOps.INSTANCE, QuestingRamContext.FALLBACK)
               .resultOrPartial(TwilightForestMod.LOGGER::error)
               .orElseThrow(),
            questPath.apply("questing_ram")
         )
      );
      return CompletableFuture.allOf((CompletableFuture<?>[])futuresBuilder.build().toArray(CompletableFuture[]::new));
   }

   public String getName() {
      return "Twilight Forest Quests";
   }
}
