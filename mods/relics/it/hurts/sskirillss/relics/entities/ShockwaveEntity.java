package it.hurts.sskirillss.relics.entities;

import it.hurts.sskirillss.relics.init.EntityRegistry;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ShockwaveEntity extends ThrowableProjectile {
   private int radius;
   private float damage;
   private int step;
   private List<BlockPos> poses = new ArrayList<>();

   public ShockwaveEntity(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public ShockwaveEntity(Level level, int radius, float damage) {
      super((EntityType)EntityRegistry.SHOCKWAVE.get(), level);
      this.radius = radius;
      this.damage = damage;
   }

   public void tick() {
      super.tick();
      BlockPos center = this.blockPosition();
      Level level = this.level();
      if (this.poses.isEmpty()) {
         for (int i = -this.radius; i <= this.radius; i++) {
            float r1 = Mth.sqrt(this.radius * this.radius - i * i);

            for (int j = -((int)r1); j <= r1; j++) {
               this.poses.add(center.offset(i, 0, j));
            }
         }
      }

      if (!this.poses.isEmpty()) {
         Entity owner = this.getOwner();
         Vec3 centerVec = new Vec3(center.getX(), center.getY(), center.getZ());
         List<BlockPos> closest = this.poses.stream().filter(px -> new Vec3(px.getX(), px.getY(), px.getZ()).distanceTo(centerVec) <= this.step).toList();
         this.poses.removeAll(closest);

         for (BlockPos p : closest) {
            if (!level.isClientSide()) {
               float damage = Mth.clamp(this.radius * this.damage / (this.step + 1), 0.0F, 1000.0F);

               for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, new AABB(Vec3.atCenterOf(p), Vec3.atCenterOf(p.above(3))).inflate(0.5))) {
                  if (owner == null || !entity.getStringUUID().equals(owner.getStringUUID())) {
                     if (this.getOwner() instanceof Player player) {
                        EntityUtils.hurt(entity, this.level().damageSources().playerAttack(player), damage);
                     } else {
                        entity.hurt(this.level().damageSources().magic(), damage);
                     }

                     entity.setDeltaMovement(entity.position().add(0.0, 1.0, 0.0).subtract(centerVec).normalize().multiply(2.0, 1.0, 2.0));
                  }
               }
            }

            BlockState state = level.getBlockState(p);
            if (state.blocksMotion() && !level.getBlockState(p.above()).blocksMotion()) {
               BlockSimulationEntity entity = new BlockSimulationEntity(level, state);
               entity.setPos(p.getX() + 0.5F, p.getY() + 0.5F, p.getZ() + 0.5F);
               entity.setDeltaMovement(0.0, this.step * 0.02F, 0.0);
               level.addFreshEntity(entity);
            }
         }

         this.step++;
         if (this.poses.isEmpty() || this.step >= this.poses.size()) {
            this.remove(RemovalReason.KILLED);
         }
      } else {
         this.remove(RemovalReason.KILLED);
      }
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.radius = compound.getInt("Radius");
      this.damage = compound.getFloat("Damage");
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("Radius", this.radius);
      compound.putFloat("Damage", this.damage);
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public int getRadius() {
      return this.radius;
   }

   public void setRadius(int radius) {
      this.radius = radius;
   }

   public float getDamage() {
      return this.damage;
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public int getStep() {
      return this.step;
   }

   public void setStep(int step) {
      this.step = step;
   }

   public List<BlockPos> getPoses() {
      return this.poses;
   }

   public void setPoses(List<BlockPos> poses) {
      this.poses = poses;
   }
}
