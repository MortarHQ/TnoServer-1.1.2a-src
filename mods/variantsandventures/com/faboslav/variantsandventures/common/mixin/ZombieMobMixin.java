package com.faboslav.variantsandventures.common.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Mob.class)
public abstract class ZombieMobMixin extends ZombieLivingEntityMixin {
   @Shadow
   @Nullable
   public abstract <T extends Mob> T convertTo(EntityType<T> var1, boolean var2);

   @Shadow
   public abstract boolean isNoAi();

   public ZombieMobMixin(EntityType<?> entityType, Level level) {
      super(entityType, level);
   }
}
