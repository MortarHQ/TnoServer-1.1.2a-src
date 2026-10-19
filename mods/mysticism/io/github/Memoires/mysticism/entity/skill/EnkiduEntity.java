package io.github.Memoires.mysticism.entity.skill;

import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EnkiduEntity extends Entity implements GeoEntity {
   private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(EnkiduEntity.class, EntityDataSerializers.OPTIONAL_UUID);
   private static final EntityDataAccessor<Boolean> DESPAWNING = SynchedEntityData.defineId(EnkiduEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(EnkiduEntity.class, EntityDataSerializers.FLOAT);
   private static final int DEFAULT_LIFESPAN = 600;
   private static final int START_ANIM_TICKS = 60;
   private static final int END_ANIM_TICKS = 64;
   private static final RawAnimation START_ANIM = RawAnimation.begin().thenPlay("animation.enkidu_chain.multi_chain_restraint_start");
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.enkidu_chain.multi_chain_restraint_idle");
   private static final RawAnimation END_ANIM = RawAnimation.begin().thenPlay("animation.enkidu_chain.multi_chain_restraint_end");
   private final AnimatableInstanceCache cache;
   private int lifespan = 600;
   private int age = 0;
   private int animTick = 0;
   private int despawnAnimTick = 0;

   public EnkiduEntity(EntityType<? extends Entity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.cache = GeckoLibUtil.createInstanceCache(this);
      this.noPhysics = true;
      this.setNoGravity(true);
   }

   public EnkiduEntity(Level pLevel, @Nullable LivingEntity pOwner) {
      this((EntityType<? extends Entity>)MysticismSkillEntityTypes.ENKIDU.get(), pLevel);
      this.setOwner(pOwner);
      this.noPhysics = true;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(OWNER_UUID, Optional.empty());
      builder.define(DESPAWNING, false);
      builder.define(SCALE, 1.0F);
   }

   public void setOwner(@Nullable LivingEntity owner) {
      if (owner != null) {
         this.entityData.set(OWNER_UUID, Optional.of(owner.getUUID()));
         this.setPos(owner.getX(), owner.getY(), owner.getZ());
         this.entityData.set(SCALE, owner.getScale());
      } else {
         this.entityData.set(OWNER_UUID, Optional.empty());
         this.entityData.set(SCALE, 1.0F);
      }

      this.refreshDimensions();
   }

   @Nullable
   public UUID getOwnerUUID() {
      return (UUID)((Optional)this.entityData.get(OWNER_UUID)).orElse(null);
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.level() instanceof ServerLevel serverLevel) {
         UUID uuid = this.getOwnerUUID();
         if (uuid != null && serverLevel.getEntity(uuid) instanceof LivingEntity livingEntity) {
            return livingEntity;
         }
      }

      return null;
   }

   public void setLifespan(int lifespanTicks) {
      this.lifespan = lifespanTicks;
   }

   public int getLifespan() {
      return this.lifespan;
   }

   public int getAge() {
      return this.age;
   }

   public boolean isDespawning() {
      return (Boolean)this.entityData.get(DESPAWNING);
   }

   public float getScale() {
      return (Float)this.entityData.get(SCALE);
   }

   public EntityDimensions getDimensions(Pose pose) {
      return super.getDimensions(pose).scale(this.getScale());
   }

   public void tick() {
      super.tick();
      this.animTick++;
      if (!this.level().isClientSide) {
         LivingEntity owner = this.getOwner();
         boolean ownerMissing = owner == null && this.getOwnerUUID() != null;
         if (owner != null) {
            this.setPos(owner.getX(), owner.getY(), owner.getZ());
            this.setDeltaMovement(Vec3.ZERO);
            float ownerScale = owner.getScale();
            if (Math.abs(ownerScale - (Float)this.entityData.get(SCALE)) > 0.01F) {
               this.entityData.set(SCALE, ownerScale);
               this.refreshDimensions();
            }
         }

         if (!(Boolean)this.entityData.get(DESPAWNING)) {
            this.age++;
            boolean shouldStartDespawn = ownerMissing || this.age >= this.lifespan - 64;
            if (shouldStartDespawn) {
               this.entityData.set(DESPAWNING, true);
            }
         } else {
            this.despawnAnimTick++;
            if (this.despawnAnimTick >= 64) {
               this.discard();
            }
         }
      }
   }

   protected void readAdditionalSaveData(CompoundTag compoundTag) {
      if (compoundTag.hasUUID("Owner")) {
         this.entityData.set(OWNER_UUID, Optional.of(compoundTag.getUUID("Owner")));
      }

      if (compoundTag.contains("Lifespan")) {
         this.lifespan = compoundTag.getInt("Lifespan");
      }

      if (compoundTag.contains("Scale")) {
         this.entityData.set(SCALE, compoundTag.getFloat("Scale"));
      }

      this.age = compoundTag.getInt("Age");
      this.refreshDimensions();
   }

   protected void addAdditionalSaveData(CompoundTag compoundTag) {
      UUID owner = this.getOwnerUUID();
      if (owner != null) {
         compoundTag.putUUID("Owner", owner);
      }

      compoundTag.putInt("Lifespan", this.lifespan);
      compoundTag.putInt("Age", this.age);
      compoundTag.putFloat("Scale", (Float)this.entityData.get(SCALE));
   }

   private PlayState predicate(AnimationState<EnkiduEntity> state) {
      if ((Boolean)this.entityData.get(DESPAWNING)) {
         state.getController().setAnimation(END_ANIM);
         return PlayState.CONTINUE;
      } else if (this.animTick < 60) {
         state.getController().setAnimation(START_ANIM);
         return PlayState.CONTINUE;
      } else {
         state.getController().setAnimation(IDLE_ANIM);
         return PlayState.CONTINUE;
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 5, this::predicate));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
