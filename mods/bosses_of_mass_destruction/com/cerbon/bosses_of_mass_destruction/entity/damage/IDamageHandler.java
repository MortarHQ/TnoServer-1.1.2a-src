package com.cerbon.bosses_of_mass_destruction.entity.damage;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityStats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface IDamageHandler {
   void beforeDamage(IEntityStats var1, DamageSource var2, float var3);

   void afterDamage(IEntityStats var1, DamageSource var2, float var3, boolean var4);

   boolean shouldDamage(LivingEntity var1, DamageSource var2, float var3);
}
