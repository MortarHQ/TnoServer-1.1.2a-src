package net.unusual.block_factorys_bosses.data.gen;

import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.common.data.DataMapProvider.Builder;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseDataMapGen extends DataMapProvider {
   public BossesRiseDataMapGen(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
      super(packOutput, lookupProvider);
   }

   protected void gather(Provider provider) {
      Builder<FurnaceFuel, Item> fuels = this.builder(NeoForgeDataMaps.FURNACE_FUELS);
      fuels.add(BossesRiseItems.PLANK, new FurnaceFuel(300), false, new ICondition[0]);
      fuels.add(BossesRiseItems.GUARDRAIL, new FurnaceFuel(300), false, new ICondition[0]);
      fuels.add(BossesRiseItems.GUARDRAIL_DIAGONAL, new FurnaceFuel(300), false, new ICondition[0]);
      fuels.add(BossesRiseItems.SHIP_STEERING_WHEEL, new FurnaceFuel(400), false, new ICondition[0]);
   }
}
