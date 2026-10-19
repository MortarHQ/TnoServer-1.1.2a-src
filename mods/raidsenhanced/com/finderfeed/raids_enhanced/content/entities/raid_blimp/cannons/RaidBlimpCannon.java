package com.finderfeed.raids_enhanced.content.entities.raid_blimp.cannons;

import com.finderfeed.fdlib.data_structures.Pair;
import com.finderfeed.fdlib.util.FDTargetFinder;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.RaidBlimp;
import com.finderfeed.raids_enhanced.content.particles.SimpleTexturedParticleOptions;
import com.finderfeed.raids_enhanced.init.REParticles;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RaidBlimpCannon {
   private RaidBlimpCannonsController owner;
   private int shootCooldown = 20;
   private String bone;
   private EntityDataAccessor<Integer> clientTarget;
   private LivingEntity target;
   public boolean isLeft;
   public float yRot;
   public float xRot;
   public float yRotO;
   public float xRotO;

   public RaidBlimpCannon(RaidBlimpCannonsController controller, String bone, EntityDataAccessor<Integer> clientTarget, boolean isLeft) {
      this.owner = controller;
      this.clientTarget = clientTarget;
      this.bone = bone;
      this.isLeft = isLeft;
   }

   public RaidBlimpCannonsController getOwner() {
      return this.owner;
   }

   public void tick(List<? extends LivingEntity> entitiesAround) {
      RaidBlimp blimp = this.getOwner().getRaidBlimp();
      Level level = blimp.level();
      if (!level.isClientSide) {
         this.processTargeting(entitiesAround);
         this.shootCooldown = Mth.clamp(this.shootCooldown - 1, 0, Integer.MAX_VALUE);
      } else {
         this.processRotation();
      }
   }

   public void setCooldown(int cooldown) {
      this.shootCooldown = cooldown;
   }

   public boolean canShoot() {
      return this.getTarget() != null && this.shootCooldown <= 0;
   }

   public void shoot(int cooldown) {
      if (this.getTarget() != null) {
         Pair<Vec3, Vec3> posAndDir = this.getCannonPosAndDirection(1.0F);
         Vec3 pos = (Vec3)posAndDir.first;
         Vec3 epos = this.getTargetPos(this.getTarget());
         this.sendCannonParticles(this.target, pos);
         Vec3 b = epos.subtract(pos);
         RaidBlimpCannonProjectile.summon(this.getOwner().getRaidBlimp(), pos, b);
         this.setCooldown(cooldown);
      }
   }

   private void sendCannonParticles(LivingEntity target, Vec3 pos) {
      if (target.level() instanceof ServerLevel serverLevel) {
         Vec3 targetPos = this.getTargetPos(target);
         Vec3 b = targetPos.subtract(pos).normalize();
         Vec3 particlePos = pos.add(b);

         for (ServerPlayer player : FDTargetFinder.getEntitiesInSphere(ServerPlayer.class, target.level(), pos, 120.0F)) {
            serverLevel.sendParticles(
               player,
               new SimpleTexturedParticleOptions(REParticles.EXPLOSION.get(), 0.75F, 8),
               true,
               particlePos.x,
               particlePos.y,
               particlePos.z,
               1,
               0.0,
               0.0,
               0.0,
               0.0
            );
         }

         serverLevel.playSound(null, pos.x, pos.y, pos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 3.0F, 1.5F);
      }
   }

   private void processTargeting(List<? extends LivingEntity> entitiesAround) {
      if (this.getTarget() != null && !this.isTargetValid(this.getTarget(), 0.1)) {
         this.setTarget(null);
      }

      if (this.getTarget() == null) {
         List<? extends LivingEntity> targets = this.getValidTargets(entitiesAround, 0.1);
         if (!targets.isEmpty()) {
            LivingEntity target = targets.get(this.getOwner().getRaidBlimp().level().random.nextInt(targets.size()));
            this.setTarget(target);
         }
      }
   }

   private List<? extends LivingEntity> getValidTargets(List<? extends LivingEntity> entities, double dotRadius) {
      List<LivingEntity> targets = new ArrayList<>();
      Pair<Vec3, Vec3> cannonPosDir = this.getCannonPosAndDirection(1.0F);
      Vec3 cannonPos = (Vec3)cannonPosDir.first;
      Vec3 cannonDir = (Vec3)cannonPosDir.second;

      for (LivingEntity entity : entities) {
         if (this.isTargetValid(cannonPos, cannonDir, entity, dotRadius) && !this.getOwner().checkIfAlreadyHasTarget(this, entity)) {
            targets.add(entity);
         }
      }

      return targets;
   }

   private boolean isTargetValid(LivingEntity entity, double dotRadius) {
      Pair<Vec3, Vec3> cannonPosDir = this.getCannonPosAndDirection(1.0F);
      return this.isTargetValid((Vec3)cannonPosDir.first, (Vec3)cannonPosDir.second, entity, dotRadius);
   }

   private boolean isTargetValid(Vec3 cannonPos, Vec3 cannonDir, LivingEntity entity, double dotRadius) {
      if (!entity.isDeadOrDying() && !entity.isRemoved()) {
         Vec3 epos = this.getTargetPos(entity);
         if (epos.distanceTo(cannonPos) > 40.0) {
            return false;
         }

         Vec3 pos = entity.position();
         Vec3 between = pos.subtract(cannonPos);
         double dot = between.normalize().dot(cannonDir.normalize());
         if (dot >= dotRadius) {
            ClipContext clipContext = new ClipContext(cannonPos, epos, Block.COLLIDER, Fluid.NONE, CollisionContext.empty());
            BlockHitResult res = entity.level().clip(clipContext);
            if (res.getType() == Type.MISS) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void processRotation() {
      this.yRotO = this.yRot;
      this.xRotO = this.xRot;
      float rotationSpeed = 10.0F;
      Pair<Float, Float> targetRotation = this.getTargetYXRotation();
      float yTargetRot = (Float)targetRotation.first;
      float xTargetRot = (Float)targetRotation.second;
      Vec3 targetRot = new Vec3(xTargetRot, yTargetRot, 0.0);
      Vec3 currentRot = new Vec3(this.xRot, this.yRot, 0.0);
      Vec3 between = targetRot.subtract(currentRot);
      float dist = (float)between.length();
      float shift = Mth.clamp(dist, 0.0F, rotationSpeed);
      Vec3 shiftVector = between.normalize().scale(shift);
      this.yRot = this.yRot + (float)shiftVector.y;
      this.xRot = this.xRot + (float)shiftVector.x;
   }

   public Pair<Float, Float> getCurrentYXRotation(float pticks) {
      return new Pair(FDMathUtil.lerp(this.yRotO, this.yRot, pticks), FDMathUtil.lerp(this.xRotO, this.xRot, pticks));
   }

   private Pair<Float, Float> getTargetYXRotation() {
      LivingEntity target = this.getTarget();
      if (target == null) {
         return new Pair(0.0F, 0.0F);
      }

      Pair<Vec3, Vec3> cannonPosAndDir = this.getCannonPosAndDirection(1.0F);
      Vec3 pos = (Vec3)cannonPosAndDir.first;
      Vec3 dir = (Vec3)cannonPosAndDir.second;
      Vec3 left = dir.cross(new Vec3(0.0, 1.0, 0.0));
      Vec3 targetPos = this.getTargetPos(target);
      Vec3 between = targetPos.subtract(pos);
      Vec3 nb = between.normalize();
      double sideCheck = left.dot(nb);
      double upOrDown = nb.dot(new Vec3(0.0, 1.0, 0.0));
      Vec3 nbxz = new Vec3(Math.sqrt(nb.x * nb.x + nb.z * nb.z), nb.y, 0.0);
      Vec3 dirxz = new Vec3(Math.sqrt(dir.x * dir.x + dir.z * dir.z), dir.y, 0.0);
      double verticalAngle = FDMathUtil.angleBetweenVectors(nbxz, dirxz);
      double horizontalAngle = FDMathUtil.angleBetweenVectors(nb.multiply(1.0, 0.0, 1.0), dir.multiply(1.0, 0.0, 1.0));
      if (sideCheck > 0.0) {
         horizontalAngle = -horizontalAngle;
      }

      if (!this.isLeft && upOrDown < 0.0 || this.isLeft && upOrDown > 0.0) {
         verticalAngle = -verticalAngle;
      }

      return new Pair((float)Math.toDegrees(horizontalAngle), (float)Math.toDegrees(verticalAngle));
   }

   private Pair<Vec3, Vec3> getCannonPosAndDirection(float pticks) {
      RaidBlimp blimp = this.getOwner().getRaidBlimp();
      Matrix4f transform = blimp.getModelPartTransformation(blimp, this.bone, RaidBlimp.getModel(blimp));
      Vector3f cannonPosF = transform.transformPosition(new Vector3f());
      Vector3f cannonDirectionF = transform.transformDirection(new Vector3f(1.0F, 0.0F, 0.0F));
      Vec3 cannonPos = new Vec3(cannonPosF.x, cannonPosF.y, cannonPosF.z).add(this.getOwner().getRaidBlimp().getPosition(pticks));
      Vec3 cannonDir = new Vec3(cannonDirectionF.x, cannonDirectionF.y, cannonDirectionF.z);
      if (this.isLeft) {
         cannonDir = cannonDir.reverse();
      }

      return new Pair(cannonPos, cannonDir);
   }

   public void setTarget(LivingEntity target) {
      if (target != null) {
         this.shootCooldown = 10;
         this.target = target;
         this.getOwner().getRaidBlimp().getEntityData().set(this.clientTarget, target.getId());
      } else {
         this.target = null;
         this.getOwner().getRaidBlimp().getEntityData().set(this.clientTarget, -1);
      }
   }

   private Vec3 getTargetPos(LivingEntity livingEntity) {
      return livingEntity.getEyePosition();
   }

   public LivingEntity getTarget() {
      Level level = this.owner.getRaidBlimp().level();
      if (!level.isClientSide) {
         return this.target;
      } else {
         return level.getEntity((Integer)this.owner.getRaidBlimp().getEntityData().get(this.clientTarget)) instanceof LivingEntity livingEntity
            ? livingEntity
            : null;
      }
   }
}
