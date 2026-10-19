package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Scylla;

import com.github.L_Ender.cataclysm.client.particle.Options.ParryParticleOptions;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.IHoldEntity;
import com.github.L_Ender.cataclysm.entity.projectile.Spark_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.message.MessageEntityCameraSwitch;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class Scylla_Ceraunus_Entity extends AbstractArrow implements IHoldEntity {
   private static final EntityDataAccessor<Optional<UUID>> CONTROLLER_UUID = SynchedEntityData.defineId(
      Scylla_Ceraunus_Entity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private static final EntityDataAccessor<Integer> CONTROLLER_ID = SynchedEntityData.defineId(Scylla_Ceraunus_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> GRAB = SynchedEntityData.defineId(Scylla_Ceraunus_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Float> Y_ROT_OLD = SynchedEntityData.defineId(Scylla_Ceraunus_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> X_ROT_OLD = SynchedEntityData.defineId(Scylla_Ceraunus_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Boolean> HOOK_MODE = SynchedEntityData.defineId(Scylla_Ceraunus_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(Scylla_Ceraunus_Entity.class, EntityDataSerializers.INT);

   public Scylla_Ceraunus_Entity(EntityType type, Level worldIn) {
      super(type, worldIn);
   }

   public Scylla_Ceraunus_Entity(EntityType type, double x, double y, double z, Level worldIn) {
      this(type, worldIn);
      this.setPos(x, y, z);
   }

   public Scylla_Ceraunus_Entity(Level worldIn, LivingEntity shooter) {
      this((EntityType)ModEntities.SCYLLA_CERAUNUS.get(), shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ(), worldIn);
      this.setOwner(shooter);
      if (shooter instanceof Player) {
         this.pickup = Pickup.ALLOWED;
      }
   }

   protected void defineSynchedData(Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(CONTROLLER_UUID, Optional.empty());
      p_326229_.define(CONTROLLER_ID, -1);
      p_326229_.define(GRAB, false);
      p_326229_.define(HOOK_MODE, false);
      p_326229_.define(Y_ROT_OLD, 0.0F);
      p_326229_.define(X_ROT_OLD, 0.0F);
      p_326229_.define(PHASE, 0);
   }

   @Nullable
   public UUID getControllerUUID() {
      return (UUID)((Optional)this.entityData.get(CONTROLLER_UUID)).orElse(null);
   }

   public void setControllerUUID(@Nullable UUID uniqueId) {
      this.entityData.set(CONTROLLER_UUID, Optional.ofNullable(uniqueId));
   }

   public Entity getController() {
      if (!this.level().isClientSide) {
         UUID id = this.getControllerUUID();
         return id == null ? null : ((ServerLevel)this.level()).getEntity(id);
      } else {
         int id = (Integer)this.entityData.get(CONTROLLER_ID);
         return id == -1 ? null : this.level().getEntity(id);
      }
   }

   public boolean getGrab() {
      return (Boolean)this.entityData.get(GRAB);
   }

   public void setGrab(boolean weapon) {
      this.entityData.set(GRAB, weapon);
   }

   public boolean getHookMode() {
      return (Boolean)this.entityData.get(HOOK_MODE);
   }

   public void setHookMode(boolean weapon) {
      this.entityData.set(HOOK_MODE, weapon);
   }

   public float getYrotOld() {
      return (Float)this.entityData.get(Y_ROT_OLD);
   }

   public void setYrotOld(float rot) {
      this.entityData.set(Y_ROT_OLD, rot);
   }

   public float getXrotOld() {
      return (Float)this.entityData.get(X_ROT_OLD);
   }

   public void setXrotOld(float rot) {
      this.entityData.set(X_ROT_OLD, rot);
   }

   public int getPhase() {
      return (Integer)this.entityData.get(PHASE);
   }

   public void setPhase(int phase) {
      this.entityData.set(PHASE, phase);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.hasUUID("ControllerUUID")) {
         this.setControllerUUID(tag.getUUID("ControllerUUID"));
      }

      this.setHookMode(tag.getBoolean("Hook"));
      this.setGrab(tag.getBoolean("Grab"));
      this.setPhase(tag.getInt("Phase"));
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.getControllerUUID() != null) {
         tag.putUUID("ControllerUUID", this.getControllerUUID());
      }

      tag.putBoolean("Hook", this.getHookMode());
      tag.putBoolean("Grab", this.getGrab());
      tag.putInt("Phase", this.getPhase());
   }

   public void tick() {
      super.tick();
      Entity controller = this.getController();
      if (controller instanceof Scylla_Entity levi) {
         this.entityData.set(CONTROLLER_ID, levi.getId());
         levi.setAnchorUUID(this.getUUID());
         if (this.getHookMode()) {
            if (this.getGrab() && !this.level().isClientSide) {
               for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().deflate(0.3F))) {
                  if (entity.equals(controller)) {
                     if (!this.getPassengers().isEmpty()) {
                        ((Entity)this.getPassengers().get(0)).setShiftKeyDown(false);
                     }

                     this.discard();
                  }
               }
            }
         } else if (this.getGrab()) {
            if (!this.isAcceptibleReturnOwner()) {
               this.discard();
            } else {
               this.setNoPhysics(true);
               Vec3 vec3 = controller.getEyePosition().subtract(this.position());
               this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * 3.0, this.getZ());
               this.setYRot(this.getYrotOld());
               if (this.level().isClientSide) {
                  this.yOld = this.getY();
               } else {
                  if (!this.getPassengers().isEmpty() && ((Entity)this.getPassengers().get(0)).isShiftKeyDown()) {
                     ((Entity)this.getPassengers().get(0)).setShiftKeyDown(false);
                  }

                  for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().deflate(0.3F))) {
                     if (entity.equals(controller)) {
                        if (!this.getPassengers().isEmpty()) {
                           PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                              (Entity)this.getPassengers().get(0),
                              new MessageEntityCameraSwitch.FirstPerson(((Entity)this.getPassengers().get(0)).getId()),
                              new CustomPacketPayload[0]
                           );
                        }

                        this.discard();
                     }
                  }
               }

               double d0 = 0.2;
               this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d0)));
            }
         }
      }
   }

   private boolean isAcceptibleReturnOwner() {
      Entity entity = this.getController();
      return entity != null && entity.isAlive() ? !(entity instanceof ServerPlayer) || !entity.isSpectator() : false;
   }

   protected void onHitEntity(EntityHitResult p_37573_) {
      Entity entity = p_37573_.getEntity();
      Entity entity1 = this.getController();
      DamageSource damagesource = CMDamageTypes.causeStormBringerDamage(this, (Entity)(entity1 == null ? this : entity1));
      if (entity.hurt(damagesource, (float)this.getBaseDamage())) {
         if (entity.getType() == EntityType.ENDERMAN) {
            return;
         }

         if (entity instanceof LivingEntity livingentity) {
            this.doKnockback(livingentity, damagesource);
            this.doPostHurtEffects(livingentity);
            this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
         }

         if (!this.getHookMode() && this.getPassengers().isEmpty() && !this.level().isClientSide) {
            entity.startRiding(this);
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new MessageEntityCameraSwitch.ThridPerson(entity.getId()), new CustomPacketPayload[0]);
         }
      }
   }

   protected void onHitBlock(BlockHitResult p_37573_) {
      super.onHitBlock(p_37573_);
      double DeltaMovementX = this.getRandom().nextGaussian() * 0.1;
      double DeltaMovementY = this.getRandom().nextGaussian() * 0.02;
      double DeltaMovementZ = this.getRandom().nextGaussian() * 0.1;
      if (this.level().isClientSide) {
         for (int i1 = 0; i1 < 5 + this.random.nextInt(2); i1++) {
            this.level()
               .addParticle(
                  new ParryParticleOptions(1.0F, 0.41568628F, 0.0F), this.getX(), this.getY(), this.getZ(), DeltaMovementX, DeltaMovementY, DeltaMovementZ
               );
         }
      } else if (this.getController() instanceof LivingEntity living && this.getPhase() > 0) {
         for (int i = 0; i < this.getPhase(); i++) {
            Spark_Entity peq = new Spark_Entity(this.level(), living);
            peq.setDamage((float)CMCommonConfig.Scylla.LightningStormDamage);
            peq.setAreaDamage((float)CMCommonConfig.Scylla.LightningAreaDamage);
            peq.setAreaRadius(1.0F);
            peq.setHpDamage((float)CMCommonConfig.Scylla.StormHpDamage);
            peq.shoot((this.random.nextFloat() - 0.5) * 0.5, this.random.nextFloat() * 0.4F + 0.01F, (this.random.nextFloat() - 0.5) * 0.5, 1.0F, 1.0F);
            peq.setPos(this.getX(), this.getY() + 0.03, this.getZ());
            this.level().addFreshEntity(peq);
         }
      }
   }

   public ItemStack getWeaponItem() {
      return this.getPickupItemStackOrigin();
   }

   protected boolean tryPickup(Player player) {
      return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack(Items.TRIDENT);
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      return true;
   }

   protected float getWaterInertia() {
      return 0.95F;
   }

   protected void doKnockback(LivingEntity entity, DamageSource damageSource) {
      double d0 = 1.5;
      double d1 = Math.max(0.0, 1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) * 1.0 / 3.0);
      Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(d0 * 0.6 * d1);
      if (vec3.lengthSqr() > 0.0) {
         entity.push(vec3.x, 0.1, vec3.z);
      }
   }

   protected void onHit(HitResult result) {
      super.onHit(result);
      if (!this.level().isClientSide) {
         this.setXrotOld(this.getXRot());
         this.setYrotOld(this.getYRot());
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 25.0F, 0.07F, 0, 20);
         this.setGrab(true);
      }
   }

   public boolean canUsePortal(boolean allowPassengers) {
      return false;
   }

   protected double getDefaultGravity() {
      return 0.08;
   }

   protected SoundEvent getDefaultHitGroundSoundEvent() {
      return (SoundEvent)ModSounds.HEAVY_SMASH.get();
   }
}
