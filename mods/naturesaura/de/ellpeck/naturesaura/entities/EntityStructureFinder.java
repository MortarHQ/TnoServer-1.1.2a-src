package de.ellpeck.naturesaura.entities;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityStructureFinder extends EyeOfEnder {
   public static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(EntityStructureFinder.class, EntityDataSerializers.INT);
   private double targetX;
   private double targetY;
   private double targetZ;
   private int despawnTimer;
   private boolean shatterOrDrop;

   public EntityStructureFinder(EntityType<? extends EyeOfEnder> type, Level level) {
      super(type, level);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(COLOR, 0);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("color", (Integer)this.entityData.get(COLOR));
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.entityData.set(COLOR, compound.getInt("color"));
   }

   public void signalTo(BlockPos pos) {
      double d0 = pos.getX();
      int i = pos.getY();
      double d1 = pos.getZ();
      double d2 = d0 - this.getX();
      double d3 = d1 - this.getZ();
      double f = Math.sqrt(d2 * d2 + d3 * d3);
      if (f > 12.0) {
         this.targetX = this.getX() + d2 / f * 12.0;
         this.targetZ = this.getZ() + d3 / f * 12.0;
         this.targetY = this.getY() + 8.0;
      } else {
         this.targetX = d0;
         this.targetY = i;
         this.targetZ = d1;
      }

      this.despawnTimer = 0;
      this.shatterOrDrop = this.random.nextInt(4) > 0;
   }

   public void tick() {
      this.baseTick();
      Vec3 vec3d = this.getDeltaMovement();
      double d0 = this.getX() + vec3d.x;
      double d1 = this.getY() + vec3d.y;
      double d2 = this.getZ() + vec3d.z;
      double f = Math.sqrt(vec3d.horizontalDistance());
      this.setYRot((float)(Mth.atan2(vec3d.x, vec3d.z) * 180.0F / (float)Math.PI));
      this.setXRot((float)(Mth.atan2(vec3d.y, f) * 180.0F / (float)Math.PI));

      while (this.getXRot() - this.xRotO < -180.0F) {
         this.xRotO -= 360.0F;
      }

      while (this.getXRot() - this.xRotO >= 180.0F) {
         this.xRotO += 360.0F;
      }

      while (this.getYRot() - this.yRotO < -180.0F) {
         this.yRotO -= 360.0F;
      }

      while (this.getYRot() - this.yRotO >= 180.0F) {
         this.yRotO += 360.0F;
      }

      this.setXRot(Mth.lerp(0.2F, this.xRotO, this.getXRot()));
      this.setYRot(Mth.lerp(0.2F, this.yRotO, this.getYRot()));
      if (!this.level().isClientSide) {
         double d3 = this.targetX - d0;
         double d4 = this.targetZ - d2;
         float f1 = (float)Math.sqrt(d3 * d3 + d4 * d4);
         float f2 = (float)Mth.atan2(d4, d3);
         double d5 = Mth.lerp(0.0025, f, f1);
         double d6 = vec3d.y;
         if (f1 < 1.0F) {
            d5 *= 0.8;
            d6 *= 0.8;
         }

         int j = this.getY() < this.targetY ? 1 : -1;
         vec3d = new Vec3(Math.cos(f2) * d5, d6 + (j - d6) * 0.015F, Math.sin(f2) * d5);
         this.setDeltaMovement(vec3d);
      }

      if (this.isInWater()) {
         for (int i = 0; i < 4; i++) {
            this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3d.x * 0.25, d1 - vec3d.y * 0.25, d2 - vec3d.z * 0.25, vec3d.x, vec3d.y, vec3d.z);
         }
      } else if (this.level().isClientSide) {
         NaturesAuraAPI.instance()
            .spawnMagicParticle(
               d0 - vec3d.x * 0.25 + this.random.nextDouble() * 0.6 - 0.3,
               d1 - vec3d.y * 0.25 - 0.5,
               d2 - vec3d.z * 0.25 + this.random.nextDouble() * 0.6 - 0.3,
               vec3d.x * 0.25,
               vec3d.y * 0.25,
               vec3d.z * 0.25,
               (Integer)this.entityData.get(COLOR),
               1.0F,
               50,
               0.0F,
               false,
               true
            );
      }

      if (!this.level().isClientSide) {
         this.setPos(d0, d1, d2);
         this.despawnTimer++;
         if (this.despawnTimer > 80 && !this.level().isClientSide) {
            this.playSound(SoundEvents.ENDER_EYE_DEATH, 1.0F, 1.0F);
            this.remove(RemovalReason.DISCARDED);
            if (this.shatterOrDrop) {
               this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
            } else {
               PacketHandler.sendToAllAround(
                  this.level(),
                  this.blockPosition(),
                  32,
                  new PacketParticles((float)this.getX(), (float)this.getY(), (float)this.getZ(), PacketParticles.Type.STRUCTURE_FINDER, this.getId())
               );
            }
         }
      } else {
         this.setPosRaw(d0, d1, d2);
      }
   }
}
