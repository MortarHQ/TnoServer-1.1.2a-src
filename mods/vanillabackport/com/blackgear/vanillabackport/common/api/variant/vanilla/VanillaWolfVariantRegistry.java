package com.blackgear.vanillabackport.common.api.variant.vanilla;

import com.blackgear.vanillabackport.common.api.variant.spawn.PriorityProvider;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnContext;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.WolfVariant;

public class VanillaWolfVariantRegistry {
   private static final Map<ResourceKey<WolfVariant>, VanillaWolfVariantSpawnSelector> SELECTORS = new ConcurrentHashMap<>();

   public static void register(ResourceKey<WolfVariant> key, VanillaWolfVariantSpawnSelector selector) {
      SELECTORS.put(key, selector);
   }

   public static Collection<VanillaWolfVariantSpawnSelector> getSelectors() {
      return Collections.unmodifiableCollection(SELECTORS.values());
   }

   public static Optional<VanillaWolfVariantSpawnSelector> getSelector(ResourceKey<WolfVariant> key) {
      return Optional.ofNullable(SELECTORS.get(key));
   }

   public static Optional<Holder<WolfVariant>> selectVariantForSpawn(SpawnContext context, RandomSource random) {
      return SELECTORS.isEmpty()
         ? Optional.empty()
         : PriorityProvider.<SpawnContext, VanillaWolfVariantSpawnSelector>pick(SELECTORS.values().stream(), selector -> selector, random, context)
            .map(VanillaWolfVariantSpawnSelector::vanillaVariant);
   }

   public static void clear() {
      SELECTORS.clear();
   }

   public static boolean isEmpty() {
      return SELECTORS.isEmpty();
   }
}
