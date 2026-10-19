package com.bobmowzie.mowziesmobs.server.capability;

import com.bobmowzie.mowziesmobs.client.particle.ParticleCloud;
import com.bobmowzie.mowziesmobs.client.particle.ParticleSnowFlake;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrozenController;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public class FrozenData implements INBTSerializable<CompoundTag> {
   public static int MAX_FREEZE_DECAY_DELAY = 10;
   public boolean frozen;
   public float freezeProgress = 0.0F;
   public float frozenYaw;
   public float frozenPitch;
   public float frozenYawHead;
   public float frozenRenderYawOffset;
   public float frozenSwingProgress;
   public float frozenWalkAnimSpeed;
   public float frozenWalkAnimPosition;
   public boolean prevHasAI = true;
   public UUID prevAttackTarget;
   public int freezeDecayDelay;
   public boolean prevFrozen = false;
   public EntityFrozenController frozenController;

   public boolean getFrozen() {
      return this.frozen;
   }

   public float getFreezeProgress() {
      return this.freezeProgress;
   }

   public void setFreezeProgress(float freezeProgress) {
      this.freezeProgress = freezeProgress;
   }

   public float getFrozenYaw() {
      return this.frozenYaw;
   }

   public void setFrozenYaw(float frozenYaw) {
      this.frozenYaw = frozenYaw;
   }

   public float getFrozenPitch() {
      return this.frozenPitch;
   }

   public void setFrozenPitch(float frozenPitch) {
      this.frozenPitch = frozenPitch;
   }

   public float getFrozenYawHead() {
      return this.frozenYawHead;
   }

   public void setFrozenYawHead(float frozenYawHead) {
      this.frozenYawHead = frozenYawHead;
   }

   public float getFrozenRenderYawOffset() {
      return this.frozenRenderYawOffset;
   }

   public void setFrozenRenderYawOffset(float frozenRenderYawOffset) {
      this.frozenRenderYawOffset = frozenRenderYawOffset;
   }

   public float getFrozenSwingProgress() {
      return this.frozenSwingProgress;
   }

   public void setFrozenSwingProgress(float frozenSwingProgress) {
      this.frozenSwingProgress = frozenSwingProgress;
   }

   public float getFrozenWalkAnimSpeed() {
      return this.frozenWalkAnimSpeed;
   }

   public void setFrozenWalkAnimSpeed(float frozenWalkAnimPosition) {
      this.frozenWalkAnimSpeed = frozenWalkAnimPosition;
   }

   public float getFrozenWalkAnimPosition() {
      return this.frozenWalkAnimPosition;
   }

   public void setFrozenWalkAnimPosition(float frozenWalkAnimPosition) {
      this.frozenWalkAnimPosition = frozenWalkAnimPosition;
   }

   public boolean prevHasAI() {
      return this.prevHasAI;
   }

   public void setPrevHasAI(boolean prevHasAI) {
      this.prevHasAI = prevHasAI;
   }

   public int getFreezeDecayDelay() {
      return this.freezeDecayDelay;
   }

   public void setFreezeDecayDelay(int freezeDecayDelay) {
      this.freezeDecayDelay = freezeDecayDelay;
   }

   public boolean getPrevFrozen() {
      return this.prevFrozen;
   }

   public void setPrevFrozen(boolean prevFrozen) {
      this.prevFrozen = prevFrozen;
   }

   public UUID getPreAttackTarget() {
      return this.prevAttackTarget;
   }

   public void setPreAttackTarget(UUID livingEntity) {
      this.prevAttackTarget = livingEntity;
   }

   public EntityFrozenController getFrozenController() {
      return this.frozenController;
   }

   public void setFrozenController(EntityFrozenController frozenController) {
      this.frozenController = frozenController;
   }

   public void addFreezeProgress(LivingEntity entity, float amount) {
      if (!entity.level().isClientSide && !entity.hasEffect(EffectHandler.FROZEN)) {
         this.freezeProgress += amount;
         this.freezeDecayDelay = MAX_FREEZE_DECAY_DELAY;
      }
   }

   public void onFreeze(LivingEntity entity) {
      if (entity != null) {
         this.frozen = true;
         this.frozenController = new EntityFrozenController((EntityType<? extends EntityFrozenController>)EntityHandler.FROZEN_CONTROLLER.get(), entity.level());
         this.frozenController.absMoveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
         entity.level().addFreshEntity(this.frozenController);
         this.frozenController.setYBodyRot(entity.yBodyRot);
         this.frozenYaw = entity.getYRot();
         this.frozenPitch = entity.getXRot();
         this.frozenYawHead = entity.yHeadRot;
         this.frozenWalkAnimSpeed = entity.walkAnimation.speed();
         this.frozenWalkAnimPosition = entity.walkAnimation.position();
         this.frozenRenderYawOffset = entity.yBodyRot;
         this.frozenSwingProgress = entity.attackAnim;
         entity.startRiding(this.frozenController, true);
         entity.stopUsingItem();
         if (entity instanceof Mob mobEntity) {
            if (mobEntity.getTarget() != null) {
               this.setPreAttackTarget(mobEntity.getTarget().getUUID());
            }

            this.prevHasAI = !((Mob)entity).isNoAi();
            mobEntity.setNoAi(true);
         }

         if (entity.level().isClientSide) {
            int particleCount = (int)(10.0F + 1.0F * entity.getBbHeight() * entity.getBbWidth() * entity.getBbWidth());

            for (int i = 0; i < particleCount; i++) {
               double snowX = entity.getX() + entity.getBbWidth() * entity.getRandom().nextFloat() - entity.getBbWidth() / 2.0F;
               double snowZ = entity.getZ() + entity.getBbWidth() * entity.getRandom().nextFloat() - entity.getBbWidth() / 2.0F;
               double snowY = entity.getY() + entity.getBbHeight() * entity.getRandom().nextFloat();
               Vec3 motion = new Vec3(snowX - entity.getX(), snowY - (entity.getY() + entity.getBbHeight() / 2.0F), snowZ - entity.getZ()).normalize();
               entity.level().addParticle(new ParticleSnowFlake.Data(40.0F, false), snowX, snowY, snowZ, 0.1 * motion.x, 0.1 * motion.y, 0.1 * motion.z);
            }
         }

         entity.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_FROZEN_CRASH.get(), 1.0F, 1.0F);
      }
   }

   public void onUnfreeze(LivingEntity entity) {
      if (entity != null) {
         this.freezeProgress = 0.0F;
         if (this.frozen) {
            entity.removeEffectNoUpdate(EffectHandler.FROZEN);
            this.frozen = false;
            if (this.frozenController != null) {
               Vec3 oldPosition = entity.position();
               entity.stopRiding();
               entity.teleportTo(oldPosition.x(), oldPosition.y(), oldPosition.z());
               this.frozenController.discard();
            }

            entity.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_FROZEN_CRASH.get(), 1.0F, 0.5F);
            if (entity.level().isClientSide) {
               int particleCount = (int)(10.0F + 1.0F * entity.getBbHeight() * entity.getBbWidth() * entity.getBbWidth());

               for (int i = 0; i < particleCount; i++) {
                  double particleX = entity.getX() + entity.getBbWidth() * entity.getRandom().nextFloat() - entity.getBbWidth() / 2.0F;
                  double particleZ = entity.getZ() + entity.getBbWidth() * entity.getRandom().nextFloat() - entity.getBbWidth() / 2.0F;
                  double particleY = entity.getY() + entity.getBbHeight() * entity.getRandom().nextFloat() + 0.3F;
                  entity.level()
                     .addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.ICE.defaultBlockState()), particleX, particleY, particleZ, 0.0, 0.0, 0.0);
               }
            }

            if (entity instanceof Mob) {
               if (((Mob)entity).isNoAi() && this.prevHasAI) {
                  ((Mob)entity).setNoAi(false);
               }

               if (this.getPreAttackTarget() != null) {
                  Player target = entity.level().getPlayerByUUID(this.getPreAttackTarget());
                  if (target != null) {
                     ((Mob)entity).setTarget(target);
                  }
               }
            }
         }
      }
   }

   public void tick(LivingEntity entity) {
      if (this.getFreezeProgress() >= 1.0F && !entity.hasEffect(EffectHandler.FROZEN)) {
         entity.addEffect(new MobEffectInstance(EffectHandler.FROZEN, 50, 0, false, false));
         this.freezeProgress = 1.0F;
      } else if (this.freezeProgress > 0.0F) {
         entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 9, Mth.floor(this.freezeProgress * 5.0F + 1.0F), false, false));
      }

      if (this.frozenController == null) {
         Entity riding = entity.getVehicle();
         if (riding instanceof EntityFrozenController) {
            this.frozenController = (EntityFrozenController)riding;
         }
      }

      if (this.frozen) {
         entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 50, false, false));
         entity.setShiftKeyDown(false);
         if (entity.level().isClientSide && entity.tickCount % 2 == 0) {
            double cloudX = entity.getX() + entity.getBbWidth() * entity.getRandom().nextFloat() - entity.getBbWidth() / 2.0F;
            double cloudZ = entity.getZ() + entity.getBbWidth() * entity.getRandom().nextFloat() - entity.getBbWidth() / 2.0F;
            double cloudY = entity.getY() + entity.getBbHeight() * entity.getRandom().nextFloat();
            entity.level()
               .addParticle(
                  new ParticleCloud.Data(0.75F, 0.75F, 1.0F, 15.0F, 25, ParticleCloud.EnumCloudBehavior.CONSTANT, 1.0F),
                  cloudX,
                  cloudY,
                  cloudZ,
                  0.0,
                  -0.01F,
                  0.0
               );
            double snowX = entity.getX() + entity.getBbWidth() * entity.getRandom().nextFloat() - entity.getBbWidth() / 2.0F;
            double snowZ = entity.getZ() + entity.getBbWidth() * entity.getRandom().nextFloat() - entity.getBbWidth() / 2.0F;
            double snowY = entity.getY() + entity.getBbHeight() * entity.getRandom().nextFloat();
            entity.level().addParticle(new ParticleSnowFlake.Data(40.0F, false), snowX, snowY, snowZ, 0.0, -0.01, 0.0);
         }
      } else if (!entity.level().isClientSide && this.getPrevFrozen()) {
         this.onUnfreeze(entity);
      }

      if (this.freezeDecayDelay <= 0) {
         this.freezeProgress -= 0.1F;
         if (this.freezeProgress < 0.0F) {
            this.freezeProgress = 0.0F;
         }
      } else {
         this.freezeDecayDelay--;
      }

      this.prevFrozen = entity.hasEffect(EffectHandler.FROZEN);
   }

   public CompoundTag serializeNBT(@NotNull Provider lookup) {
      CompoundTag compound = new CompoundTag();
      compound.putFloat("freezeProgress", this.getFreezeProgress());
      compound.putInt("freezeDecayDelay", this.getFreezeDecayDelay());
      compound.putFloat("frozenWalkAnimSpeed", this.getFrozenWalkAnimSpeed());
      compound.putFloat("frozenWalkAnimPosition", this.getFrozenWalkAnimPosition());
      compound.putFloat("frozenRenderYawOffset", this.getFrozenRenderYawOffset());
      compound.putFloat("frozenSwingProgress", this.getFrozenSwingProgress());
      compound.putFloat("frozenPitch", this.getFrozenPitch());
      compound.putFloat("frozenYaw", this.getFrozenYaw());
      compound.putFloat("frozenYawHead", this.getFrozenYawHead());
      compound.putBoolean("prevHasAI", this.prevHasAI());
      if (this.getPreAttackTarget() != null) {
         compound.putUUID("prevAttackTarget", this.getPreAttackTarget());
      }

      compound.putBoolean("frozen", this.frozen);
      compound.putBoolean("prevFrozen", this.prevFrozen);
      return compound;
   }

   public void deserializeNBT(@NotNull Provider lookup, CompoundTag compound) {
      this.setFreezeProgress(compound.getFloat("freezeProgress"));
      this.setFreezeDecayDelay(compound.getInt("freezeDecayDelay"));
      this.setFrozenWalkAnimSpeed(compound.getFloat("frozenWalkAnimSpeed"));
      this.setFrozenWalkAnimPosition(compound.getFloat("frozenWalkAnimPosition"));
      this.setFrozenRenderYawOffset(compound.getFloat("frozenRenderYawOffset"));
      this.setFrozenSwingProgress(compound.getFloat("frozenSwingProgress"));
      this.setFrozenPitch(compound.getFloat("frozenPitch"));
      this.setFrozenYaw(compound.getFloat("frozenYaw"));
      this.setFrozenYawHead(compound.getFloat("frozenYawHead"));
      this.setPrevHasAI(compound.getBoolean("prevHasAI"));

      try {
         this.setPreAttackTarget(compound.getUUID("prevAttackTarget"));
      } catch (NullPointerException var4) {
      }

      this.frozen = compound.getBoolean("frozen");
      this.prevFrozen = compound.getBoolean("prevFrozen");
   }
}
