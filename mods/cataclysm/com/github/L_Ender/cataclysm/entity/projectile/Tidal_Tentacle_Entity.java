package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.entity.util.TidalTentacleUtil;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class Tidal_Tentacle_Entity extends Entity {
   private static final EntityDataAccessor<Optional<UUID>> CREATOR_ID = SynchedEntityData.defineId(
      Tidal_Tentacle_Entity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private static final EntityDataAccessor<Integer> FROM_ID = SynchedEntityData.defineId(Tidal_Tentacle_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> TARGET_COUNT = SynchedEntityData.defineId(Tidal_Tentacle_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> CURRENT_TARGET_ID = SynchedEntityData.defineId(Tidal_Tentacle_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Float> PROGRESS = SynchedEntityData.defineId(Tidal_Tentacle_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Tidal_Tentacle_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Boolean> RETRACTING = SynchedEntityData.defineId(Tidal_Tentacle_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> HAS_CLAW = SynchedEntityData.defineId(Tidal_Tentacle_Entity.class, EntityDataSerializers.BOOLEAN);
   private List<Entity> previouslyTouched = new ArrayList<>();
   private boolean hasChained = false;
   public float prevProgress = 0.0F;
   public static final float MAX_EXTEND_TIME = 5.0F;

   public Tidal_Tentacle_Entity(EntityType<?> type, Level level) {
      super(type, level);
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(CREATOR_ID, Optional.empty());
      p_326229_.define(FROM_ID, -1);
      p_326229_.define(TARGET_COUNT, 0);
      p_326229_.define(CURRENT_TARGET_ID, -1);
      p_326229_.define(PROGRESS, 0.0F);
      p_326229_.define(DAMAGE, 3.0F);
      p_326229_.define(RETRACTING, false);
      p_326229_.define(HAS_CLAW, true);
   }

   public void tick() {
      float progress = this.getProgress();
      this.prevProgress = progress;
      super.tick();
      Entity creator = this.getCreatorEntity();
      Entity current = this.getToEntity();
      if (this.tickCount == 1 && !this.level().isClientSide) {
         this.playSound((SoundEvent)ModSounds.TIDAL_TENTACLE.get(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
      }

      if (!this.isRetracting() && progress < 5.0F) {
         this.setProgress(progress + 1.0F);
      }

      if (this.isRetracting() && progress > 0.0F) {
         this.setProgress(progress - 1.0F);
      }

      if (this.isRetracting() && progress == 0.0F) {
         if (this.getFromEntity() instanceof Tidal_Tentacle_Entity tendonSegment) {
            tendonSegment.setRetracting(true);
            this.updateLastTendon(tendonSegment);
         } else {
            this.updateLastTendon(null);
         }

         this.remove(RemovalReason.DISCARDED);
      }

      if (creator instanceof LivingEntity && current != null) {
         Vec3 target = new Vec3(current.getX(), current.getY(0.4F), current.getZ());
         Vec3 lerp = target.subtract(this.position());
         this.setDeltaMovement(lerp.scale(0.5));
         if (!this.level().isClientSide && progress >= 5.0F && this.tickCount % 2 == 0) {
            Entity entity = this.getCreatorEntity();
            if (entity instanceof LivingEntity) {
               DamageSource damagesource = this.damageSources().mobProjectile(this, (LivingEntity)entity);
               if (current != creator && current.hurt(damagesource, this.getBaseDamage())) {
                  MobEffectInstance effectinstance1 = ((LivingEntity)current).getEffect(ModEffect.EFFECTABYSSAL_CURSE);
                  int i = 1;
                  if (effectinstance1 != null) {
                     i += effectinstance1.getAmplifier();
                     ((LivingEntity)current).removeEffectNoUpdate(ModEffect.EFFECTABYSSAL_CURSE);
                  } else {
                     i--;
                  }

                  i = Mth.clamp(i, 0, 4);
                  MobEffectInstance effectinstance = new MobEffectInstance(ModEffect.EFFECTABYSSAL_CURSE, 60, i, false, true, true);
                  ((LivingEntity)current).addEffect(effectinstance);
               }
            }
         }
      }

      Vec3 vector3d = this.getDeltaMovement();
      if (!this.level().isClientSide && !this.hasChained) {
         if (this.getTargetsHit() > 5) {
            this.setRetracting(true);
         } else if (creator instanceof LivingEntity && this.getProgress() >= 5.0F) {
            Entity closestValid = null;

            for (Entity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(12.0))) {
               if (!entity.equals(creator)
                  && !this.previouslyTouched.contains(entity)
                  && this.isValidTarget((LivingEntity)creator, entity)
                  && this.hasLineOfSight(entity)
                  && (closestValid == null || this.distanceTo(entity) < this.distanceTo(closestValid))) {
                  closestValid = entity;
               }
            }

            if (closestValid != null) {
               this.createChain(closestValid);
               this.hasChained = true;
            } else {
               this.setRetracting(true);
            }
         }
      }

      double d0 = this.getX() + vector3d.x;
      double d1 = this.getY() + vector3d.y;
      double d2 = this.getZ() + vector3d.z;
      this.setDeltaMovement(vector3d.scale(0.99F));
      this.setPos(d0, d1, d2);
   }

   private boolean isValidTarget(LivingEntity creator, Entity entity) {
      return !creator.isAlliedTo(entity) && !entity.isAlliedTo(creator) && entity instanceof Mob
         ? true
         : creator.getLastHurtMob() != null && creator.getLastHurtMob().getUUID().equals(entity.getUUID())
            || creator.getLastHurtByMob() != null && creator.getLastHurtByMob().getUUID().equals(entity.getUUID());
   }

   private boolean hasLineOfSight(Entity entity) {
      if (entity.level() != this.level()) {
         return false;
      }

      Vec3 vec3 = new Vec3(this.getX(), this.getEyeY(), this.getZ());
      Vec3 vec31 = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
      return vec31.distanceTo(vec3) > 128.0 ? false : this.level().clip(new ClipContext(vec3, vec31, Block.COLLIDER, Fluid.NONE, this)).getType() == Type.MISS;
   }

   private void updateLastTendon(Tidal_Tentacle_Entity lastTendon) {
      Entity creator = this.getCreatorEntity();
      if (creator == null) {
         creator = this.level().getPlayerByUUID(this.getCreatorEntityUUID());
      }

      if (creator instanceof LivingEntity) {
         TidalTentacleUtil.setLastTentacle((LivingEntity)creator, lastTendon);
      }
   }

   private void createChain(Entity closestValid) {
      this.entityData.set(HAS_CLAW, false);
      Tidal_Tentacle_Entity child = (Tidal_Tentacle_Entity)((EntityType)ModEntities.TIDAL_TENTACLE.get()).create(this.level());
      child.previouslyTouched = new ArrayList<>(this.previouslyTouched);
      child.previouslyTouched.add(closestValid);
      child.setCreatorEntityUUID(this.getCreatorEntityUUID());
      child.setFromEntityID(this.getId());
      child.setToEntityID(closestValid.getId());
      child.setPos(closestValid.getX(), closestValid.getY(0.4F), closestValid.getZ());
      child.setTargetsHit(this.getTargetsHit() + 1);
      this.updateLastTendon(child);
      this.level().addFreshEntity(child);
   }

   private float getBaseDamage() {
      return (Float)this.entityData.get(DAMAGE);
   }

   public UUID getCreatorEntityUUID() {
      return (UUID)((Optional)this.entityData.get(CREATOR_ID)).orElse(null);
   }

   public void setCreatorEntityUUID(UUID id) {
      this.entityData.set(CREATOR_ID, Optional.ofNullable(id));
   }

   public Entity getCreatorEntity() {
      UUID uuid = this.getCreatorEntityUUID();
      return uuid != null && !this.level().isClientSide ? ((ServerLevel)this.level()).getEntity(uuid) : null;
   }

   public int getFromEntityID() {
      return (Integer)this.entityData.get(FROM_ID);
   }

   public void setFromEntityID(int id) {
      this.entityData.set(FROM_ID, id);
   }

   public Entity getFromEntity() {
      return this.getFromEntityID() == -1 ? null : this.level().getEntity(this.getFromEntityID());
   }

   public int getToEntityID() {
      return (Integer)this.entityData.get(CURRENT_TARGET_ID);
   }

   public void setToEntityID(int id) {
      this.entityData.set(CURRENT_TARGET_ID, id);
   }

   public Entity getToEntity() {
      return this.getToEntityID() == -1 ? null : this.level().getEntity(this.getToEntityID());
   }

   public int getTargetsHit() {
      return (Integer)this.entityData.get(TARGET_COUNT);
   }

   public void setTargetsHit(int i) {
      this.entityData.set(TARGET_COUNT, i);
   }

   public float getProgress() {
      return (Float)this.entityData.get(PROGRESS);
   }

   public void setProgress(float progress) {
      this.entityData.set(PROGRESS, progress);
   }

   public boolean isRetracting() {
      return (Boolean)this.entityData.get(RETRACTING);
   }

   public void setRetracting(boolean retract) {
      this.entityData.set(RETRACTING, retract);
   }

   public boolean hasClaw() {
      return (Boolean)this.entityData.get(HAS_CLAW);
   }

   protected void readAdditionalSaveData(CompoundTag p_20052_) {
   }

   protected void addAdditionalSaveData(CompoundTag p_20139_) {
   }

   public boolean isCreator(Entity mob) {
      return this.getCreatorEntityUUID() != null && mob.getUUID().equals(this.getCreatorEntityUUID());
   }
}
