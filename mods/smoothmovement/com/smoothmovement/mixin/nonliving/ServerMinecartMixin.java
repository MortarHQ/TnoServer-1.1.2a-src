package com.smoothmovement.mixin.nonliving;

import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ServerTime;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.neoforged.neoforge.common.extensions.IAbstractMinecartExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractMinecart.class)
public abstract class ServerMinecartMixin extends Entity implements IAbstractMinecartExtension {
   public ServerMinecartMixin(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   @Redirect(
      method = "getMaxSpeedWithRail",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/block/BaseRailBlock;getRailMaxSpeed(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/vehicle/AbstractMinecart;)F",
         remap = true
      ),
      remap = false
   )
   private float adjustSpeed(BaseRailBlock instance, BlockState state, Level level, BlockPos pos, AbstractMinecart abstractMinecart) {
      float defaultValue = instance.getRailMaxSpeed(state, level, pos, abstractMinecart);
      if (this.level().isClientSide || !((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableMinecartLagAdjustedMovement) {
         return defaultValue;
      }

      if (!ServerTime.hasLag()) {
         return defaultValue;
      }

      float maxSpeed = 0.4F;
      if (state.getBlock() instanceof BaseRailBlock railBlock) {
         RailShape shape = railBlock.getRailDirection(state, level, pos, abstractMinecart);
         if (shape == RailShape.EAST_WEST || shape == RailShape.NORTH_SOUTH) {
            Direction direction = null;
            switch (shape) {
               case EAST_WEST:
                  direction = this.getDeltaMovement().x > 0.0 ? Direction.EAST : Direction.WEST;
                  break;
               case NORTH_SOUTH:
                  direction = this.getDeltaMovement().z > 0.0 ? Direction.SOUTH : Direction.NORTH;
            }

            BlockState nextRail = level.getBlockState(pos.relative(direction));
            if (nextRail.getBlock() instanceof BaseRailBlock railBlock2) {
               RailShape shape2 = railBlock2.getRailDirection(nextRail, level, pos.relative(direction), abstractMinecart);
               if (shape2 == RailShape.EAST_WEST || shape2 == RailShape.NORTH_SOUTH) {
                  maxSpeed = 6.4F;
               }
            }
         }
      }

      return Math.min(maxSpeed, defaultValue * ServerTime.slownessFactor);
   }

   @ModifyConstant(method = "moveAlongTrack", constant = @Constant(doubleValue = 0.06))
   private double adjustValue(double constant) {
      return !this.level().isClientSide
            && ServerTime.hasLag()
            && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableMinecartLagAdjustedMovement
         ? constant * ServerTime.slownessFactor
         : constant;
   }

   @ModifyConstant(method = "moveAlongTrack", constant = @Constant(doubleValue = 0.02))
   private double adjustValue2(double constant) {
      return !this.level().isClientSide
            && ServerTime.hasLag()
            && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableMinecartLagAdjustedMovement
         ? constant * ServerTime.slownessFactor
         : constant;
   }

   @ModifyConstant(method = "moveAlongTrack", constant = @Constant(doubleValue = -0.02))
   private double adjustValue3(double constant) {
      return !this.level().isClientSide
            && ServerTime.hasLag()
            && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableMinecartLagAdjustedMovement
         ? constant * ServerTime.slownessFactor
         : constant;
   }
}
