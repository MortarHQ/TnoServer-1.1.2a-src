package io.github.Memoires.mysticism.entity.skill;

import io.github.Memoires.mysticism.ability.subclass.IWeaponStorage;
import io.github.Memoires.mysticism.menu.container.WeaponStorageContainer;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.entity.projectile.ThrownItemProjectile;
import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
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

public class GateEntity extends Entity implements GeoEntity {
   private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(GateEntity.class, EntityDataSerializers.OPTIONAL_UUID);
   private static final double BEHIND_DISTANCE = 1.5;
   private static final int THROW_INTERVAL_TICKS = 20;
   private static final float THROW_VELOCITY = 2.2F;
   private static final float THROW_INACCURACY = 1.0F;
   private static final RawAnimation GATE_ROTATION = RawAnimation.begin().thenLoop("Gate rotation");
   private final AnimatableInstanceCache cache;
   private ManasSkillInstance weaponStorageSkillInstance;
   private int throwCooldown = 20;
   private boolean isSurround = false;

   public GateEntity(EntityType<? extends Entity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.cache = GeckoLibUtil.createInstanceCache(this);
      this.noPhysics = true;
      this.setNoGravity(true);
   }

   public GateEntity(EntityType<? extends Entity> pEntityType, Level pLevel, LivingEntity owner, ManasSkillInstance weaponStorageSkillInstance) {
      this(pEntityType, pLevel);
      this.setOwner(owner);
      this.weaponStorageSkillInstance = weaponStorageSkillInstance;
      if (owner != null) {
         this.snapBehindOwner(owner);
      }
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(OWNER_UUID, Optional.empty());
   }

   public void setOwner(@Nullable LivingEntity owner) {
      this.entityData.set(OWNER_UUID, owner != null ? Optional.of(owner.getUUID()) : Optional.empty());
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

   public void setWeaponStorageSkillInstance(@Nullable ManasSkillInstance instance) {
      this.weaponStorageSkillInstance = instance;
   }

   @Nullable
   public ManasSkillInstance getWeaponStorageSkillInstance() {
      return this.weaponStorageSkillInstance;
   }

   public boolean getIsSurround() {
      return this.isSurround;
   }

   public void setIsSurround(boolean isSurround) {
      this.isSurround = isSurround;
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         LivingEntity owner = this.getOwner();
         if (owner == null) {
            if (this.getOwnerUUID() != null) {
               this.discard();
            }
         } else {
            if (--this.throwCooldown <= 0) {
               this.throwCooldown = 20;
               this.throwRandomWeapon(owner);
            }
         }
      }
   }

   private void snapBehindOwner(LivingEntity owner) {
      float yawRad = owner.getYRot() * (float) (Math.PI / 180.0);
      double behindX = owner.getX() + Mth.sin(yawRad) * 1.5;
      double behindZ = owner.getZ() - Mth.cos(yawRad) * 1.5;
      double behindY = owner.getEyeY() - 0.25;
      this.setPos(behindX, behindY, behindZ);
      Vec3 look = owner.getLookAngle();
      float yaw = (float)(Math.atan2(look.z, look.x) * (180.0 / Math.PI)) - 90.0F;
      float pitch = (float)(-(Math.atan2(look.y, Math.sqrt(look.x * look.x + look.z * look.z)) * (180.0 / Math.PI)));
      this.setYRot(yaw);
      this.setXRot(pitch);
      this.yRotO = yaw;
      this.xRotO = pitch;
      this.setDeltaMovement(Vec3.ZERO);
   }

   @Nullable
   private WeaponStorageContainer fetchWeaponStorage(LivingEntity owner) {
      if (this.weaponStorageSkillInstance == null) {
         return null;
      } else {
         return this.weaponStorageSkillInstance.getSkill() instanceof IWeaponStorage weaponStorageSkill
            ? weaponStorageSkill.getWeaponStorage(this.weaponStorageSkillInstance, owner.registryAccess(), owner)
            : null;
      }
   }

   private void throwRandomWeapon(LivingEntity owner) {
      WeaponStorageContainer container = this.fetchWeaponStorage(owner);
      if (container != null) {
         RandomSource random = this.level().getRandom();
         ItemStack weapon = container.getRandomWeapon(random);
         if (!weapon.isEmpty()) {
            ItemStack toThrow = weapon.copyWithCount(1);
            toThrow.set((DataComponentType)TensuraDataComponents.DUMMY_ITEM.get(), true);
            ThrownItemProjectile projectile = new ThrownItemProjectile(this.level(), owner, toThrow, true, 0.0F);
            projectile.pickup = Pickup.DISALLOWED;
            Vec3 look = owner.getLookAngle();
            if (this.getIsSurround()) {
               look = this.getLookAngle();
            }

            double spawnOffset = 1.5;
            double spawnX = this.getX() + look.x * spawnOffset;
            double spawnY = this.getY() + look.y * spawnOffset;
            double spawnZ = this.getZ() + look.z * spawnOffset;
            projectile.setPos(spawnX, spawnY, spawnZ);
            projectile.shoot(look.x, look.y, look.z, 2.2F, 1.0F);
            this.level().addFreshEntity(projectile);
            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.CRIT, spawnX, spawnY, spawnZ, 6, 0.1, 0.1, 0.1, 0.02);
            }
         }
      }
   }

   protected void readAdditionalSaveData(CompoundTag compoundTag) {
      if (compoundTag.hasUUID("Owner")) {
         this.entityData.set(OWNER_UUID, Optional.of(compoundTag.getUUID("Owner")));
      }

      this.throwCooldown = compoundTag.getInt("ThrowCooldown");
      this.isSurround = compoundTag.getBoolean("isSurround");
   }

   protected void addAdditionalSaveData(CompoundTag compoundTag) {
      UUID owner = this.getOwnerUUID();
      if (owner != null) {
         compoundTag.putUUID("Owner", owner);
      }

      compoundTag.putInt("ThrowCooldown", this.throwCooldown);
      compoundTag.putBoolean("isSurround", this.isSurround);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, this::rotationPredicate));
   }

   private <E extends GateEntity> PlayState rotationPredicate(AnimationState<E> state) {
      state.setAndContinue(GATE_ROTATION);
      return PlayState.CONTINUE;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
