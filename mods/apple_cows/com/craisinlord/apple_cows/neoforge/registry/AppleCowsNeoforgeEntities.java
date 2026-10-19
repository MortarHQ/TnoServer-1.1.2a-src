package com.craisinlord.apple_cows.neoforge.registry;

import com.craisinlord.apple_cows.content.entity.AppleCowEntityVariants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.animal.Cow;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class AppleCowsNeoforgeEntities {
   private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, "apple_cows");
   public static final DeferredHolder<EntityType<?>, EntityType<AppleCowEntityVariants.AppleCow>> APPLE_COW = ENTITY_TYPES.register(
      "apple_cow", () -> buildCowType(AppleCowEntityVariants.AppleCow::new, "apple_cow")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AppleCowEntityVariants.GoldenAppleCow>> GOLDEN_APPLE_COW = ENTITY_TYPES.register(
      "golden_apple_cow", () -> buildCowType(AppleCowEntityVariants.GoldenAppleCow::new, "golden_apple_cow")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AppleCowEntityVariants.EnchantedGoldenAppleCow>> ENCHANTED_GOLDEN_APPLE_COW = ENTITY_TYPES.register(
      "enchanted_golden_apple_cow", () -> buildCowType(AppleCowEntityVariants.EnchantedGoldenAppleCow::new, "enchanted_golden_apple_cow")
   );

   private AppleCowsNeoforgeEntities() {
   }

   public static void register(IEventBus modEventBus) {
      ENTITY_TYPES.register(modEventBus);
   }

   private static <T extends Cow> EntityType<T> buildCowType(EntityFactory<T> factory, String name) {
      return Builder.of(factory, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10).build(name);
   }
}
