package me.wesley1808.servercore.mixin.features.activation_range.inactive_ticks;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Arrow.class)
public abstract class ArrowMixin extends AbstractArrow {
   private ArrowMixin(EntityType<? extends AbstractArrow> entityType, Level level) {
      super(entityType, level);
   }

   public void servercore$inactiveTick() {
      super.servercore$inactiveTick();
      if (this.inGround) {
         this.tickDespawn();
      }
   }
}
