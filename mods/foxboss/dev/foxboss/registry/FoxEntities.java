package dev.foxboss.registry;

import dev.foxboss.entity.DragonStarEntity;
import dev.foxboss.entity.MilimEntity;
import dev.foxboss.entity.ThrownBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class FoxEntities {
   public static final DeferredRegister<EntityType<?>> TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, "foxboss");
   public static final DeferredHolder<EntityType<?>, EntityType<MilimEntity>> MILIM = TYPES.register(
      "milim",
      () -> Builder.of(MilimEntity::new, MobCategory.MONSTER)
         .sized(0.65F, 1.95F)
         .eyeHeight(1.68F)
         .clientTrackingRange(12)
         .updateInterval(1)
         .fireImmune()
         .build("foxboss:milim")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<DragonStarEntity>> DRAGON_STAR = TYPES.register(
      "dragon_star",
      () -> Builder.of(DragonStarEntity::new, MobCategory.MISC)
         .sized(0.25F, 0.25F)
         .clientTrackingRange(16)
         .updateInterval(20)
         .noSave()
         .fireImmune()
         .build("foxboss:dragon_star")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ThrownBlockEntity>> THROWN_BLOCK = TYPES.register(
      "thrown_block",
      () -> Builder.of(ThrownBlockEntity::new, MobCategory.MISC)
         .sized(0.8F, 0.8F)
         .clientTrackingRange(16)
         .updateInterval(1)
         .fireImmune()
         .build("foxboss:thrown_block")
   );

   private FoxEntities() {
   }
}
