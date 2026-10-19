package com.cerbon.bosses_of_mass_destruction.item.custom;

import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichUtils;
import com.cerbon.bosses_of_mass_destruction.item.BMDItems;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.event.Event;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class SoulStarEntity extends Entity implements ItemSupplier {
   private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(SoulStarEntity.class, EntityDataSerializers.ITEM_STACK);
   private final ClientParticleBuilder particleBuilder = new ClientParticleBuilder((ParticleOptions)BMDParticles.SPARKLES.get())
      .color(LichUtils.blueColorFade)
      .age(RandomUtils.range(80, 100))
      .colorVariation(0.3)
      .scale(f -> 0.05F - f * 0.025F)
      .brightness(15728880);
   private double targetX = 0.0;
   private double targetY = 0.0;
   private double targetZ = 0.0;

   public SoulStarEntity(EntityType<?> entityType, Level level) {
      super(entityType, level);
   }

   public SoulStarEntity(Level level, double x, double y, double z) {
      this((EntityType<?>)BMDEntities.SOUL_STAR.get(), level);
      this.absMoveTo(x, y, z);
   }

   public void setItem(ItemStack stack) {
      if (stack.isEmpty()) {
         this.getEntityData().set(ITEM, new ItemStack((ItemLike)BMDItems.SOUL_STAR.get()));
      } else {
         this.getEntityData().set(ITEM, stack.copyWithCount(1));
      }
   }

   private ItemStack getTrackedItem() {
      return (ItemStack)this.getEntityData().get(ITEM);
   }

   @NotNull
   public ItemStack getItem() {
      ItemStack itemStack = this.getTrackedItem();
      return itemStack.isEmpty() ? new ItemStack((ItemLike)BMDItems.SOUL_STAR.get()) : itemStack;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(ITEM, ItemStack.EMPTY);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean shouldRenderAtSqrDistance(double distance) {
      double d = this.getBoundingBox().getSize() * 4.0;
      if (Double.isNaN(d)) {
         d = 4.0;
      }

      d *= 64.0;
      return distance < d * d;
   }

   public void initTargetPos(BlockPos pos) {
      double d = pos.getX();
      double e = pos.getZ();
      double f = d - this.getX();
      double g = e - this.getZ();
      double h = Math.sqrt(f * f + g * g);
      this.targetX = this.getX() + f / h * 12.0;
      this.targetZ = this.getZ() + g / h * 12.0;
      this.targetY = this.getY() + 8.0;
   }

   @OnlyIn(Dist.CLIENT)
   public void lerpMotion(double x, double y, double z) {
      this.setDeltaMovement(x, y, z);
      if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
         double f = Math.sqrt(x * x + z * z);
         this.setYRot((float)(Mth.atan2(x, z) * 180.0F / (float)Math.PI));
         this.setXRot((float)(Mth.atan2(y, f) * 180.0F / (float)Math.PI));
         this.yRotO = this.getYRot();
         this.xRotO = this.getXRot();
      }
   }

   public void tick() {
      if (this.level().isClientSide() && this.tickCount == 1) {
         double rotationOffset = this.random.nextDouble() * 360.0;
         CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new Event(() -> true, () -> this.spawnTrailParticles(rotationOffset), this::isRemoved));
      }

      super.tick();
      Vec3 vec3 = this.getDeltaMovement();
      double d = this.getX() + vec3.x;
      double e = this.getY() + vec3.y;
      double f = this.getZ() + vec3.z;
      double g = vec3.horizontalDistance();
      this.setXRot(this.updateRotation(this.xRotO, (float)(Mth.atan2(vec3.y, g) * 180.0F / (float)Math.PI)));
      this.setYRot(this.updateRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI)));
      if (!this.level().isClientSide()) {
         double xd = this.targetX - d;
         double zd = this.targetZ - f;
         float distance = (float)Math.sqrt(xd * xd + zd * zd);
         float k = (float)Mth.atan2(zd, xd);
         double l = Mth.lerp(0.0025, g, distance);
         double m = vec3.y;
         if (distance < 1.0F) {
            l *= 0.8;
            m *= 0.8;
            this.playSound((SoundEvent)BMDSounds.SOUL_STAR.get(), 1.0F, 1.0F);
            this.discard();
            this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
         }

         int n = this.getY() < this.targetY ? 1 : -1;
         vec3 = new Vec3(Math.cos(k) * l, m + (n - m) * 0.015F, Math.sin(k) * l);
         this.setDeltaMovement(vec3);
         this.absMoveTo(d, e, f);
      } else {
         this.setPosRaw(d, e, f);
      }

      this.spawnParticles(d, vec3, e, f);
   }

   private void spawnTrailParticles(double rotationOffset) {
      Vec3 look = this.getDeltaMovement();
      Vec3 cross = look.cross(VecUtils.yAxis).normalize();
      Vec3 rotatedOffset = VecUtils.rotateVector(cross, look, rotationOffset + this.tickCount * 30.0).scale(0.25);
      Vec3 particlePos = this.position().add(rotatedOffset);
      this.particleBuilder.build(particlePos, this.getDeltaMovement().scale(0.1));
   }

   private void spawnParticles(double d, Vec3 vec3, double e, double f) {
      if (this.isInWater()) {
         for (int p = 0; p <= 3; p++) {
            this.level().addParticle(ParticleTypes.BUBBLE, d - vec3.x * 0.25, e - vec3.y * 0.25, f - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
         }
      }
   }

   protected void addAdditionalSaveData(@NotNull CompoundTag compound) {
      ItemStack itemStack = this.getTrackedItem();
      if (!itemStack.isEmpty()) {
         compound.put("Item", itemStack.save(this.level().registryAccess(), new CompoundTag()));
      }
   }

   protected void readAdditionalSaveData(@NotNull CompoundTag compound) {
      ItemStack itemStack = ItemStack.parseOptional(this.level().registryAccess(), compound.getCompound("Item"));
      this.setItem(itemStack);
   }

   public float getLightLevelDependentMagicValue() {
      return 1.0F;
   }

   public boolean isAttackable() {
      return false;
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return super.getAddEntityPacket(entity);
   }

   private float updateRotation(float f, float g) {
      float f1 = f;

      while (g - f1 < -180.0F) {
         f1 -= 360.0F;
      }

      while (g - f1 >= 180.0F) {
         f1 += 360.0F;
      }

      return Mth.lerp(0.2F, f1, g);
   }
}
