package me.wesley1808.servercore.mixin.features.activation_range.inactive_ticks;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AgeableMob.class)
public abstract class AgeableMobMixin extends PathfinderMob {
   private AgeableMobMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
      super(entityType, level);
   }

   @Shadow
   public abstract int getAge();

   @Shadow
   public abstract void setAge(int var1);

   public void servercore$inactiveTick() {
      super.servercore$inactiveTick();
      int age = this.getAge();
      if (age < 0) {
         this.setAge(age + 1);
      } else if (age > 0) {
         this.setAge(age - 1);
      }
   }
}
