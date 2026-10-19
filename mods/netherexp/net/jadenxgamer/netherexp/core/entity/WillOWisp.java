package net.jadenxgamer.netherexp.core.entity;

import java.util.ArrayList;
import java.util.List;
import net.jadenxgamer.elysium_api.api.client.sound.LoopedEntityBoundSoundInstance;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.config.enums.BansheeRedirectConfig;
import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.AdvancementGranter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

public class WillOWisp extends ThrowableItemProjectile {
   private static final float INITIAL_SPEED_BPS = 0.1F;
   private static final float MAX_SPEED_BPS = 16.0F;
   private static final float WIND_PROPELLED_SPEED_BPS = 26.0F;
   private static final float ACCELERATION_TIME = 5.0F;
   private static final float VELOCITY_SMOOTHING = 0.3F;
   private static final int MAX_LIFETIME_TICKS = 300;
   private static final float[][] BASE_TURN_CURVE = new float[][]{{0.0F, 1.8F}, {0.2F, 2.0F}, {0.4F, 2.2F}, {0.6F, 2.4F}, {0.8F, 2.6F}, {1.0F, 2.8F}};
   private static final List<WillOWisp.TurnPoint> STATIC_TURN_CURVE = new ArrayList<>();
   public final AnimationState loopAnimation = new AnimationState();
   private LivingEntity target;
   private float currentSpeed;
   private int damage = 3;
   private SoundInstance soundInstance;
   private Vec3 currentDirection;
   private Vec3 smoothedDirection;
   private Vec3 lastParticlePos;
   private double distanceSinceLastParticle = 0.0;
   private boolean particleTrailInitialized = false;
   private float manoeuvrability = (float)JNEConfigs.GENERIC_WILL_O_WISP_MANEUVERABILITY.getAsDouble();
   private static final EntityDataAccessor<Boolean> WIND_PROPELLED;

   public WillOWisp(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
      this.noPhysics = true;
      this.initialize();
   }

   public WillOWisp(LivingEntity shooter, Level level, LivingEntity target) {
      this(shooter, level, target, shooter.getX(), shooter.getY() + 1.0, shooter.getZ(), 3);
   }

   public WillOWisp(LivingEntity shooter, Level level, LivingEntity target, double x, double y, double z, int damage) {
      super(JNEEntityType.WILL_O_WISP.get(), shooter, level);
      this.noPhysics = true;
      this.target = target;
      this.damage = damage;
      this.setPos(x, y, z);
      this.initialize();
      this.initializeDirection(shooter);
   }

   public void handleEntityEvent(byte id) {
      if (id == 40) {
         this.impactParticle(this.level(), this.position());
      }

      if (id == 41) {
         this.level().addParticle(ParticleTypes.GUST, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
      }

      super.handleEntityEvent(id);
   }

   private void initialize() {
      this.currentSpeed = 0.005F;
      this.currentDirection = new Vec3(1.0, 0.0, 0.0);
      this.smoothedDirection = this.currentDirection;
      this.particleTrailInitialized = false;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(WIND_PROPELLED, false);
   }

   private void initializeDirection(LivingEntity shooter) {
      Vec3 lookAngle = shooter.getLookAngle().normalize();
      if (this.target != null && this.target.isAlive()) {
         Vec3 toTarget = new Vec3(this.target.getX() - this.getX(), this.target.getY() + 1.0 - this.getY(), this.target.getZ() - this.getZ());
         if (toTarget.lengthSqr() > 0.001) {
            this.currentDirection = lookAngle.scale(0.3).add(toTarget.normalize().scale(0.7)).normalize();
         } else {
            this.currentDirection = lookAngle;
         }
      } else {
         this.currentDirection = lookAngle;
      }

      this.smoothedDirection = this.currentDirection;
      this.setDeltaMovement(this.currentDirection.scale(this.currentSpeed));
      this.updateRotationFromDirection();
   }

   private void updateRotationFromDirection() {
      double horizontalDistance = Math.sqrt(this.currentDirection.x * this.currentDirection.x + this.currentDirection.z * this.currentDirection.z);
      this.setYRot((float)(Math.atan2(this.currentDirection.x, this.currentDirection.z) * (180.0 / Math.PI)));
      this.setXRot((float)(Math.atan2(this.currentDirection.y, horizontalDistance) * (180.0 / Math.PI)));
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
   }

   public void tick() {
      super.tick();
      if (this.getOwner() != null && !this.getOwner().isAlive()) {
         this.impact(this.position());
      }

      if ((Boolean)this.entityData.get(WIND_PROPELLED)) {
         this.currentSpeed = 1.3F;
         if (this.level().isClientSide() && this.tickCount % 4 == 0) {
            WillOWisp.Client.windTrailParticle(this.level(), this.getX(), this.getY(), this.getZ());
         }
      } else {
         this.checkWindChargeCollision();
         float ageInSeconds = this.tickCount / 20.0F;
         float accelerationRate = 0.00795F;
         if (ageInSeconds < 5.0F) {
            this.currentSpeed = Math.min(this.currentSpeed + accelerationRate, 0.8F);
         }
      }

      if (this.target != null && this.target.isAlive()) {
         this.updateDirection();
      }

      Vec3 targetVelocity = this.smoothedDirection.scale(this.currentSpeed);
      Vec3 currentVelocity = this.getDeltaMovement();
      Vec3 smoothedVelocity = currentVelocity.scale(0.7F).add(targetVelocity.scale(0.3F));
      this.setDeltaMovement(smoothedVelocity);
      this.updateProjectileRotation();
      if (this.level().isClientSide()) {
         if ((Boolean)JNEConfigs.WILL_O_WISP_PARTICLES.get()) {
            this.updateParticleTrail();
         }

         if ((Boolean)JNEConfigs.WILL_O_WISP_SOUNDS.get()) {
            WillOWisp.Client.updateSound(this);
         }

         this.loopAnimation.startIfStopped(this.tickCount);
      } else if (this.tickCount > 300) {
         this.impact(this.position());
      }
   }

   private void checkWindChargeCollision() {
      if (!this.level().isClientSide()) {
         AABB box = this.getBoundingBox().inflate(0.1);

         for (WindCharge windCharge : this.level().getEntitiesOfClass(WindCharge.class, box)) {
            if (windCharge.isAlive()) {
               this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WIND_CHARGE_BURST, SoundSource.NEUTRAL, 1.0F, 1.0F);
               this.level().broadcastEntityEvent(this, (byte)41);
               this.entityData.set(WIND_PROPELLED, true);
               windCharge.discard();
               break;
            }
         }
      }
   }

   private void updateProjectileRotation() {
      Vec3 motion = this.getDeltaMovement();
      if (motion.lengthSqr() > 0.001) {
         double horizontalDistance = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
         float newYRot = (float)(Math.atan2(motion.x, motion.z) * (180.0 / Math.PI));
         float newXRot = (float)(Math.atan2(motion.y, horizontalDistance) * (180.0 / Math.PI));
         this.setYRot(this.lerpProjectileRotation(this.yRotO, newYRot));
         this.setXRot(this.lerpProjectileRotation(this.xRotO, newXRot));
         this.yRotO = this.getYRot();
         this.xRotO = this.getXRot();
      }
   }

   private float lerpProjectileRotation(float current, float target) {
      float difference = target - current;

      while (difference < -180.0F) {
         difference += 360.0F;
      }

      while (difference >= 180.0F) {
         difference -= 360.0F;
      }

      return current + difference * 0.3F;
   }

   private void updateDirection() {
      Vec3 desiredDirection = new Vec3(this.target.getX() - this.getX(), this.target.getY() + 1.0 - this.getY(), this.target.getZ() - this.getZ());
      if (!(desiredDirection.lengthSqr() < 0.001)) {
         desiredDirection = desiredDirection.normalize();
         float turnFactor;
         float effectiveManoeuvrability;
         if ((Boolean)this.entityData.get(WIND_PROPELLED)) {
            turnFactor = 0.95F;
            effectiveManoeuvrability = 1.0F;
         } else {
            turnFactor = this.calculateTurnFactor(this.tickCount / 20.0F);
            effectiveManoeuvrability = this.manoeuvrability;
         }

         this.currentDirection = this.currentDirection.scale(1.0F - turnFactor).add(desiredDirection.scale(turnFactor)).normalize();
         this.smoothedDirection = this.smoothedDirection
            .scale(1.0F - effectiveManoeuvrability)
            .add(this.currentDirection.scale(effectiveManoeuvrability))
            .normalize();
      }
   }

   private float calculateTurnFactor(float time) {
      if (time >= STATIC_TURN_CURVE.getLast().time) {
         return STATIC_TURN_CURVE.getLast().turnFactor;
      }

      for (int i = 0; i < STATIC_TURN_CURVE.size() - 1; i++) {
         WillOWisp.TurnPoint start = STATIC_TURN_CURVE.get(i);
         WillOWisp.TurnPoint end = STATIC_TURN_CURVE.get(i + 1);
         if (time >= start.time && time <= end.time) {
            float segmentProgress = (time - start.time) / (end.time - start.time);
            return start.turnFactor + (end.turnFactor - start.turnFactor) * segmentProgress;
         }
      }

      return STATIC_TURN_CURVE.getFirst().turnFactor;
   }

   protected void onHitEntity(EntityHitResult result) {
      Entity entity = result.getEntity();
      if (!(entity instanceof WindCharge)) {
         if (entity instanceof LivingEntity living) {
            if (this.wasRedirected(living)) {
               switch ((BansheeRedirectConfig)JNEConfigs.BANSHEE_REDIRECT_STUNS.get()) {
                  case STUN:
                     ((Banshee)living).setStunTime((Integer)JNEConfigs.BANSHEE_STUN_TIMER.get());
                     break;
                  case INSTAKILL:
                     living.kill();
               }

               AdvancementGranter.grantPlayersInRadius(this.level(), this.blockPosition(), JNECriteriaTriggers.BANSHEE_REDIRECT);
            }

            if (living.isBlocking()) {
               this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.NEUTRAL, 1.0F, 1.0F);
               living.getUseItem().hurtAndBreak(5, living, LivingEntity.getSlotForHand(living.getUsedItemHand()));
            } else {
               entity.hurt(this.damageSources().source(JNEDamageSources.WILL_O_WISP, this.getOwner()), this.damage);
            }
         }

         this.impact(this.position());
         super.onHitEntity(result);
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      this.impact(this.position());
   }

   private void impact(Vec3 impactPos) {
      if (!this.level().isClientSide()) {
         this.level().broadcastEntityEvent(this, (byte)40);
         this.level().playSound(null, impactPos.x, impactPos.y, impactPos.z, SoundEvents.MUD_PLACE, SoundSource.NEUTRAL, 1.0F, 1.0F);
         this.discard();
      }
   }

   private void impactParticle(Level level, Vec3 pos) {
      WillOWisp.Client.impactParticle(level, pos.x, pos.y, pos.z);
   }

   private void updateParticleTrail() {
      Vec3 currentPos = this.position();
      Vec3 motion = this.getDeltaMovement();
      if (!this.particleTrailInitialized) {
         this.lastParticlePos = motion.lengthSqr() > 0.001 ? currentPos.subtract(motion.normalize().scale(0.2)) : currentPos;
         this.particleTrailInitialized = true;
      } else if (this.lastParticlePos == null) {
         this.lastParticlePos = currentPos;
      } else {
         double distanceMoved = currentPos.distanceTo(this.lastParticlePos);
         this.distanceSinceLastParticle += distanceMoved;
         double particleInterval = 0.5 / (1.0 + this.currentSpeed / 0.8F * 2.0);

         while (this.distanceSinceLastParticle >= particleInterval) {
            this.distanceSinceLastParticle -= particleInterval;
            double distance = currentPos.distanceTo(this.lastParticlePos);
            if (distance < 0.001) {
               this.lastParticlePos = currentPos;
               break;
            }

            Vec3 spawnPos = this.lastParticlePos.add(currentPos.subtract(this.lastParticlePos).scale(particleInterval / distance));
            this.spawnParticleWithOffset(spawnPos, 0.3);
            if (this.currentSpeed > 0.24F) {
               int clusterCount = (int)(this.currentSpeed / 0.8F * 4.0F);

               for (int i = 0; i < clusterCount; i++) {
                  this.spawnParticleWithOffset(spawnPos, 0.15);
               }
            }

            this.lastParticlePos = spawnPos;
         }

         if (this.level().isClientSide()) {
            WillOWisp.Client.spawnSpeedAdjustedTrailParticle(this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), this);
         }
      }
   }

   private void spawnParticleWithOffset(Vec3 pos, double offsetRange) {
      if (this.level().isClientSide()) {
         double offsetX = (this.level().random.nextDouble() - 0.5) * offsetRange;
         double offsetY = (this.level().random.nextDouble() - 0.5) * offsetRange;
         double offsetZ = (this.level().random.nextDouble() - 0.5) * offsetRange;
         WillOWisp.Client.spawnSpeedAdjustedTrailParticle(pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ, this);
      }
   }

   @NotNull
   protected Item getDefaultItem() {
      return JNEItems.WILL_O_WISP.get();
   }

   public boolean isNoGravity() {
      return true;
   }

   private boolean wasRedirected(LivingEntity hitEntity) {
      Entity owner = this.getOwner();
      return owner != null && hitEntity == owner && hitEntity.getType() == JNEEntityType.BANSHEE.get();
   }

   public float getManoeuvrability() {
      return this.manoeuvrability;
   }

   public void setManoeuvrability(float manoeuvrability) {
      this.manoeuvrability = manoeuvrability;
   }

   static {
      for (float[] point : BASE_TURN_CURVE) {
         float time = point[0] * 5.0F;
         float turnFactor = point[1];
         STATIC_TURN_CURVE.add(new WillOWisp.TurnPoint(time, turnFactor));
      }

      STATIC_TURN_CURVE.sort((a, b) -> Float.compare(a.time, b.time));
      WIND_PROPELLED = SynchedEntityData.defineId(WillOWisp.class, EntityDataSerializers.BOOLEAN);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      private static void impactParticle(Level level, double x, double y, double z) {
         WorldParticleBuilder.create(JNEParticleTypes.WILL_O_WISP_IMPACT.get())
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.create(0.0F).build())
            .setScaleData(GenericParticleData.create(0.595F).build())
            .setTransparencyData(GenericParticleData.create(1.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(10)
            .enableNoClip()
            .spawn(level, x, y, z);
      }

      private static void windTrailParticle(Level level, double x, double y, double z) {
         if (level.isClientSide()) {
            WorldParticleBuilder.create(JNEParticleTypes.WIND_TRAIL.get())
               .setFullBrightLighting()
               .setScaleData(GenericParticleData.create(0.2F, 0.6F).setEasing(Easing.SINE_OUT).build())
               .setTransparencyData(GenericParticleData.create(0.3F, 0.0F).build())
               .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
               .setLifetime(20)
               .disableNoClip()
               .spawn(level, x, y, z);
         }
      }

      private static void spawnSpeedAdjustedTrailParticle(double x, double y, double z, WillOWisp willOWisp) {
         if (!(willOWisp.level().random.nextFloat() > 0.3F)) {
            int lifetime = Math.max(1, (int)(6.0F / willOWisp.currentSpeed) + willOWisp.level().random.nextInt(-12, 13));
            lifetime = Math.min(lifetime, 100);
            WorldParticleBuilder.create(JNEParticleTypes.WISP.get())
               .setFullBrightLighting()
               .setScaleData(GenericParticleData.create(0.13F).build())
               .setTransparencyData(GenericParticleData.create(1.0F).build())
               .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
               .setSpritePicker(ParticleSpritePicker.WITH_AGE)
               .setLifetime(lifetime)
               .enableNoClip()
               .spawn(willOWisp.level(), x, y, z);
         }
      }

      private static void updateSound(WillOWisp willOWisp) {
         if (willOWisp.soundInstance == null) {
            willOWisp.soundInstance = new LoopedEntityBoundSoundInstance(
               JNESoundEvents.WILL_O_WISP_AMBIENT.get(), SoundSource.NEUTRAL, 0.5F, 1.0F, willOWisp, 0L
            );
            Minecraft.getInstance().getSoundManager().play(willOWisp.soundInstance);
         }
      }
   }

   private record TurnPoint(float time, float turnFactor) {
   }
}
