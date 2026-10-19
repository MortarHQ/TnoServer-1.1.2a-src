package artifacts.neoforge.data;

import artifacts.registry.ModGameEvents;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.common.data.DataMapProvider.Builder;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.VibrationFrequency;

public class DataMaps extends DataMapProvider {
   public DataMaps(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
      super(packOutput, lookupProvider);
   }

   protected void gather(Provider provider) {
      Builder<VibrationFrequency, GameEvent> builder = this.builder(NeoForgeDataMaps.VIBRATION_FREQUENCIES);
      ModGameEvents.VIBRATION_FREQUENCIES.forEach((holder, frequency) -> builder.add(holder, new VibrationFrequency(frequency), false, new ICondition[0]));
   }
}
