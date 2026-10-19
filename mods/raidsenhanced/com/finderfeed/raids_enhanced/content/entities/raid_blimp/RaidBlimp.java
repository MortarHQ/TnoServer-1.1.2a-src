package com.finderfeed.raids_enhanced.content.entities.raid_blimp;

import com.finderfeed.fdlib.nbt.AutoSerializable;
import com.finderfeed.fdlib.nbt.SerializableField;
import com.finderfeed.fdlib.systems.bedrock.animations.Animation;
import com.finderfeed.fdlib.systems.bedrock.animations.Animation.LoopMode;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationTicker;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.finderfeed.fdlib.util.FDTargetFinder;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import com.finderfeed.raids_enhanced.content.entities.FDRaider;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.cannons.RaidBlimpCannonsController;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.navigation.RaidBlimpMoveControl;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.navigation.RaidBlimpPathNavigation;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.raid_airship_parts.RaidBlimpPart;
import com.finderfeed.raids_enhanced.init.REAnimations;
import com.finderfeed.raids_enhanced.init.REModels;
import com.finderfeed.raids_enhanced.init.RESounds;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.event.EventHooks;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RaidBlimp extends FDRaider implements AutoSerializable {
   public static final int HEIGHT_ABOVE_GROUND = 25;
   @SerializableField
   private boolean shouldDropLoot = false;
   private static FDModel clientModel;
   private static FDModel serverModel;
   private Entity lastHurtBy;
   public static final String BOMB_ILLAGER_LAYER = "ropes_bombs_its_yours_my_friend";
   private static final EntityDataAccessor<Integer> targetRight1 = SynchedEntityData.defineId(RaidBlimp.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> targetRight2 = SynchedEntityData.defineId(RaidBlimp.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> targetRight3 = SynchedEntityData.defineId(RaidBlimp.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> targetLeft1 = SynchedEntityData.defineId(RaidBlimp.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> targetLeft2 = SynchedEntityData.defineId(RaidBlimp.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> targetLeft3 = SynchedEntityData.defineId(RaidBlimp.class, EntityDataSerializers.INT);
   public RaidBlimpCannonsController cannonsController;
   private int bombThrowTicker = -1;
   private Vec3 bombThrowPos = null;
   private Vec3 randomFallingDirection;
   private float fallingPower = 1.0F;
   public boolean isMoving = false;
   private boolean isInRaidRadius = false;

   public RaidBlimp(EntityType<? extends FDRaider> type, Level level) {
      super(type, level);
      this.cannonsController = new RaidBlimpCannonsController(this, targetRight1, targetRight2, targetRight3, targetLeft1, targetLeft2, targetLeft3);
      this.moveControl = new RaidBlimpMoveControl(this, 10, false);
      this.getAnimationSystem().startAnimation("IDLE", AnimationTicker.builder((Animation)REAnimations.RAID_AIRSHIP_FLY.get()).build());
      this.getAnimationSystem().startAnimation("IDLE2", AnimationTicker.builder((Animation)REAnimations.RAID_AIRSHIP_IDLE.get()).build());
      this.getAnimationSystem()
         .startAnimation(
            "ropes_bombs_its_yours_my_friend",
            AnimationTicker.builder((Animation)REAnimations.RAID_AIRSHIP_ILLAGER_OBSERVE.get()).setToNullTransitionTime(0).build()
         );
      this.setNoGravity(true);
   }

   protected void registerGoals() {
   }

   public static FDModel getModel(RaidBlimp raidBlimp) {
      if (raidBlimp.level().isClientSide) {
         if (clientModel == null) {
            clientModel = new FDModel(REModels.RAID_BLIMP.get());
         }

         return clientModel;
      } else {
         if (serverModel == null) {
            serverModel = new FDModel(REModels.RAID_BLIMP.get());
         }

         return serverModel;
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (!this.isDeadOrDying()) {
            this.detectEntitiesBeneathAndThrowBomb();
         }

         if (!this.shouldFlyToRaidCenter()) {
            this.lowerBlimpIfNoTargets();
            this.tickPlayersNearby();
            this.flyToHurtBy();
            this.turnToLastHurtBy();
         } else {
            this.lastHurtBy = null;
            this.flyToRaidCenter();
         }
      }

      if (!this.isDeadOrDying()) {
         this.cannonsController.tick();
      }
   }

   private void flyToRaidCenter() {
      BlockPos pos = this.getRaidFlyToPos();
      Vec3 vcenter = pos.getCenter();
      Vec3 between = vcenter.subtract(this.position());
      double length = between.length();
      if (length > 10.0) {
         this.getNavigation().moveTo(vcenter.x, vcenter.y, vcenter.z, 1.0);
      } else {
         this.isInRaidRadius = true;
      }
   }

   private void tickPlayersNearby() {
      if (this.tickCount % 4 == 0) {
         List<Player> players = FDTargetFinder.getEntitiesInCylinder(
            Player.class, this.level(), this.position().add(0.0, -15.0, 0.0), 30.0F, 20.0F, playerx -> !playerx.isCreative() && !playerx.isSpectator()
         );
         if (!players.isEmpty()) {
            Player player = players.get(this.random.nextInt(players.size()));
            this.lastHurtBy = player;
         }
      }
   }

   private void lowerBlimpIfNoTargets() {
      if (!this.isMoving && this.lastHurtBy == null) {
         RaidBlimpCannonsController controller = this.cannonsController;
         if (!controller.hasTargets()) {
            int y = this.level().getHeight(Types.MOTION_BLOCKING, this.getBlockX(), this.getBlockZ()) + 25;
            double ty = this.getY();
            if (ty - y > 5.0) {
               this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.01, 0.0));
            }
         }

         this.setDeltaMovement(this.getDeltaMovement().scale(0.97F));
      }
   }

   private BlockPos getRaidFlyToPos() {
      if (this.raid != null) {
         BlockPos blockPos = this.raid.getCenter();
         int y = this.level().getHeight(Types.MOTION_BLOCKING, blockPos.getX(), blockPos.getZ()) + 25;
         return new BlockPos(blockPos.getX(), y, blockPos.getZ());
      } else {
         return null;
      }
   }

   private boolean shouldFlyToRaidCenter() {
      if (this.raid != null && this.raid.isActive()) {
         BlockPos center = this.getRaidFlyToPos();
         Vec3 vcenter = center.getCenter();
         Vec3 between = vcenter.subtract(this.position());
         double length = between.length();
         if (length > 50.0) {
            this.isInRaidRadius = false;
            return true;
         } else {
            return !this.isInRaidRadius;
         }
      } else {
         return false;
      }
   }

   private void flyToHurtBy() {
      if (this.getLastHurtBy() != null && this.tickCount % 5 == 0) {
         Entity entity = this.getLastHurtBy();
         if (this.distanceTo(entity) > 200.0F) {
            this.lastHurtBy = null;
         }

         Vec3 pos = entity.position();
         Vec3 b = pos.subtract(this.position()).multiply(1.0, 0.0, 1.0);
         double yDiff = this.getY() - entity.getY();
         double hdist = b.length();
         float absYDiff = (float)Math.abs(yDiff);
         if (hdist > 30.0 || absYDiff > 25.0F || absYDiff < 5.0F) {
            Vec3 nb = b.normalize();
            Vec3 movePos = pos.add(nb.reverse().scale(Math.min(hdist, 10.0)));
            double height = pos.y + 25.0;
            movePos = this.getMovePos(movePos.x, height, movePos.z);
            this.getNavigation().moveTo(movePos.x, movePos.y, movePos.z, 1.0);
         }
      }
   }

   public Vec3 getMovePos(double x, double y, double z) {
      int height = this.level().getHeight(Types.MOTION_BLOCKING, (int)Math.round(x), (int)Math.round(z));
      float resultHeight = (float)Math.max(y, height + 25);
      return new Vec3(x, resultHeight, z);
   }

   private void turnToLastHurtBy() {
      if (!this.isDeadOrDying()) {
         if (this.getLastHurtBy() != null && !this.isMoving) {
            Entity entity = this.getLastHurtBy();
            Vec3 pos = entity.position();
            if (this.distanceTo(entity) > 40.0F) {
               return;
            }

            double d0 = pos.x - this.getX();
            double d1 = pos.y - this.getY();
            double d2 = pos.z - this.getZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d3 >= 2.5000003E-7F) {
               float f = (float)(Mth.atan2(d2, d0) * 180.0 / (float) Math.PI);
               this.setYRot(this.rotlerp(this.getYRot(), f, 2.0F));
            }

            Vec3 b = pos.subtract(this.position()).multiply(1.0, 0.0, 1.0).normalize();
            Vec3 l = this.getLookAngle().multiply(1.0, 0.0, 1.0).normalize();
            double angle = FDMathUtil.angleBetweenVectors(b, l);
            if (angle >= 1.3744469F && angle <= 1.7671459F) {
               this.lastHurtBy = null;
            }
         }
      }
   }

   protected float rotlerp(float p_24992_, float p_24993_, float p_24994_) {
      float f = Mth.wrapDegrees(p_24993_ - p_24992_);
      if (f > p_24994_) {
         f = p_24994_;
      }

      if (f < -p_24994_) {
         f = -p_24994_;
      }

      float f1 = p_24992_ + f;
      if (f1 < 0.0F) {
         f1 += 360.0F;
      } else if (f1 > 360.0F) {
         f1 -= 360.0F;
      }

      return f1;
   }

   public void setLastHurtBy(Entity lastHurtBy) {
      this.lastHurtBy = lastHurtBy;
   }

   public Entity getLastHurtBy() {
      return this.lastHurtBy;
   }

   public boolean hurt(DamageSource src, float damage) {
      boolean result;
      if (result = super.hurt(src, damage) && src.getEntity() != null) {
         if (this.distanceTo(src.getEntity()) < 40.0F) {
            Entity entity = src.getEntity();
            Vec3 pos = entity.position();
            Vec3 relativePos = pos.subtract(this.position());
            Vec3 rotated = relativePos.yRot((float)Math.toRadians(this.getYRot()));
            if (Math.abs(rotated.x) < 8.0) {
               this.setLastHurtBy(src.getEntity());
            }
         } else if (this.distanceTo(src.getEntity()) < 200.0F) {
            this.setLastHurtBy(src.getEntity());
         }
      }

      return result;
   }

   protected boolean shouldDropLoot() {
      return this.shouldDropLoot && super.shouldDropLoot();
   }

   public boolean checkTargetClass(Entity target) {
      return target instanceof AbstractVillager
         || target instanceof Player player && !player.isSpectator() && !player.isCreative()
         || target instanceof IronGolem;
   }

   protected void tickDeath() {
      this.deathTime++;
      if (!this.level().isClientSide()) {
         if (this.deathTime == 1) {
            this.level().playSound(null, this.position().x, this.position().y, this.position().z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 5.0F, 1.0F);
            this.level().playSound(null, this.position().x, this.position().y, this.position().z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 5.0F, 0.5F);
            this.level()
               .playSound(null, this.position().x, this.position().y, this.position().z, RESounds.RAID_BLIMP_FALL.get(), SoundSource.HOSTILE, 5.0F, 1.1F);
         }

         this.getAnimationSystem().stopAnimation("ropes_bombs_its_yours_my_friend");
         this.getAnimationSystem().startAnimation("IDLE", AnimationTicker.builder(REAnimations.RAID_AIRSHIP_DEATH).build());
         if (this.randomFallingDirection == null) {
            this.fallingPower = 1.0F;
            this.randomFallingDirection = new Vec3(1.0, 0.0, 0.0).yRot(this.random.nextFloat() * (float) Math.PI * 2.0F);
         }

         this.fallingPower = Mth.clamp(this.fallingPower - 0.05F, -0.25F, 1.0F);
         Vec3 deltaMovement = this.getDeltaMovement();
         Vec3 newDeltaMovement = new Vec3(
            deltaMovement.x + this.randomFallingDirection.x * this.fallingPower * 0.1,
            -0.8,
            deltaMovement.z + this.randomFallingDirection.z * this.fallingPower * 0.1
         );
         this.setDeltaMovement(newDeltaMovement);
         if (this.onGround() || this.deathTime > 300) {
            this.spawnDeathParts();
            DamageSource damageSource = this.getLastDamageSource();
            if (damageSource == null) {
               damageSource = this.level().damageSources().generic();
            }

            this.shouldDropLoot = true;
            this.dropAllDeathLoot((ServerLevel)this.level(), damageSource);
            this.explode();
         }
      } else {
         this.deathClientParticles();
      }
   }

   private void spawnDeathParts() {
      for (int i = 0; i < 30; i++) {
         Vec3 v = new Vec3(1.0, 0.0, 0.0).yRot(this.random.nextFloat() * (float) Math.PI * 2.0F);
         Vec3 speed = v.scale(0.1F + this.random.nextFloat() * 1.0F).add(0.0, 0.15 + this.random.nextFloat() * 1.25F, 0.0);
         Vec3 startPos = this.position().add(v.scale(this.random.nextFloat() * 3.0F));
         RaidBlimpPart.summon(this.level(), startPos, speed, i == 0 ? 2 : 1, this.random.nextInt(200) + 400);
      }
   }

   private void deathClientParticles() {
      if (this.deathTime == 1) {
         for (int i = 0; i < 3; i++) {
            Vec3 ppos = this.position().add(this.random.nextFloat() * 5.0F - 2.5, this.random.nextFloat() * 4.0F, this.random.nextFloat() * 5.0F - 2.5);
            this.level().addParticle(ParticleTypes.EXPLOSION_EMITTER, true, ppos.x, ppos.y, ppos.z, 0.0, 0.0, 0.0);
         }
      }

      for (int i = 0; i < 10; i++) {
         Vec3 ppos = this.position().add(this.random.nextFloat() * 4.0F - 2.0F, this.random.nextFloat() * 4.0F, this.random.nextFloat() * 4.0F - 2.0F);
         this.level()
            .addParticle(
               ParticleTypes.CAMPFIRE_COSY_SMOKE,
               true,
               ppos.x,
               ppos.y,
               ppos.z,
               this.random.nextFloat() * 0.2F - 0.1F,
               this.random.nextFloat() * 0.2F - 0.1F,
               this.random.nextFloat() * 0.2F - 0.1F
            );
      }

      for (int i = 0; i < 10; i++) {
         Vec3 ppos = this.position().add(this.random.nextFloat() * 4.0F - 2.0F, this.random.nextFloat() * 4.0F, this.random.nextFloat() * 4.0F - 2.0F);
         this.level()
            .addParticle(
               ParticleTypes.LARGE_SMOKE,
               true,
               ppos.x,
               ppos.y,
               ppos.z,
               this.random.nextFloat() * 0.2F - 0.1F,
               this.random.nextFloat() * 0.2F - 0.1F,
               this.random.nextFloat() * 0.2F - 0.1F
            );
      }
   }

   private void explode() {
      this.level().playSound(null, this.position().x, this.position().y, this.position().z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 5.0F, 1.0F);
      this.level().playSound(null, this.position().x, this.position().y, this.position().z, RESounds.RAID_BLIMP_EXPLODE.get(), SoundSource.HOSTILE, 5.0F, 1.0F);
      Vec3 pos = this.position();

      for (ServerPlayer serverPlayer : FDTargetFinder.getEntitiesInSphere(ServerPlayer.class, this.level(), pos, 160.0F)) {
         for (int i = 0; i < 5; i++) {
            Vec3 ppos = this.position().add(this.random.nextFloat() * 8.0F - 4.0F, this.random.nextFloat() * 4.0F, this.random.nextFloat() * 8.0F - 4.0F);
            ((ServerLevel)this.level()).sendParticles(serverPlayer, ParticleTypes.EXPLOSION_EMITTER, true, ppos.x, ppos.y, ppos.z, 1, 0.0, 0.0, 0.0, 0.0);
         }
      }

      this.setRemoved(RemovalReason.DISCARDED);
   }

   private void detectEntitiesBeneathAndThrowBomb() {
      if (this.bombThrowTicker >= 0) {
         if (this.bombThrowTicker > 10) {
            this.getAnimationSystem()
               .startAnimation(
                  "ropes_bombs_its_yours_my_friend",
                  AnimationTicker.builder(REAnimations.RAID_AIRSHIP_THROW_BOMB).setToNullTransitionTime(0).setLoopMode(LoopMode.HOLD_ON_LAST_FRAME).build()
               );
         }

         if (this.bombThrowTicker == 13) {
            Matrix4f mat = this.getModelPartTransformation(this, "pillager_bomb", getModel(this), 1.0F);
            Vec3 t = new Vec3(mat.transformPosition(new Vector3f(0.0F, 0.0F, 0.0F))).add(this.position());
            if (this.bombThrowPos == null || this.bombThrowPos.distanceTo(this.position()) > 200.0) {
               this.bombThrowPos = this.position().add(0.0, -40.0, 0.0);
            }

            Vec3 throwPos = this.bombThrowPos.add(this.level().random.nextGaussian() * 0.1, 0.0, this.level().random.nextGaussian() * 0.1);
            double startSpeed = -1.5;
            Vec3 hb = this.calculateBombHorizontalSpeed(t, throwPos, startSpeed, 0.08);
            Vec3 speed = new Vec3(hb.x, startSpeed, hb.z);
            RaiderBomb.summon(this, t, speed);
         }

         this.bombThrowTicker--;
      } else {
         this.getAnimationSystem()
            .startAnimation(
               "ropes_bombs_its_yours_my_friend", AnimationTicker.builder(REAnimations.RAID_AIRSHIP_ILLAGER_OBSERVE).setToNullTransitionTime(0).build()
            );
         if (this.tickCount % 5 == 0) {
            float downDistance = 40.0F;
            List<LivingEntity> targets = FDTargetFinder.getEntitiesInCylinder(
               LivingEntity.class,
               this.level(),
               this.position().add(0.0, -downDistance, 0.0),
               downDistance,
               5.0F,
               entity -> {
                  if (entity.isDeadOrDying() || entity == this) {
                     return false;
                  } else if (this.checkTargetClass(entity)) {
                     ClipContext clipContext = new ClipContext(
                        this.position(), entity.position().add(0.0, entity.getBbHeight() / 2.0F, 0.0), Block.COLLIDER, Fluid.NONE, CollisionContext.empty()
                     );
                     BlockHitResult res = this.level().clip(clipContext);
                     return res.getType() == Type.MISS;
                  } else {
                     return false;
                  }
               }
            );
            if (!targets.isEmpty()) {
               this.bombThrowPos = targets.get(this.level().random.nextInt(targets.size())).position();
               this.bombThrowTicker = ((Animation)REAnimations.RAID_AIRSHIP_THROW_BOMB.get()).getAnimTime();
            }
         }
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.autoLoad(tag);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      this.autoSave(tag);
   }

   private Vec3 calculateBombHorizontalSpeed(Vec3 startPos, Vec3 endPos, double startVerticalSpeed, double gravity) {
      double dist = startPos.y - endPos.y;
      if (dist < 0.0) {
         return endPos.subtract(startPos).multiply(1.0, 0.0, 1.0).scale(0.1F);
      }

      if (gravity > 0.0) {
         gravity = -gravity;
      }

      if (startVerticalSpeed > 0.0) {
         startVerticalSpeed = -startVerticalSpeed;
      }

      double d = startVerticalSpeed * startVerticalSpeed - 4.0 * gravity * dist;
      double time = (-startVerticalSpeed - Math.sqrt(d)) / (2.0 * gravity);
      if (time <= 0.0) {
         return endPos.subtract(startPos).multiply(1.0, 0.0, 1.0).scale(0.1F);
      }

      double hdist = Math.sqrt(Math.pow(endPos.x - startPos.x, 2.0) + Math.pow(endPos.z - startPos.z, 2.0));
      double hspeed = hdist / time;
      return endPos.subtract(startPos).multiply(1.0, 0.0, 1.0).normalize().scale(hspeed);
   }

   public boolean isNoGravity() {
      return true;
   }

   protected double getDefaultGravity() {
      return 0.0;
   }

   protected PathNavigation createNavigation(Level p_186262_) {
      RaidBlimpPathNavigation flyingpathnavigation = new RaidBlimpPathNavigation(this, p_186262_);
      flyingpathnavigation.setCanOpenDoors(false);
      flyingpathnavigation.setCanFloat(true);
      flyingpathnavigation.setCanPassDoors(true);
      return flyingpathnavigation;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(targetRight1, -1);
      builder.define(targetRight2, -1);
      builder.define(targetRight3, -1);
      builder.define(targetLeft1, -1);
      builder.define(targetLeft2, -1);
      builder.define(targetLeft3, -1);
   }

   public void applyRaidBuffs(ServerLevel level, int p_37844_, boolean p_37845_) {
   }

   public SoundEvent getCelebrateSound() {
      return null;
   }

   public void push(double p_20286_, double p_20287_, double p_20288_) {
   }

   public void push(Vec3 p_347665_) {
   }

   public void push(Entity p_21294_) {
   }

   protected void pushEntities() {
   }

   public void knockback(double p_147241_, double p_147242_, double p_147243_) {
   }

   protected void playBlockFallSound() {
   }

   protected int calculateFallDamage(float p_21237_, float p_21238_) {
      return 0;
   }

   protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
   }

   public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
      return false;
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   public void checkDespawn() {
      if (!EventHooks.checkMobDespawn(this)) {
         if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
         } else {
            this.noActionTime = 0;
         }
      }
   }
}
