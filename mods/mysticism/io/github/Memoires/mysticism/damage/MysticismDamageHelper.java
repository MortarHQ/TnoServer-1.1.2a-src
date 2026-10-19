package io.github.Memoires.mysticism.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class MysticismDamageHelper {
   public static DamageSource create(ServerLevel level, ResourceKey<DamageType> key) {
      return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
   }

   public static DamageSource create(LivingEntity owner, ResourceKey<DamageType> key) {
      Level level = owner.level();
      return level instanceof ServerLevel sl
         ? new DamageSource(sl.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), owner)
         : new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), owner);
   }

   public static boolean isVoidDamage(DamageSource source) {
      return source.is(MysticismDamageTypes.VOID);
   }
}
