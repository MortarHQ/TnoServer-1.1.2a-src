package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.Easing;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LunarThorn extends Entity implements TraceableEntity {
   private static final String TAG_ATTACK_MODE = "attack_mode";
   private static final String TAG_OWNER = "owner";
   private static final String TAG_SPAWNED_TICKS = "spawned_ticks";
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerId;
   protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(LunarThorn.class, EntityDataSerializers.INT);
   private int attackMode;
   public float oldClientScale = 0.0F;
   public float clientScale = 0.0F;

   public LivingEntity getOwner() {
      return this.owner;
   }

   public void setOwner(LivingEntity owner) {
      this.ownerId = owner.getUUID();
      this.owner = owner;
   }

   public int getSpawnedTicks() {
      return (Integer)this.getEntityData().get(SPAWNED_TICKS);
   }

   public void setSpawnedTicks(int spawnedTicks) {
      this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
   }

   public int getAttackMode() {
      return this.attackMode;
   }

   public void setAttackMode(int attackMode) {
      this.attackMode = attackMode;
   }

   public LunarThorn(EntityType<? extends LunarThorn> type, Level level) {
      super(type, level);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(SPAWNED_TICKS, 0);
   }

   public boolean isOnFire() {
      return false;
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.level() instanceof ServerLevel serverLevel && this.owner == null && this.ownerId != null) {
            if (serverLevel.getEntity(this.ownerId) instanceof LivingEntity livingEntity) {
               this.owner = livingEntity;
            }

            if (this.owner == null) {
               this.ownerId = null;
            }
         }

         if (this.getSpawnedTicks() >= 200) {
            this.discard();
         }

         if (this.getSpawnedTicks() > 40 && this.getOwner() != null) {
            for (LivingEntity livingEntity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.5))) {
               if (ESEntityUtil.shouldHarm(this.getOwner(), livingEntity)) {
                  if (this.getAttackMode() == 1 && !livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                     livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10));
                  }

                  livingEntity.hurt(
                     ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.POISON, this, this.getOwner()),
                     this.getAttackMode() == 0 ? 4.0F : 3.0F
                  );
               }
            }
         }

         this.setSpawnedTicks(this.getSpawnedTicks() + 1);
      } else {
         float scale = this.getSpawnedTicks() <= 40 ? Easing.OUT_QUART.calculate(this.getSpawnedTicks() / 40.0F) : 1.0F;
         if (this.getSpawnedTicks() >= 160) {
            scale = Easing.OUT_QUART.calculate((200 - this.getSpawnedTicks()) / 40.0F);
         }

         this.oldClientScale = this.clientScale;
         this.clientScale = scale;
         if (this.random.nextInt(20) == 0) {
            this.level().addParticle(ESSmokeParticleOptions.LUNAR_SHORT, this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0);
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

      this.setSpawnedTicks(compoundTag.getInt("spawned_ticks"));
      this.setAttackMode(compoundTag.getInt("attack_mode"));
   }

   protected void addAdditionalSaveData(CompoundTag compoundTag) {
      if (this.owner != null) {
         compoundTag.putUUID("owner", this.owner.getUUID());
      }

      compoundTag.putInt("spawned_ticks", this.getSpawnedTicks());
      compoundTag.putInt("attack_mode", this.getAttackMode());
   }
}
