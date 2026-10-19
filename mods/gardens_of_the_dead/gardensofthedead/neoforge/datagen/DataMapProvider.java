package gardensofthedead.neoforge.datagen;

import gardensofthedead.registry.ModItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

public class DataMapProvider extends net.neoforged.neoforge.common.data.DataMapProvider {
   public DataMapProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
      super(packOutput, lookupProvider);
   }

   protected void gather() {
      ModItems.addCompostables(
         (item, p) -> this.builder(NeoForgeDataMaps.COMPOSTABLES)
            .add(BuiltInRegistries.ITEM.wrapAsHolder(item.asItem()), new Compostable(p), false, new ICondition[0])
      );
   }
}
