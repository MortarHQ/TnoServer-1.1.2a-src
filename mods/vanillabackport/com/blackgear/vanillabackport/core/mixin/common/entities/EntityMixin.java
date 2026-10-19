package com.blackgear.vanillabackport.core.mixin.common.entities;

import com.blackgear.vanillabackport.common.api.extensions.AirborneMoverEntity;
import com.blackgear.vanillabackport.common.api.extensions.MotionAwareEntity;
import com.blackgear.vanillabackport.common.registries.ModAttributes;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements MotionAwareEntity {
   @Shadow
   public boolean verticalCollision;
   @Shadow
   public boolean horizontalCollision;
   @Shadow
   public boolean verticalCollisionBelow;
   @Unique
   private Vec3 lastKnownSpeed = Vec3.ZERO;
   @Unique
   @Nullable
   private Vec3 lastKnownPosition;
   @Unique
   private Vec3 vb$postCollideMov;
   @Unique
   private Vec3 vb$preCollideVelocity;
   @Unique
   private boolean vb$doBounce;
   @Unique
   private boolean vb$xCollFromCollide;
   @Unique
   private boolean vb$zCollFromCollide;

   @Shadow
   public abstract Vec3 position();

   @Shadow
   @Nullable
   public abstract LivingEntity getControllingPassenger();

   @Shadow
   public abstract boolean isAlive();

   @Shadow
   public abstract double getX();

   @Shadow
   public abstract double getY();

   @Shadow
   public abstract double getZ();

   @Shadow
   public abstract Level level();

   @Shadow
   @Deprecated
   public abstract BlockPos getOnPosLegacy();

   @Shadow
   public abstract Vec3 getDeltaMovement();

   @Shadow
   public abstract void gameEvent(Holder<GameEvent> var1);

   @Shadow
   public abstract void setDeltaMovement(Vec3 var1);

   @Shadow
   public abstract boolean isNoGravity();

   @Shadow
   public abstract boolean onGround();

   @Inject(method = "reapplyPosition()V", at = @At("TAIL"))
   private void vb$reapplyPosition(CallbackInfo ci) {
      this.lastKnownPosition = null;
   }

   @Inject(method = "baseTick()V", at = @At("TAIL"))
   private void vb$baseTick(CallbackInfo ci) {
      this.computeSpeed();
   }

   @Override
   public void computeSpeed() {
      if (this.lastKnownPosition == null) {
         this.lastKnownPosition = this.position();
      }

      this.lastKnownSpeed = this.position().subtract(this.lastKnownPosition);
      this.lastKnownPosition = this.position();
   }

   @Override
   public Vec3 getKnownSpeed() {
      return this.getControllingPassenger() instanceof Player player && this.isAlive() ? ((MotionAwareEntity)player).getKnownSpeed() : this.lastKnownSpeed;
   }

   @Inject(method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"))
   private void vb$moveHead(MoverType type, Vec3 delta, CallbackInfo ci) {
      this.vb$doBounce = vb$hasBounce((Entity)this);
      if (this.vb$doBounce) {
         this.vb$preCollideVelocity = this.getDeltaMovement();
         this.vb$postCollideMov = null;
         this.vb$xCollFromCollide = false;
         this.vb$zCollFromCollide = false;
      }
   }

   @WrapOperation(
      method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;")
   )
   private Vec3 vb$captureCollide(Entity self, Vec3 movement, Operation<Vec3> original) {
      Vec3 result = (Vec3)original.call(new Object[]{self, movement});
      if (this.vb$doBounce) {
         this.vb$postCollideMov = result;
         this.vb$xCollFromCollide = Math.abs(result.x) < Math.abs(movement.x) && !Mth.equal(movement.x, result.x);
         this.vb$zCollFromCollide = Math.abs(result.z) < Math.abs(movement.z) && !Mth.equal(movement.z, result.z);
      }

      return result;
   }

   @Inject(
      method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tryCheckInsideBlocks()V")
   )
   private void vb$injectBounce(MoverType type, Vec3 delta, CallbackInfo ci) {
      if (this.vb$doBounce && this.vb$postCollideMov != null) {
         if (this.verticalCollision || this.horizontalCollision) {
            Vec3 currentMovement = this.vb$preCollideVelocity != null ? this.vb$preCollideVelocity : this.getDeltaMovement();
            Entity var7 = (Entity)this;
            boolean suppress = var7 instanceof LivingEntity le && le.isSuppressingBounce();
            double restitution = suppress ? 0.0 : vb$getBounciness((Entity)this);
            Vec3 movementAfterBounce = currentMovement;
            if (this.vb$xCollFromCollide) {
               movementAfterBounce = movementAfterBounce.with(Axis.X, -currentMovement.x * restitution);
            }

            if (this.vb$zCollFromCollide) {
               movementAfterBounce = movementAfterBounce.with(Axis.Z, -currentMovement.z * restitution);
            }

            boolean bounced = restitution > 0.0 && (this.vb$xCollFromCollide || this.vb$zCollFromCollide);
            if (this.verticalCollision) {
               if (this.verticalCollisionBelow) {
                  restitution = !(-currentMovement.y < this.getEffectiveGravity()) && !suppress ? restitution : 0.0;
               }

               double gravityCompensation;
               double effectiveDrag;
               if (restitution > 0.0) {
                  double portionWithMovement = currentMovement.y != 0.0 ? this.vb$postCollideMov.y / currentMovement.y : 0.0;
                  gravityCompensation = portionWithMovement * this.getEffectiveGravity();
                  effectiveDrag = Mth.lerp(portionWithMovement, 1.0, vb$getAirDrag((Entity)this));
                  bounced = true;
               } else {
                  gravityCompensation = 0.0;
                  effectiveDrag = 1.0;
               }

               movementAfterBounce = movementAfterBounce.with(Axis.Y, (gravityCompensation - currentMovement.y) * effectiveDrag * restitution);
            }

            if (bounced) {
               this.gameEvent(GameEvent.HIT_GROUND);
            }

            this.setDeltaMovement(movementAfterBounce);
         }
      }
   }

   @Unique
   private static boolean vb$hasBounce(Entity entity) {
      if (!(entity instanceof LivingEntity le)) {
         return false;
      } else {
         AttributeInstance inst = le.getAttribute(ModAttributes.BOUNCINESS);
         return inst != null && inst.getValue() > 0.0;
      }
   }

   @Unique
   private static double vb$getBounciness(Entity entity) {
      if (entity instanceof LivingEntity le) {
         AttributeInstance inst = le.getAttribute(ModAttributes.BOUNCINESS);
         return inst != null ? inst.getValue() : 0.0;
      } else {
         return 0.0;
      }
   }

   @Unique
   private static double vb$getAirDrag(Entity entity) {
      if (entity instanceof LivingEntity living) {
         float airDragModifier = (float)living.getAttributeValue(ModAttributes.AIR_DRAG_MODIFIER);
         if (entity instanceof AirborneMoverEntity airborne && airborne.omnidirectionalAirMover()) {
            BlockPos posBelow = living.getBlockPosBelowThatAffectsMyMovement();
            float friction = living.onGround()
               ? computeModifiedFriction(
                  living.level().getBlockState(posBelow).getBlock().getFriction(), (float)living.getAttributeValue(ModAttributes.FRICTION_MODIFIER)
               )
               : 1.0F;
            float airDrag = computeModifiedFriction(0.91F, airDragModifier);
            return friction * airDrag;
         } else {
            return computeModifiedFriction(0.98F, airDragModifier);
         }
      } else {
         return 0.98;
      }
   }

   @Unique
   private static float computeModifiedFriction(float friction, float modifier) {
      return Mth.clamp(1.0F - (1.0F - friction) * modifier, 0.0F, 1.0F);
   }

   @Unique
   protected double getEffectiveGravity() {
      Entity isFalling = (Entity)this;
      if (isFalling instanceof LivingEntity living) {
         boolean isFallingx = this.getDeltaMovement().y <= 0.0;
         return isFallingx && living.hasEffect(MobEffects.SLOW_FALLING) ? 0.01 : 0.08;
      } else {
         return this.isNoGravity() ? 0.0 : 0.08;
      }
   }
}
