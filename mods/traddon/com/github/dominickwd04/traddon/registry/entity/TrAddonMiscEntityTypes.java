package com.github.dominickwd04.traddon.registry.entity;

import com.github.dominickwd04.traddon.entity.magic.field.cloud.CrystalDustCloud;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;

public class TrAddonMiscEntityTypes {
   private static final DeferredRegister<EntityType<?>> MISC = DeferredRegister.create("tensura", Registries.ENTITY_TYPE);
   public static final RegistrySupplier<EntityType<CrystalDustCloud>> CRYSTAL_DUST_CLOUD = MISC.register(
      "crystal_dust_cloud", () -> Builder.of(CrystalDustCloud::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).build("crystal_dust_cloud")
   );

   public static void init() {
      MISC.register();
   }
}
