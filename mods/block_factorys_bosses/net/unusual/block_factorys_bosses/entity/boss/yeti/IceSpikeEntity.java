package net.unusual.block_factorys_bosses.entity.boss.yeti;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IceSpikeEntity extends Entity implements OwnableByAllEntity, GeoEntity, DangerZonesProvider {
   public static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(
      IceSpikeEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   public static final EntityDataAccessor<Integer> DATA_SPAWN_ANIM_TIME = SynchedEntityData.defineId(IceSpikeEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_SCALE = SynchedEntityData.defineId(IceSpikeEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_DELAY = SynchedEntityData.defineId(IceSpikeEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Boolean> DATA_EVIL = SynchedEntityData.defineId(IceSpikeEntity.class, EntityDataSerializers.BOOLEAN);
   public static final int SPAWN_ANIMATION_DURATION = 11;
   public static final int DEFAULT_SCALE = 4;
   public static final int SPAWN_DELAY_DEFAULT = 4;
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   private final DangerZonesProvider.DangerZone zone;
   private float damage = 10.0F;

   public IceSpikeEntity(EntityType<IceSpikeEntity> type, Level world) {
      super(type, world);
      this.zone = new DangerZonesProvider.DangerZone();
      this.zone.setOffset(new Vector3f(0.0F, 0.0F, 0.0F));
      this.scaleZone();
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_OWNER_UUID, Optional.empty());
      builder.define(DATA_SPAWN_ANIM_TIME, 0);
      builder.define(DATA_SCALE, 4);
      builder.define(DATA_DELAY, 16);
      builder.define(DATA_EVIL, false);
   }

   private void scaleZone() {
      float size = this.getDimensions(Pose.STANDING).width();
      this.zone.setSize(size, size);
   }

   public AABB getBoundingBoxForCulling() {
      return super.getBoundingBoxForCulling().inflate(3.0);
   }

   @Nullable
   public UUID getOwnerUUID() {
      return (UUID)((Optional)this.entityData.get(DATA_OWNER_UUID)).orElse(null);
   }

   public void setOwnerUUID(@Nullable UUID uuid) {
      this.entityData.set(DATA_OWNER_UUID, Optional.ofNullable(uuid));
   }

   public int getScale() {
      return (Integer)this.entityData.get(DATA_SCALE);
   }

   public void setScale(int scale) {
      if (scale < 1) {
         scale = 1;
      } else if (scale > 4) {
         scale = 4;
      }

      this.entityData.set(DATA_SCALE, scale);
   }

   public int getDelay() {
      return (Integer)this.entityData.get(DATA_DELAY);
   }

   public void setDelay(int scale) {
      this.entityData.set(DATA_DELAY, scale);
   }

   public boolean getEvil() {
      return (Boolean)this.entityData.get(DATA_EVIL);
   }

   public void setEvil(boolean evil) {
      this.entityData.set(DATA_EVIL, evil);
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public float getDamage() {
      return this.damage;
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      if (DATA_SCALE.equals(key)) {
         this.refreshDimensions();
         this.scaleZone();
      }

      super.onSyncedDataUpdated(key);
   }

   public EntityDimensions getDimensions(Pose pose) {
      float size = switch (this.getScale()) {
         case 1 -> 0.4F;
         case 2 -> 0.6F;
         case 3 -> 0.8F;
         default -> 1.0F;
      };
      return super.getDimensions(pose).scale(size);
   }

   public boolean ignoreExplosion(Explosion explosion) {
      return true;
   }

   public boolean fireImmune() {
      return true;
   }

   public void remove(RemovalReason reason) {
      if (reason == RemovalReason.KILLED) {
         BlockPos pos = BlockPos.containing(this.position());
         if (this.random.nextInt(10) == 0) {
            this.level().playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 0.5F, 0.5F);
         }

         this.level().levelEvent(2001, pos, Block.getId(((Block)BossesRiseBlocks.ICE_BLOCK_PARTICULES.get()).defaultBlockState()));
      }

      super.remove(reason);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      tag.putInt("spawn_animation", (Integer)this.entityData.get(DATA_SPAWN_ANIM_TIME));
      if (this.getOwnerUUID() != null) {
         tag.putUUID("owner", this.getOwnerUUID());
      }

      tag.putInt("scale", (Integer)this.entityData.get(DATA_SCALE));
      tag.putInt("delay", (Integer)this.entityData.get(DATA_DELAY));
      tag.putBoolean("evil", (Boolean)this.entityData.get(DATA_EVIL));
      tag.putInt("age", this.tickCount);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      this.entityData.set(DATA_SPAWN_ANIM_TIME, tag.getInt("spawn_animation"));
      if (tag.hasUUID("owner")) {
         this.setOwnerUUID(tag.getUUID("owner"));
      }

      this.entityData.set(DATA_SCALE, tag.getInt("scale"));
      this.entityData.set(DATA_DELAY, tag.getInt("delay"));
      this.entityData.set(DATA_EVIL, tag.getBoolean("evil"));
      this.tickCount = tag.getInt("age");
   }

   public void baseTick() {
      if (this.firstTick && this.level() instanceof ServerLevel level) {
         level.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), this.getScale() * 2, 0.5, 0.5, 0.5, 0.0);
         level.getEntities(this, this.getBoundingBox(), entity1 -> canHurt(entity1, this)).forEach(entity -> {
            if (entity instanceof LivingEntity living) {
               living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
            }
         });
      }

      super.baseTick();
      if (this.level() instanceof ServerLevel level) {
         if (this.tickCount >= this.getDelay()) {
            if (this.tickCount == this.getDelay()) {
               this.playSound((SoundEvent)BossesRiseSounds.ICICLE_SPAWN.value(), 0.15F, 1.0F + this.random.nextFloat() * 0.25F);
               level.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), this.getScale() * 2, 0.5, 0.5, 0.5, 0.0);
               level.getEntities(this, this.getBoundingBox(), entity1 -> canHurt(entity1, this)).forEach(entity -> {
                  entity.hurt(level.damageSources().freeze(), this.damage);
                  entity.setTicksFrozen(200);
               });
            }

            int spawnTime = (Integer)this.getEntityData().get(DATA_SPAWN_ANIM_TIME);
            this.getEntityData().set(DATA_SPAWN_ANIM_TIME, spawnTime + 1);
            if (spawnTime == 11) {
               this.discard();
               level.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), this.getScale(), 0.25, 0.25, 0.25, 0.0);
            }
         }
      }
   }

   public static <T extends Entity & OwnableByAllEntity> boolean canHurt(Entity target, T iceSpike) {
      if (iceSpike.getOwner() == target) {
         return false;
      } else {
         return target instanceof OwnableEntity ownable && ownable.getOwnerUUID() == iceSpike.getOwnerUUID()
            ? false
            : target.isAlive() && !target.isSpectator() && !(target instanceof IceSpikeEntity) && !(target instanceof IceSpikeClusterEntity);
      }
   }

   public boolean canFreeze() {
      return false;
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }

   @Override
   public Collection<DangerZonesProvider.DangerZone> getDangerZones() {
      return this.tickCount < this.getDelay() ? List.of(this.zone) : List.of();
   }
}
