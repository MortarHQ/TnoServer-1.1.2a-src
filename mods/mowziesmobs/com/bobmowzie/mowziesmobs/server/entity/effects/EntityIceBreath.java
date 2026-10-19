package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.particle.ParticleCloud;
import com.bobmowzie.mowziesmobs.client.particle.ParticleRing;
import com.bobmowzie.mowziesmobs.client.particle.ParticleSnowFlake;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenData;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class EntityIceBreath extends EntityMagicEffect {
   private static final int RANGE = 10;
   private static final int ARC = 45;
   private static final int DAMAGE_PER_HIT = 1;

   public EntityIceBreath(EntityType<? extends EntityIceBreath> type, Level world) {
      super(type, world);
   }

   public EntityIceBreath(EntityType<? extends EntityIceBreath> type, Level world, LivingEntity caster) {
      super(type, world, caster);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.tickCount == 1 && this.level().isClientSide) {
         MMCommon.PROXY.playIceBreathSound(this);
      }

      if (this.tickCount > 1 && this.getCaster() == null) {
         this.discard();
      }

      if (this.getCaster() != null && !this.getCaster().isAlive()) {
         this.discard();
      }

      if (this.tickCount == 1) {
         this.playSound((SoundEvent)MMSounds.ENTITY_FROSTMAW_ICEBREATH_START.get(), 1.0F, 0.6F);
      }

      if (this.getCaster() instanceof Player player) {
         this.absMoveTo(player.getX(), player.getEyeY() - 0.5, player.getZ(), player.getYRot(), player.getXRot());
         AbilityData abilityData = DataHandler.getData(player, DataHandler.ABILITY_DATA);
         if (!abilityData.getAbilityFromType(AbilityHandler.ICE_BREATH_ABILITY).isUsing()) {
            this.discard();
         }
      }

      float yaw = (float)Math.toRadians(-this.getYRot());
      float pitch = (float)Math.toRadians(-this.getXRot());
      float spread = 0.25F;
      float speed = 0.56F;
      float xComp = (float)(Math.sin(yaw) * Math.cos(pitch));
      float yComp = (float)Math.sin(pitch);
      float zComp = (float)(Math.cos(yaw) * Math.cos(pitch));
      if (this.level().isClientSide) {
         Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
         boolean overrideLimiter = camera.getPosition().distanceToSqr(this.getX(), this.getY(), this.getZ()) < 4096.0;
         if (this.tickCount % 8 == 0) {
            this.level()
               .addAlwaysVisibleParticle(
                  new ParticleRing.Data(yaw, -pitch, 40.0F, 1.0F, 1.0F, 1.0F, 1.0F, (int)(110.0F * spread), false, ParticleRing.EnumRingBehavior.GROW),
                  overrideLimiter,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  0.5F * xComp,
                  0.5F * yComp,
                  0.5F * zComp
               );
         }

         for (int i = 0; i < 6; i++) {
            double xSpeed = speed * 1.0F * xComp;
            double ySpeed = speed * 1.0F * yComp;
            double zSpeed = speed * 1.0F * zComp;
            this.level().addParticle(new ParticleSnowFlake.Data(37.0F, true), this.getX(), this.getY(), this.getZ(), xSpeed, ySpeed, zSpeed);
         }

         for (int i = 0; i < 5; i++) {
            double xSpeed = speed * xComp + spread * 0.7 * (this.random.nextFloat() * 2.0F - 1.0F) * Math.sqrt(1.0F - xComp * xComp);
            double ySpeed = speed * yComp + spread * 0.7 * (this.random.nextFloat() * 2.0F - 1.0F) * Math.sqrt(1.0F - yComp * yComp);
            double zSpeed = speed * zComp + spread * 0.7 * (this.random.nextFloat() * 2.0F - 1.0F) * Math.sqrt(1.0F - zComp * zComp);
            float value = this.random.nextFloat() * 0.15F;
            this.level()
               .addAlwaysVisibleParticle(
                  new ParticleCloud.Data(
                     0.75F + value, 0.75F + value, 1.0F, 10.0F + this.random.nextFloat() * 20.0F, 40, ParticleCloud.EnumCloudBehavior.GROW, 1.0F
                  ),
                  overrideLimiter,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  xSpeed,
                  ySpeed,
                  zSpeed
               );
         }
      }

      if (this.tickCount > 10) {
         this.hitEntities();
      }

      if (this.tickCount > 10) {
         this.freezeBlocks();
      }

      if (this.tickCount > 65 && !(this.getCaster() instanceof Player)) {
         this.discard();
      }
   }

   public void hitEntities() {
      List<Entity> entitiesHit = this.getEntityLivingBaseNearby(10.0, 10.0, 10.0, 10.0);
      float damage = 1.0F;
      if (this.getCaster() instanceof EntityFrostmaw) {
         damage = (float)(damage * (Double)ConfigHandler.COMMON.MOBS.FROSTMAW.combatConfig.attackMultiplier.get());
      }

      if (this.getCaster() instanceof Player) {
         damage = (float)(damage * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.attackMultiplier.get());
      }

      for (Entity entityHit : entitiesHit) {
         if (!(entityHit instanceof ItemEntity)
            && entityHit != this.getCaster()
            && !(entityHit instanceof ItemEntity)
            && !entityHit.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
            && !(entityHit instanceof EnderDragon)) {
            float entityHitYaw = (float)((Math.atan2(entityHit.getZ() - this.getZ(), entityHit.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
            float entityAttackingYaw = this.getYRot() % 360.0F;
            if (entityHitYaw < 0.0F) {
               entityHitYaw += 360.0F;
            }

            if (entityAttackingYaw < 0.0F) {
               entityAttackingYaw += 360.0F;
            }

            float entityRelativeYaw = entityHitYaw - entityAttackingYaw;
            float xzDistance = (float)Math.sqrt(
               (entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ()) + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX())
            );
            double hitY = entityHit.getY() + entityHit.getBbHeight() / 2.0;
            float entityHitPitch = (float)(Math.atan2(hitY - this.getY(), xzDistance) * (180.0 / Math.PI) % 360.0);
            float entityAttackingPitch = -this.getXRot() % 360.0F;
            if (entityHitPitch < 0.0F) {
               entityHitPitch += 360.0F;
            }

            if (entityAttackingPitch < 0.0F) {
               entityAttackingPitch += 360.0F;
            }

            float entityRelativePitch = entityHitPitch - entityAttackingPitch;
            float entityHitDistance = (float)Math.sqrt(
               (entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ())
                  + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX())
                  + (hitY - this.getY()) * (hitY - this.getY())
            );
            boolean inRange = entityHitDistance <= 10.0F;
            boolean yawCheck = entityRelativeYaw <= 22.5F && entityRelativeYaw >= -22.5F || entityRelativeYaw >= 337.5F || entityRelativeYaw <= -337.5F;
            boolean pitchCheck = entityRelativePitch <= 22.5F && entityRelativePitch >= -22.5F
               || entityRelativePitch >= 337.5F
               || entityRelativePitch <= -337.5F;
            boolean frostmawCloseCheck = this.getCaster() instanceof EntityFrostmaw && entityHitDistance <= 2.0F;
            if ((inRange && yawCheck && pitchCheck || frostmawCloseCheck)
               && this.raytraceCheckEntity(entityHit)
               && entityHit.hurt(this.damageSources().freeze(), damage)
               && entityHit instanceof LivingEntity) {
               entityHit.setDeltaMovement(entityHit.getDeltaMovement().multiply(0.25, 1.0, 0.25));
               DataHandler.<FrozenData>getData(entityHit, DataHandler.FROZEN_DATA).addFreezeProgress((LivingEntity)entityHit, 0.23F);
            }
         }
      }
   }

   public void freezeBlocks() {
      int checkDist = 10;

      for (int i = (int)this.getX() - checkDist; i < (int)this.getX() + checkDist; i++) {
         for (int j = (int)this.getY() - checkDist; j < (int)this.getY() + checkDist; j++) {
            for (int k = (int)this.getZ() - checkDist; k < (int)this.getZ() + checkDist; k++) {
               BlockPos pos = new BlockPos(i, j, k);
               BlockState blockState = this.level().getBlockState(pos);
               BlockState blockStateAbove = this.level().getBlockState(pos.above());
               if (blockState.getBlock() == Blocks.WATER && blockStateAbove.getBlock() == Blocks.AIR) {
                  float blockHitYaw = (float)((Math.atan2(pos.getZ() - this.getZ(), pos.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
                  float entityAttackingYaw = this.getYRot() % 360.0F;
                  if (blockHitYaw < 0.0F) {
                     blockHitYaw += 360.0F;
                  }

                  if (entityAttackingYaw < 0.0F) {
                     entityAttackingYaw += 360.0F;
                  }

                  float blockRelativeYaw = blockHitYaw - entityAttackingYaw;
                  float xzDistance = (float)Math.sqrt(
                     (pos.getZ() - this.getZ()) * (pos.getZ() - this.getZ()) + (pos.getX() - this.getX()) * (pos.getX() - this.getX())
                  );
                  float blockHitPitch = (float)(Math.atan2(pos.getY() - this.getY(), xzDistance) * (180.0 / Math.PI) % 360.0);
                  float entityAttackingPitch = -this.getXRot() % 360.0F;
                  if (blockHitPitch < 0.0F) {
                     blockHitPitch += 360.0F;
                  }

                  if (entityAttackingPitch < 0.0F) {
                     entityAttackingPitch += 360.0F;
                  }

                  float blockRelativePitch = blockHitPitch - entityAttackingPitch;
                  float blockHitDistance = (float)Math.sqrt(
                     (pos.getZ() - this.getZ()) * (pos.getZ() - this.getZ())
                        + (pos.getX() - this.getX()) * (pos.getX() - this.getX())
                        + (pos.getY() - this.getY()) * (pos.getY() - this.getY())
                  );
                  boolean inRange = blockHitDistance <= 10.0F;
                  boolean yawCheck = blockRelativeYaw <= 22.5F && blockRelativeYaw >= -22.5F || blockRelativeYaw >= 337.5F || blockRelativeYaw <= -337.5F;
                  boolean pitchCheck = blockRelativePitch <= 22.5F && blockRelativePitch >= -22.5F
                     || blockRelativePitch >= 337.5F
                     || blockRelativePitch <= -337.5F;
                  if (inRange && yawCheck && pitchCheck) {
                     EntityBlockSwapper.swapBlock(this.level(), pos, Blocks.ICE.defaultBlockState(), 140, false, false);
                  }
               }
            }
         }
      }
   }

   public List<Entity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(Entity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return this.level()
         .getEntitiesOfClass(
            entityClass,
            this.getBoundingBox().inflate(dX, dY, dZ),
            e -> e != this && this.distanceTo(e) <= r + e.getBbWidth() / 2.0F && e.getY() <= this.getY() + dY
         );
   }
}
