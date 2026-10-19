package it.hurts.sskirillss.relics.entities;

import it.hurts.octostudios.octolib.module.particle.trail.EntityTrailProvider;
import it.hurts.sskirillss.relics.entities.misc.ITargetableEntity;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.sync.S2CEntityTargetPacket;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class LifeEssenceEntity extends ThrowableProjectile implements ITargetableEntity {
   private static final EntityDataAccessor<Float> HEAL = SynchedEntityData.defineId(LifeEssenceEntity.class, EntityDataSerializers.FLOAT);
   private LivingEntity target;

   public void setHeal(float heal) {
      this.getEntityData().set(HEAL, heal);
   }

   public float getHeal() {
      return (Float)this.getEntityData().get(HEAL);
   }

   public LifeEssenceEntity(EntityType<? extends LifeEssenceEntity> type, Level worldIn) {
      super(type, worldIn);
   }

   public void tick() {
      super.tick();
      Level level = this.getCommandSenderWorld();
      level.addParticle(
         ParticleUtils.constructSimpleSpark(new Color(200 + this.random.nextInt(50), 150 + this.random.nextInt(50), this.random.nextInt(50)), 0.05F, 10, 0.9F),
         this.xOld,
         this.yOld,
         this.zOld,
         -this.getDeltaMovement().x * 0.1F * this.random.nextFloat(),
         -this.getDeltaMovement().y * 0.1F * this.random.nextFloat(),
         -this.getDeltaMovement().z * 0.1F * this.random.nextFloat()
      );
      if (this.target != null && !this.target.isDeadOrDying() && this.tickCount < 250) {
         if (this.position().distanceTo(this.target.getEyePosition()) > 1.0) {
            Vec3 direction = this.target.getEyePosition().subtract(this.position()).normalize();
            Vec3 motion = this.getDeltaMovement();
            int window = 5;
            if (this.tickCount > window) {
               float factor = Math.clamp((this.tickCount - window) * 0.1F, 0.0F, 1.0F);
               this.setDeltaMovement(
                  motion.x + (direction.x * factor - motion.x) * factor,
                  motion.y + (direction.y * factor - motion.y) * factor,
                  motion.z + (direction.z * factor - motion.z) * factor
               );
            }
         } else {
            this.target.heal(this.getHeal());
            this.discard();
         }
      } else {
         if (!level.isClientSide()) {
            this.discard();
         }
      }
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(HEAL, 0.0F);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putFloat("heal", this.getHeal());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setHeal(tag.getFloat("heal"));
   }

   public boolean isNoGravity() {
      return false;
   }

   @Nullable
   @Override
   public LivingEntity getTarget() {
      return this.target;
   }

   @Override
   public void setTarget(LivingEntity target) {
      this.target = target;
      if (!this.level().isClientSide() && target != null) {
         NetworkHandler.sendToClientsTrackingEntity(new S2CEntityTargetPacket(this.getId(), target.getId()), this);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class TrailProvider extends EntityTrailProvider<LifeEssenceEntity> {
      public TrailProvider(LifeEssenceEntity entity) {
         super(entity);
      }

      public Vec3 getTrailPosition(float partialTicks) {
         return ((LifeEssenceEntity)this.entity).getPosition(partialTicks).add(((LifeEssenceEntity)this.entity).getDeltaMovement().scale(-1.0));
      }

      public int getTrailUpdateFrequency() {
         return 1;
      }

      public boolean isTrailAlive() {
         return ((LifeEssenceEntity)this.entity).isAlive();
      }

      public boolean isTrailGrowing() {
         return ((LifeEssenceEntity)this.entity).tickCount > 2;
      }

      public int getTrailMaxLength() {
         return 5;
      }

      public int getTrailFadeInColor() {
         return -256;
      }

      public int getTrailFadeOutColor() {
         return -2130771968;
      }

      public double getTrailScale() {
         return 0.025F;
      }
   }
}
