package com.foxablazeultimate.entity;

import com.foxablazeultimate.ability.skill.hellsniper.HellSniperSkill;
import com.foxablazeultimate.config.HellSniperConfig;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.entity.magic.beam.BeamProjectile;
import java.awt.Color;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public final class HellSniperBeamEntity extends BeamProjectile {
   private static final double MIN_VECTOR_LENGTH_SQR = 1.0E-8;
   private static final double TARGET_HITBOX_INFLATION = 0.05;
   private static final double NEEDLE_VISUAL_LENGTH = 1.75;
   private static final int MIN_LIFETIME_TICKS = 10;
   private static final int LIFETIME_PADDING_TICKS = 10;
   private static final ExplosionDamageCalculator TERRAIN_ONLY_EXPLOSION = new ExplosionDamageCalculator() {
      public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
         return false;
      }
   };
   private UUID targetId;
   private boolean impacted;
   private boolean visualOnly;
   private float impactDamage;
   private float impactExplosionPower;
   private boolean impactDestroysTerrain;
   private double flightSpeed = 2.0;
   private double travelledDistance;
   private Vec3 launchOrigin = Vec3.ZERO;

   public HellSniperBeamEntity(EntityType<? extends HellSniperBeamEntity> entityType, Level level) {
      super(entityType, level);
      this.setNoGravity(true);
      this.noPhysics = true;
      this.setSize(0.08F);
      this.setVisualSize(0.08F);
      HellSniperConfig.HellSniperSettings config = HellSniperConfig.settings();
      this.flightSpeed = sanitizeSpeed(config.projectileSpeed);
      this.setRange((float)Math.max(1.0, config.targetingRange));
      this.setLife(calculateLifetime(this.getRange(), this.flightSpeed));
      this.beamColorAndSize.put(new Color(255, 255, 255, 255), 0.08F);
   }

   public HellSniperBeamEntity(EntityType<? extends HellSniperBeamEntity> entityType, ServerLevel level, LivingEntity owner, LivingEntity target) {
      this(entityType, level);
      this.setOwner(owner);
      this.setTarget(target);
      this.prepareFlight(owner, target);
   }

   public void setTarget(LivingEntity target) {
      this.targetId = target == null ? null : target.getUUID();
   }

   public Optional<UUID> getTargetId() {
      return Optional.ofNullable(this.targetId);
   }

   public void setVisualOnly(boolean visualOnly) {
      this.visualOnly = visualOnly;
   }

   public void initialize(
      LivingEntity owner,
      LivingEntity target,
      ManasSkillInstance instance,
      TensuraSkill skill,
      int mode,
      float damage,
      float explosionPower,
      boolean destroysTerrain
   ) {
      this.initialize(
         owner,
         target,
         instance,
         skill,
         mode,
         damage,
         explosionPower,
         destroysTerrain,
         HellSniperCircleEntity.calculateLaunchPosition(owner, HellSniperConfig.settings().projectileLaunchDistance)
      );
   }

   public void initialize(
      LivingEntity owner,
      LivingEntity target,
      ManasSkillInstance instance,
      TensuraSkill skill,
      int mode,
      float damage,
      float explosionPower,
      boolean destroysTerrain,
      Vec3 launchPosition
   ) {
      this.setOwner(owner);
      this.setTarget(target);
      this.setSkill(owner, instance, skill, mode);
      this.setMode(mode);
      HellSniperConfig.HellSniperSettings config = HellSniperConfig.settings();
      boolean infinite = mode == 1;
      this.setMpCost(config.magiculeCost);
      this.impactDamage = Math.max(0.0F, damage);
      this.impactExplosionPower = Math.max(0.0F, explosionPower);
      this.impactDestroysTerrain = destroysTerrain;
      this.setDamage(this.impactDamage);
      this.prepareFlight(
         target, launchPosition, infinite ? config.infiniteTargetingRange + HellSniperSkill.getInfiniteFormationRadius() : config.targetingRange
      );
   }

   private void prepareFlight(LivingEntity owner, LivingEntity target) {
      this.prepareFlight(
         target,
         HellSniperCircleEntity.calculateLaunchPosition(owner, HellSniperConfig.settings().projectileLaunchDistance),
         HellSniperConfig.settings().targetingRange
      );
   }

   private void prepareFlight(LivingEntity target, Vec3 launchPosition, double requestedRange) {
      HellSniperConfig.HellSniperSettings config = HellSniperConfig.settings();
      this.flightSpeed = sanitizeSpeed(config.projectileSpeed);
      this.setRange((float)Math.max(1.0, requestedRange));
      this.setLife(calculateLifetime(this.getRange(), this.flightSpeed));
      this.setAge(0);
      this.travelledDistance = 0.0;
      this.impacted = false;
      Vec3 launchPos = launchPosition == null ? targetCenter(target) : launchPosition;
      this.launchOrigin = launchPos;
      this.setPos(launchPos);
      Vec3 movement = this.movementToward(targetCenter(target));
      this.setDeltaMovement(movement);
      this.updateRotation(movement);
      this.setInitialVisualEnd(visualEnd(launchPos, movement));
      this.hasImpulse = true;
   }

   public void tick() {
      this.baseTick();
      if (!this.isRemoved()) {
         if (this.level().isClientSide()) {
            this.tickClientPrediction();
         } else {
            this.setAge(this.getAge() + 1);
            if (!this.impacted && (this.getLife() == -1 || this.getAge() <= this.getLife())) {
               if (this.getOwner() instanceof LivingEntity owner && owner.isAlive() && !owner.isRemoved() && owner.level() == this.level()) {
                  LivingEntity target = this.resolveTarget();
                  if (!this.isValidTarget(owner, target)) {
                     this.discard();
                  } else {
                     Vec3 from = this.position();
                     Vec3 targetPos = targetCenter(target);
                     Vec3 movement = this.movementToward(targetPos);
                     this.setDeltaMovement(movement);
                     this.updateRotation(movement);
                     this.hasImpulse = true;
                     Vec3 to = from.add(movement);
                     BlockHitResult blockHit = this.level().clip(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this));
                     Optional<Vec3> targetHit = clipTarget(target, from, to);
                     if (targetHit.isPresent() && isTargetHitFirst(from, targetHit.get(), blockHit)) {
                        this.impactTarget(owner, target, targetHit.get());
                     } else if (blockHit.getType() != Type.MISS) {
                        this.impactBlock(owner, blockHit.getLocation());
                     } else {
                        this.setPos(to);
                        this.travelledDistance = this.travelledDistance + movement.length();
                        this.updateVisualEnd(visualEnd(to, movement));
                        if (this.travelledDistance > Math.max(1.0, this.getRange())) {
                           this.discard();
                        }
                     }
                  }
               } else {
                  this.discard();
               }
            } else {
               this.discard();
            }
         }
      }
   }

   private void tickClientPrediction() {
      Vec3 movement = this.getDeltaMovement();
      if (!(movement.lengthSqr() <= 1.0E-8)) {
         this.updateRotation(movement);
         Vec3 next = this.position().add(movement);
         this.setPos(next);
         this.updateVisualEnd(visualEnd(next, movement));
      }
   }

   private LivingEntity resolveTarget() {
      if (this.targetId != null && this.level() instanceof ServerLevel server) {
         return server.getEntity(this.targetId) instanceof LivingEntity living ? living : null;
      } else {
         return null;
      }
   }

   private boolean isValidTarget(LivingEntity owner, LivingEntity target) {
      if (!HellSniperSkill.isEligibleTarget(owner, target)) {
         return false;
      }

      double range = Math.max(1.0, this.getRange());
      return this.launchOrigin.distanceToSqr(targetCenter(target)) <= range * range;
   }

   private Vec3 movementToward(Vec3 targetPos) {
      Vec3 direction = targetPos.subtract(this.position());
      double distance = direction.length();
      return distance < 1.0E-6 ? Vec3.ZERO : direction.scale(Math.min(this.flightSpeed, distance) / distance);
   }

   private static Optional<Vec3> clipTarget(LivingEntity target, Vec3 from, Vec3 to) {
      AABB hitbox = target.getBoundingBox().inflate(0.05);
      return hitbox.contains(from) ? Optional.of(from) : hitbox.clip(from, to);
   }

   private static boolean isTargetHitFirst(Vec3 from, Vec3 targetHit, BlockHitResult blockHit) {
      return blockHit.getType() == Type.MISS || from.distanceToSqr(targetHit) <= from.distanceToSqr(blockHit.getLocation()) + 1.0E-7;
   }

   private void impactTarget(LivingEntity owner, LivingEntity target, Vec3 hitPos) {
      if (!this.impacted) {
         this.impacted = true;
         this.setPos(hitPos);
         if (!this.visualOnly) {
            target.hurt(this.getDamageSource(), this.impactDamage);
            this.explodeTerrain(owner, targetCenter(target));
         }

         this.discard();
      }
   }

   private void impactBlock(LivingEntity owner, Vec3 hitPos) {
      if (!this.impacted) {
         this.impacted = true;
         this.setPos(hitPos);
         if (!this.visualOnly) {
            this.explodeTerrain(owner, hitPos);
         }

         this.discard();
      }
   }

   private void explodeTerrain(LivingEntity owner, Vec3 pos) {
      if (!(this.impactExplosionPower <= 0.0F)) {
         this.level()
            .explode(
               owner,
               null,
               TERRAIN_ONLY_EXPLOSION,
               pos,
               this.impactExplosionPower,
               false,
               this.impactDestroysTerrain ? ExplosionInteraction.TNT : ExplosionInteraction.NONE
            );
      }
   }

   private static Vec3 targetCenter(LivingEntity target) {
      return new Vec3(target.getX(), target.getY(0.5), target.getZ());
   }

   private static double sanitizeSpeed(double speed) {
      return Double.isFinite(speed) && speed > 0.01 ? speed : 2.0;
   }

   private static int calculateLifetime(double range, double speed) {
      return Math.max(10, (int)Math.ceil(Math.max(1.0, range) / sanitizeSpeed(speed)) + 10);
   }

   private static Vec3 visualEnd(Vec3 position, Vec3 movement) {
      return movement.lengthSqr() <= 1.0E-8 ? position : position.add(movement.normalize().scale(1.75));
   }

   private void updateRotation(Vec3 movement) {
      if (!(movement.lengthSqr() <= 1.0E-8)) {
         double horizontal = movement.horizontalDistance();
         this.setYRot((float)(Math.atan2(movement.x, movement.z) * 180.0 / Math.PI));
         this.setXRot((float)(Math.atan2(movement.y, horizontal) * 180.0 / Math.PI));
      }
   }

   private void setInitialVisualEnd(Vec3 end) {
      this.setCollidePos((float)end.x, (float)end.y, (float)end.z);
      this.endPosX = end.x;
      this.endPosY = end.y;
      this.endPosZ = end.z;
      this.prevCollidePosX = end.x;
      this.prevCollidePosY = end.y;
      this.prevCollidePosZ = end.z;
   }

   private void updateVisualEnd(Vec3 end) {
      this.prevCollidePosX = this.getCollideX();
      this.prevCollidePosY = this.getCollideY();
      this.prevCollidePosZ = this.getCollideZ();
      this.setCollidePos((float)end.x, (float)end.y, (float)end.z);
      this.endPosX = end.x;
      this.endPosY = end.y;
      this.endPosZ = end.z;
   }

   protected boolean hitEntity(Entity entity, ProjectileHitResult result) {
      return false;
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      Vec3 movement = new Vec3(packet.getXa(), packet.getYa(), packet.getZa());
      this.setDeltaMovement(movement);
      this.setInitialVisualEnd(visualEnd(this.position(), movement));
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity entity) {
      Entity owner = this.getOwner();
      return new ClientboundAddEntityPacket(this, entity, owner == null ? 0 : owner.getId());
   }

   public ResourceLocation[] getTextureLocation() {
      return null;
   }

   protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.targetId != null) {
         tag.putUUID("HellSniperTarget", this.targetId);
      }

      tag.putBoolean("HellSniperImpacted", this.impacted);
      tag.putBoolean("HellSniperVisualOnly", this.visualOnly);
      tag.putFloat("HellSniperDamage", this.impactDamage);
      tag.putFloat("HellSniperExplosionPower", this.impactExplosionPower);
      tag.putBoolean("HellSniperDestroyTerrain", this.impactDestroysTerrain);
      tag.putDouble("HellSniperFlightSpeed", this.flightSpeed);
      tag.putDouble("HellSniperTravelledDistance", this.travelledDistance);
      tag.putDouble("HellSniperLaunchX", this.launchOrigin.x);
      tag.putDouble("HellSniperLaunchY", this.launchOrigin.y);
      tag.putDouble("HellSniperLaunchZ", this.launchOrigin.z);
   }

   protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.targetId = tag.hasUUID("HellSniperTarget") ? tag.getUUID("HellSniperTarget") : null;
      this.impacted = tag.getBoolean("HellSniperImpacted");
      this.visualOnly = tag.getBoolean("HellSniperVisualOnly");
      this.impactDamage = tag.getFloat("HellSniperDamage");
      this.impactExplosionPower = tag.getFloat("HellSniperExplosionPower");
      this.impactDestroysTerrain = tag.getBoolean("HellSniperDestroyTerrain");
      if (tag.contains("HellSniperFlightSpeed")) {
         this.flightSpeed = sanitizeSpeed(tag.getDouble("HellSniperFlightSpeed"));
      }

      this.travelledDistance = Math.max(0.0, tag.getDouble("HellSniperTravelledDistance"));
      this.launchOrigin = tag.contains("HellSniperLaunchX")
         ? new Vec3(tag.getDouble("HellSniperLaunchX"), tag.getDouble("HellSniperLaunchY"), tag.getDouble("HellSniperLaunchZ"))
         : this.position();
      this.setDamage(this.impactDamage);
   }
}
