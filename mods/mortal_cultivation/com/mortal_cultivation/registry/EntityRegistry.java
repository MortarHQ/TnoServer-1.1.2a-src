package com.mortal_cultivation.registry;

import com.mortal_cultivation.entity.MonsterGatheringTalismanEntity;
import com.mortal_cultivation.entity.ThunderTalismanEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntityRegistry {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, "mortal_cultivation");
   public static final DeferredHolder<EntityType<?>, EntityType<ThunderTalismanEntity>> THUNDER_TALISMAN_ENTITY = ENTITY_TYPES.register(
      "thunder_talisman_entity",
      () -> Builder.of(ThunderTalismanEntity::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .clientTrackingRange(4)
         .updateInterval(10)
         .build("thunder_talisman_entity")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<MonsterGatheringTalismanEntity>> MONSTER_GATHERING_TALISMAN_ENTITY = ENTITY_TYPES.register(
      "monster_gathering_talisman_entity",
      () -> Builder.of(MonsterGatheringTalismanEntity::new, MobCategory.MISC)
         .sized(0.5F, 0.8F)
         .clientTrackingRange(8)
         .updateInterval(10)
         .build("monster_gathering_talisman_entity")
   );

   public static void register(IEventBus modEventBus) {
      ENTITY_TYPES.register(modEventBus);
   }
}
