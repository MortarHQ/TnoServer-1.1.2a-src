package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class EnergizedFlame extends Entity implements TraceableEntity {
   private static final String TAG_OWNER = "owner";
   private static final double HEIGHT = 6.0;
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerId;

   public LivingEntity getOwner() {
      return this.owner;
   }

   public void setOwner(LivingEntity owner) {
      this.ownerId = owner.getUUID();
      this.owner = owner;
   }

   public EnergizedFlame(EntityType<? extends EnergizedFlame> type, Level level) {
      super(type, level);
   }

   protected void defineSynchedData(Builder builder) {
   }

   public boolean isOnFire() {
      return false;
   }

   public void tick() {
      super.tick();
      if (this.tickCount > 60) {
         this.discard();
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

         if (this.tickCount == 20) {
            this.playSound(SoundEvents.FIRECHARGE_USE, 1.0F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
         }

         if (this.tickCount > 20 && this.getOwner() != null) {
            AABB box = this.getBoundingBox().inflate(0.5, 0.0, 0.5);
            box = box.setMaxY(box.minY + 6.0);

            for (LivingEntity living : this.level().getEntitiesOfClass(LivingEntity.class, box)) {
               if (ESEntityUtil.shouldHarm(this.getOwner(), living)) {
                  living.hurt(ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.ENERGIZED_FLAME, this, this.getOwner()), 2.0F);
                  living.igniteForSeconds(3.0F);
               }
            }
         }
      } else {
         double dx = this.random.nextDouble() - 0.5;
         double dy = this.random.nextDouble() - 0.5;
         double dz = this.random.nextDouble() - 0.5;
         this.level().addParticle(GatheringTrailParticleOptions.ENERGY, this.getX() - dx, this.getY() - dy, this.getZ() - dz, dz * 0.25, 6.0, dx * 0.25);
         if (this.tickCount % 20 == 0 && this.tickCount >= 20) {
            this.level().addParticle(RingExplosionParticleOptions.ENERGY_SMALL, this.getX(), this.getY() + 0.12, this.getZ(), 0.0, 0.0, 0.0);
         }
      }
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         this.discard();
         return true;
      } else {
         return false;
      }
   }

   protected void readAdditionalSaveData(CompoundTag compoundTag) {
      if (compoundTag.hasUUID("owner")) {
         this.ownerId = compoundTag.getUUID("owner");
      }
   }

   protected void addAdditionalSaveData(CompoundTag compoundTag) {
      if (this.owner != null) {
         compoundTag.putUUID("owner", this.owner.getUUID());
      }
   }
}
