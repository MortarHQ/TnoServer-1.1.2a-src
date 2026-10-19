package fuzs.puzzleslib.api.init.v3.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.LevelReader;

public final class LookupHelper {
   private LookupHelper() {
   }

   public static <T> Holder<T> lookup(Entity entity, ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> resourceKey) {
      return lookup(entity.registryAccess(), registryKey, resourceKey);
   }

   public static <T> Holder<T> lookup(LevelReader levelReader, ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> resourceKey) {
      return lookup(levelReader.registryAccess(), registryKey, resourceKey);
   }

   public static <T> Holder<T> lookup(Provider registries, ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> resourceKey) {
      return registries.lookupOrThrow(registryKey).getOrThrow(resourceKey);
   }

   public static Holder<Enchantment> lookupEnchantment(Entity entity, ResourceKey<Enchantment> resourceKey) {
      return lookup(entity.registryAccess(), Registries.ENCHANTMENT, resourceKey);
   }

   public static Holder<Enchantment> lookupEnchantment(LevelReader levelReader, ResourceKey<Enchantment> resourceKey) {
      return lookup(levelReader.registryAccess(), Registries.ENCHANTMENT, resourceKey);
   }

   public static Holder<Enchantment> lookupEnchantment(Provider registries, ResourceKey<Enchantment> resourceKey) {
      return registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(resourceKey);
   }

   public static Holder<DamageType> lookupDamageType(Entity entity, ResourceKey<DamageType> resourceKey) {
      return lookup(entity.registryAccess(), Registries.DAMAGE_TYPE, resourceKey);
   }

   public static Holder<DamageType> lookupDamageType(LevelReader levelReader, ResourceKey<DamageType> resourceKey) {
      return lookup(levelReader.registryAccess(), Registries.DAMAGE_TYPE, resourceKey);
   }

   public static Holder<DamageType> lookupDamageType(Provider registries, ResourceKey<DamageType> resourceKey) {
      return registries.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(resourceKey);
   }
}
