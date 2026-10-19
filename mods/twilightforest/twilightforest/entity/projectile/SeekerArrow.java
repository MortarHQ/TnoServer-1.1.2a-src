package twilightforest.entity.projectile;

import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFEntities;

public class SeekerArrow extends TFArrow {
   private static final EntityDataAccessor<Integer> TARGET = SynchedEntityData.defineId(SeekerArrow.class, EntityDataSerializers.INT);
   private static final double seekDistance = 5.0;
   private static final double seekFactor = 0.8;
   private static final double seekAngle = Math.PI / 6;
   private static final double seekThreshold = 0.5;

   public SeekerArrow(EntityType<? extends SeekerArrow> type, Level world) {
      super(type, world);
      this.setBaseDamage(1.0);
   }

   public SeekerArrow(Level world, LivingEntity shooter, ItemStack stack, ItemStack weapon) {
      super((EntityType<? extends TFArrow>)TFEntities.SEEKER_ARROW.get(), world, shooter, stack, weapon);
      this.setBaseDamage(1.0);
   }

   public SeekerArrow(AbstractArrow parentArrow, ItemStack stack, ItemStack weapon) {
      super((EntityType<? extends TFArrow>)TFEntities.SEEKER_ARROW.get(), parentArrow, stack, weapon);
      this.setBaseDamage(1.0);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(TARGET, -1);
   }

   public void tick() {
      if (this.isThisArrowFlying()) {
         if (!this.level().isClientSide()) {
            this.updateTarget();
         }

         if (this.level().isClientSide() && !this.inGround) {
            for (int i = 0; i < 4; i++) {
               this.level()
                  .addParticle(
                     ParticleTypes.WITCH,
                     this.getX() + this.getDeltaMovement().x() * i / 4.0,
                     this.getY() + this.getDeltaMovement().y() * i / 4.0,
                     this.getZ() + this.getDeltaMovement().z() * i / 4.0,
                     -this.getDeltaMovement().x(),
                     -this.getDeltaMovement().y() + 0.2,
                     -this.getDeltaMovement().z()
                  );
            }
         }

         Entity target = this.getTarget();
         if (target != null) {
            Vec3 targetVec = this.getVectorToTarget(target).scale(0.8);
            Vec3 courseVec = this.getMotionVec();
            double courseLen = courseVec.length();
            double targetLen = targetVec.length();
            double totalLen = Math.sqrt(courseLen * courseLen + targetLen * targetLen);
            double dotProduct = courseVec.dot(targetVec) / (courseLen * targetLen);
            if (dotProduct > 0.5) {
               Vec3 newMotion = courseVec.scale(courseLen / totalLen).add(targetVec.scale(courseLen / totalLen));
               this.setDeltaMovement(newMotion.add(0.0, 0.045F, 0.0));
            } else if (!this.level().isClientSide()) {
               this.setTarget(null);
            }
         }
      }

      super.tick();
   }

   private void updateTarget() {
      Entity target = this.getTarget();
      if (target != null && !target.isAlive()) {
         target = null;
         this.setTarget(null);
      }

      if (target == null) {
         AABB positionBB = new AABB(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY(), this.getZ());
         AABB targetBB = positionBB;
         Vec3 courseVec = this.getMotionVec().scale(5.0).yRot((float) (Math.PI / 6));
         targetBB = targetBB.minmax(positionBB.move(courseVec));
         courseVec = this.getMotionVec().scale(5.0).yRot((float) (-Math.PI / 6));
         targetBB = targetBB.minmax(positionBB.move(courseVec));
         targetBB = targetBB.inflate(0.0, 2.5, 0.0);
         double closestDot = -1.0;
         Entity closestTarget = null;
         List<LivingEntity> entityList = this.level().getEntitiesOfClass(LivingEntity.class, targetBB);
         List<LivingEntity> monsters = entityList.stream().filter(l -> l instanceof Monster).toList();
         if (!monsters.isEmpty()) {
            for (LivingEntity monster : monsters) {
               if (((Monster)monster).getTarget() == this.getOwner()) {
                  this.setTarget(monster);
                  return;
               }
            }

            for (LivingEntity monster : monsters) {
               if (!(monster instanceof NeutralMob) && monster.hasLineOfSight(this)) {
                  this.setTarget(monster);
                  return;
               }
            }
         }

         for (LivingEntity living : entityList) {
            if (living.hasLineOfSight(this)
               && living != this.getOwner()
               && (!(this.getOwner() != null && living instanceof TamableAnimal animal) || animal.getOwner() != this.getOwner())) {
               Vec3 motionVec = this.getMotionVec().normalize();
               Vec3 targetVec = this.getVectorToTarget(living).normalize();
               double dot = motionVec.dot(targetVec);
               if (dot > Math.max(closestDot, 0.5)) {
                  closestDot = dot;
                  closestTarget = living;
               }
            }
         }

         if (closestTarget != null) {
            this.setTarget(closestTarget);
         }
      }
   }

   private Vec3 getMotionVec() {
      return new Vec3(this.getDeltaMovement().x(), this.getDeltaMovement().y(), this.getDeltaMovement().z());
   }

   private Vec3 getVectorToTarget(Entity target) {
      return new Vec3(target.getX() - this.getX(), target.getY() + target.getEyeHeight() - this.getY(), target.getZ() - this.getZ());
   }

   @Nullable
   private Entity getTarget() {
      return this.level().getEntity((Integer)this.getEntityData().get(TARGET));
   }

   private void setTarget(@Nullable Entity e) {
      this.getEntityData().set(TARGET, e == null ? -1 : e.getId());
   }

   private boolean isThisArrowFlying() {
      return !this.inGround && this.getDeltaMovement().lengthSqr() > 1.0;
   }

   protected void onHitEntity(EntityHitResult result) {
      this.setCritArrow(false);
      super.onHitEntity(result);
   }
}
