package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.worldgen.features.NbtTemplateFeature;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class ModFeatures {
   public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create("chronodawn", Registries.FEATURE);
   public static final RegistrySupplier<NbtTemplateFeature> NBT_TEMPLATE = FEATURES.register("nbt_template", NbtTemplateFeature::new);

   public static void register() {
      FEATURES.register();
      ChronoDawn.LOGGER.debug("Registered ModFeatures");
   }

   private ModFeatures() {
      throw new UnsupportedOperationException("Utility class");
   }
}
