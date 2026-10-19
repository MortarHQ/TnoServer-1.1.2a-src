package io.github.Memoires.mysticism.registry.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.entity.boss.memoires.MemoiresEntity;
import io.github.Memoires.mysticism.entity.golems.ClayGolemEntity;
import io.github.Memoires.mysticism.entity.monster.OkamiEntity;
import io.github.Memoires.mysticism.entity.monster.ShadowImpEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;

public class MysticismMonsterEntityTypes {
   private static final DeferredRegister<EntityType<?>> MONSTERS = DeferredRegister.create("mysticism", Registries.ENTITY_TYPE);
   public static final RegistrySupplier<EntityType<OkamiEntity>> OKAMI = MONSTERS.register(
      "okami",
      () -> Builder.of(OkamiEntity::new, MobCategory.MONSTER)
         .sized(0.85F, 1.85F)
         .clientTrackingRange(10)
         .build(ResourceLocation.fromNamespaceAndPath("mysticism", "okami").toString())
   );
   public static final RegistrySupplier<EntityType<ClayGolemEntity>> CLAY_GOLEM = MONSTERS.register(
      "clay_golem",
      () -> Builder.of(ClayGolemEntity::new, MobCategory.MONSTER)
         .sized(0.45F, 1.35F)
         .clientTrackingRange(10)
         .build(ResourceLocation.fromNamespaceAndPath("mysticism", "clay_golem").toString())
   );
   public static final RegistrySupplier<EntityType<MemoiresEntity>> MEMOIRES = MONSTERS.register(
      "memoires",
      () -> Builder.of(MemoiresEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.8F)
         .eyeHeight(1.62F)
         .build(ResourceLocation.fromNamespaceAndPath("mysticism", "memoires").toString())
   );
   public static final RegistrySupplier<EntityType<ShadowImpEntity>> SHADOW_IMP = MONSTERS.register(
      "shadow_imp",
      () -> Builder.of(ShadowImpEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.2F)
         .eyeHeight(1.05F)
         .build(ResourceLocation.fromNamespaceAndPath("mysticism", "shadow_imp").toString())
   );

   public static void init() {
      MONSTERS.register();
   }
}
