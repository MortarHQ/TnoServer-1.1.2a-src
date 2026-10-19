package com.github.b4ndithelps.tennogamenolife.registry.entity;

import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.github.b4ndithelps.tennogamenolife.entity.projectile.BlackRodProjectile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TenreincarnationEntities {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, "tennogamenolife");
   public static final DeferredHolder<EntityType<?>, EntityType<DragonRoarCircle>> DRAGON_ROAR_CIRCLE = ENTITY_TYPES.register(
      "dragon_roar_circle",
      () -> Builder.of(DragonRoarCircle::new, MobCategory.MISC)
         .sized(1.0F, 0.1F)
         .noSave()
         .clientTrackingRange(128)
         .updateInterval(1)
         .build("dragon_roar_circle")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<BlackRodProjectile>> BLACK_ROD = ENTITY_TYPES.register(
      "black_rod",
      () -> Builder.of(BlackRodProjectile::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .clientTrackingRange(128)
         .updateInterval(1)
         .setShouldReceiveVelocityUpdates(true)
         .build("black_rod")
   );

   public static void register(IEventBus eventBus) {
      ENTITY_TYPES.register(eventBus);
   }
}
