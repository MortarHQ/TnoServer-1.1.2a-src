package net.blay09.mods.waystones.compat;

import java.util.function.Supplier;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.config.WaystonesConfigData;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RepurposedStructuresIntegration {
   public static final DeferredRegister<Supplier<Boolean>> RS_CONDITIONS_REGISTRY = DeferredRegister.create(
      ResourceLocation.fromNamespaceAndPath("repurposed_structures", "json_conditions"), "waystones"
   );
   public static final DeferredHolder<Supplier<Boolean>, Supplier<Boolean>> WAYSTONE_CONFIG_CONDITION = RS_CONDITIONS_REGISTRY.register(
      "config", () -> () -> WaystonesConfig.getActive().worldGen.spawnInVillages != WaystonesConfigData.VillageWaystoneGeneration.DISABLED
   );

   public RepurposedStructuresIntegration() {
      IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
      RS_CONDITIONS_REGISTRY.register(modEventBus);
   }
}
