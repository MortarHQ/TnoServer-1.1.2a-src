package com.tno_tensura_extras.entity;

import com.tno_tensura_extras.registry.ExtrasEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TeleportPortalEntity extends Entity implements GeoEntity, IEntityWithComplexSpawn {
   public static final int CLOSE_TICKS = 55;
   public static final double GROUND_OFFSET = 0.05;
   private static final RawAnimation OPEN = RawAnimation.begin().then("animation.warp_portal.start", LoopType.PLAY_ONCE).thenLoop("animation.warp_portal.loop");
   private static final RawAnimation CLOSE = RawAnimation.begin().thenPlayAndHold("animation.warp_portal.end");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private int life = 160;
   private int age;

   public TeleportPortalEntity(EntityType<? extends TeleportPortalEntity> type, Level level) {
      super(type, level);
      this.noPhysics = true;
      this.setNoGravity(true);
      this.setInvulnerable(true);
   }

   public static TeleportPortalEntity spawn(Level level, double x, double y, double z, int life) {
      TeleportPortalEntity portal = new TeleportPortalEntity(ExtrasEntityTypes.TELEPORT_PORTAL.get(), level);
      portal.life = Math.max(56, life);
      portal.setPos(x, y + 0.05, z);
      portal.setYRot(0.0F);
      portal.setXRot(0.0F);
      level.addFreshEntity(portal);
      return portal;
   }

   public int getLife() {
      return this.life;
   }

   public int getAge() {
      return this.age;
   }

   public void tick() {
      this.age++;
      if (!this.level().isClientSide() && this.age >= this.life) {
         this.discard();
      }
   }

   public AABB getBoundingBoxForCulling() {
      return this.getBoundingBox().inflate(2.0);
   }

   public boolean isAttackable() {
      return false;
   }

   public boolean canUsePortal(boolean allowPassengers) {
      return false;
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("Life", this.life);
      compound.putInt("Age", this.age);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.life = compound.getInt("Life");
      this.age = compound.getInt("Age");
   }

   public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
      buffer.writeVarInt(this.life);
      buffer.writeVarInt(this.age);
   }

   public void readSpawnData(RegistryFriendlyByteBuf buffer) {
      this.life = buffer.readVarInt();
      this.age = buffer.readVarInt();
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, state -> state.setAndContinue(this.life - this.age < 55 ? CLOSE : OPEN)));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
