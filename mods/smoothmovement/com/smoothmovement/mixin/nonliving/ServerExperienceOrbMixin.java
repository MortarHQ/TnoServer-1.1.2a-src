package com.smoothmovement.mixin.nonliving;

import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ServerTime;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ExperienceOrb.class)
public abstract class ServerExperienceOrbMixin extends Entity {
   public ServerExperienceOrbMixin(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   @ModifyArg(
      method = "tick",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/ExperienceOrb;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"
      )
   )
   private Vec3 changeVel(Vec3 org) {
      return !this.level().isClientSide && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableExpOrbLagAdjustedMovement
         ? org.scale(ServerTime.slownessFactor)
         : org;
   }
}
