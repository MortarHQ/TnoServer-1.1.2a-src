package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PermafrostCloud extends Entity implements TraceableEntity {
   private static final String TAG_OWNER = "owner";
   private static final String TAG_SMALL = "small";
   protected static final EntityDataAccessor<Boolean> SMALL = SynchedEntityData.defineId(PermafrostCloud.class, EntityDataSerializers.BOOLEAN);
   private int attackCooldown;
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerId;

   public boolean isSmall() {
      return (Boolean)this.getEntityData().get(SMALL);
   }

   public void setSmall(boolean small) {
      this.getEntityData().set(SMALL, small);
      this.refreshDimensions();
   }

   public LivingEntity getOwner() {
      return this.owner;
   }

   public void setOwner(LivingEntity owner) {
      this.ownerId = owner.getUUID();
      this.owner = owner;
   }

   public PermafrostCloud(EntityType<? extends PermafrostCloud> type, Level level) {
      super(type, level);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(SMALL, false);
   }

   public boolean isOnFire() {
      return false;
   }

   public void tick() {
      super.tick();
      if (this.tickCount > 200) {
         this.discard();
      }

      if (this.tickCount % 3 == 0) {
         this.refreshDimensions();
      }

      if (!this.level().isClientSide) {
         if (this.level() instanceof ServerLevel serverLevel && this.owner == null && this.ownerId != null) {
            if (serverLevel.getEntity(this.ownerId) instanceof LivingEntity livingEntity) {
               this.owner = livingEntity;
            }

            if (this.owner == null) {
               this.ownerId = null;
            }
         }

         if (this.attackCooldown > 0) {
            this.attackCooldown--;
         }

         if (this.getOwner() != null && this.attackCooldown <= 0) {
            boolean success = false;

            for (LivingEntity livingEntity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox())) {
               if (ESEntityUtil.shouldHarm(this.getOwner(), livingEntity)) {
                  int oldInvulnerableTime = livingEntity.invulnerableTime;
                  livingEntity.invulnerableTime = 0;
                  success = livingEntity.hurt(ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.FREEZE, this, this.getOwner()), 4.0F)
                     || success;
                  livingEntity.invulnerableTime = oldInvulnerableTime;
                  if (livingEntity.canFreeze()) {
                     livingEntity.setTicksFrozen(Math.min(livingEntity.getTicksFrozen() + 8, 300));
                  }
               }
            }

            if (success) {
               this.attackCooldown = 10;
            }
         }
      } else {
         int count = Mth.ceil(Math.PI * (this.getBbWidth() / 2.0F) * (this.getBbWidth() / 2.0F));
         float bbWidth = this.getBbWidth();

         for (int i = 0; i < count; i++) {
            float angle = this.random.nextFloat() * (float) (Math.PI * 2);
            float scale = Mth.sqrt(this.random.nextFloat()) * bbWidth;
            double x = this.getX() + Mth.cos(angle) * scale;
            double y = this.getY();
            double z = this.getZ() + Mth.sin(angle) * scale;
            this.level().addAlwaysVisibleParticle(ParticleTypes.SNOWFLAKE, x, y, z, 0.0, 0.0, 0.0);
         }
      }
   }

   public EntityDimensions getDimensions(Pose pose) {
      return this.isSmall() ? super.getDimensions(pose).scale(0.5F, 1.0F) : super.getDimensions(pose);
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         this.discard();
         return true;
      } else {
         return false;
      }
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      if (tag.hasUUID("owner")) {
         this.ownerId = tag.getUUID("owner");
      }

      this.setSmall(tag.getBoolean("small"));
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      if (this.owner != null) {
         tag.putUUID("owner", this.owner.getUUID());
      }

      tag.putBoolean("small", this.isSmall());
   }
}
