package com.github.wal_bos.moreskills.world.entity;

import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.world.entity.feldway.FeldwayAwakeningEvent;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FeldwayAwakeningEntity extends PathfinderMob {
   private static final EntityDataAccessor<Integer> DATA_AWAKENING_AGE = SynchedEntityData.defineId(FeldwayAwakeningEntity.class, EntityDataSerializers.INT);
   private static final int DURATION = 520;
   private final ServerBossEvent bossEvent;
   private UUID targetId = null;
   private boolean capturedStart = false;
   private double startX;
   private double startY;
   private double startZ;
   private long originalDayTime = 0L;
   private long originalGameTime = 0L;
   private boolean originalDaylightRule = true;
   private double visualDayTime = 0.0;
   private boolean restoredTime = false;

   public FeldwayAwakeningEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
      this.bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.RED, BossBarOverlay.PROGRESS);
      this.bossEvent.setDarkenScreen(true);
      this.bossEvent.setPlayBossMusic(true);
      this.setPersistenceRequired();
      this.setNoGravity(true);
      this.xpReward = 0;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_AWAKENING_AGE, 0);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 100000.0)
         .add(Attributes.ARMOR, 100.0)
         .add(Attributes.ARMOR_TOUGHNESS, 100.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.0)
         .add(Attributes.FOLLOW_RANGE, 192.0);
   }

   public void setAwakeningTarget(UUID targetId) {
      this.targetId = targetId;
   }

   public UUID getAwakeningTargetId() {
      return this.targetId;
   }

   public int getAwakeningAge() {
      return (Integer)this.entityData.get(DATA_AWAKENING_AGE);
   }

   private void setAwakeningAge(int age) {
      this.entityData.set(DATA_AWAKENING_AGE, age);
   }

   public int getAwakeningDuration() {
      return 520;
   }

   public float getAwakeningProgress() {
      return Mth.clamp(this.getAwakeningAge() / 520.0F, 0.0F, 1.0F);
   }

   public double getVisualDayTime() {
      return this.visualDayTime;
   }

   public void setVisualDayTime(double visualDayTime) {
      this.visualDayTime = visualDayTime;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public boolean canBeAffected(MobEffectInstance effectInstance) {
      return false;
   }

   public boolean hurt(DamageSource source, float amount) {
      return false;
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return true;
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   protected void registerGoals() {
   }

   public void tick() {
      super.tick();
      this.forceLayingState();
      if (this.level().isClientSide()) {
         this.setNoGravity(true);
         this.setDeltaMovement(Vec3.ZERO);
      } else {
         ServerLevel level = (ServerLevel)this.level();
         if (!this.capturedStart) {
            this.capturedStart = true;
            this.startX = this.getX();
            this.startY = this.getY();
            this.startZ = this.getZ();
            this.originalDayTime = level.getDayTime();
            this.originalGameTime = level.getGameTime();
            this.originalDaylightRule = level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT);
            this.visualDayTime = this.originalDayTime;
         }

         int age = this.getAwakeningAge() + 1;
         this.setAwakeningAge(age);
         float progress = this.getAwakeningProgress();
         float rise = this.riseCurve(progress);
         this.setPos(this.startX, this.startY + rise, this.startZ);
         this.setDeltaMovement(Vec3.ZERO);
         this.setNoGravity(true);
         this.navigation.stop();
         this.setHealth(this.getMaxHealth());
         this.bossEvent.setProgress(progress);
         this.speak(level, age);
         FeldwayAwakeningEvent.tick(this, level, age, 520);
         if (age >= 520) {
            this.spawnAwakened();
         }
      }
   }

   private float riseCurve(float progress) {
      float delayed = Mth.clamp((progress - 0.18F) / 0.82F, 0.0F, 1.0F);
      delayed = delayed * delayed * (3.0F - 2.0F * delayed);
      return 0.1F + delayed * 18.0F;
   }

   private void forceLayingState() {
      BlockPos pos = BlockPos.containing(this.getX(), this.getY(), this.getZ());
      this.setSleepingPos(pos);
      this.setPose(Pose.SLEEPING);
      this.setYBodyRot(this.getYRot());
      this.setYHeadRot(this.getYRot());
      this.setXRot(0.0F);
   }

   private void speak(ServerLevel level, int age) {
      if (age == 20) {
         this.message(level, Component.literal("<Feldway> How... did you do that?").withStyle(ChatFormatting.GRAY));
         level.playSound(null, this.blockPosition(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.HOSTILE, 3.0F, 0.38F);
      }

      if (age == 80) {
         this.message(level, Component.literal("<Feldway> So even this vessel can be brought low.").withStyle(ChatFormatting.DARK_GRAY));
         level.playSound(null, this.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.HOSTILE, 2.8F, 0.22F);
      }

      if (age == 150) {
         this.message(level, Component.literal("<Feldway> Then take witness. Beyond this defeat, four shadows still remain.").withStyle(ChatFormatting.RED));
         level.playSound(null, this.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE, 3.4F, 0.16F);
      }

      if (age == 245) {
         this.message(
            level, Component.literal("<Feldway> Time. Space. Life. Death. The shades answer only to the one who endures.").withStyle(ChatFormatting.GOLD)
         );
         level.playSound(null, this.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 4.5F, 0.26F);
      }

      if (age == 360) {
         this.message(
            level, Component.literal("<Feldway> You have opened the path. Now inherit what stands behind creation.").withStyle(ChatFormatting.DARK_RED)
         );
         level.playSound(null, this.blockPosition(), SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 5.5F, 0.35F);
      }

      if (age == 470) {
         this.message(
            level, Component.literal("<Feldway> Claim the Four Shades... and prove you can bear their authority.").withStyle(ChatFormatting.DARK_PURPLE)
         );
         level.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.HOSTILE, 5.5F, 0.12F);
      }
   }

   private void message(ServerLevel level, Component component) {
      double rangeSqr = 32400.0;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(this) <= rangeSqr) {
            player.sendSystemMessage(component);
         }
      }
   }

   private void spawnAwakened() {
      if (this.level() instanceof ServerLevel level) {
         this.restoreTime(level);
         FeldwayAwakenedEntity awakened = (FeldwayAwakenedEntity)((EntityType)ModEntityTypes.FELDWAY_AWAKENED.get()).create(level);
         if (awakened == null) {
            this.bossEvent.removeAllPlayers();
            this.discard();
         } else {
            awakened.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            awakened.setPersistenceRequired();
            if (this.targetId != null && level.getEntity(this.targetId) instanceof LivingEntity living && living.isAlive()) {
               awakened.setTarget(living);
               awakened.setLockedTarget(this.targetId);
            }

            level.addFreshEntity(awakened);
            FeldwayAwakeningEvent.finish(this, level);
            this.bossEvent.removeAllPlayers();
            this.discard();
         }
      }
   }

   public void setVisualTime(ServerLevel level, long dayTime) {
      this.visualDayTime = dayTime;
      level.setDayTime(dayTime);
      ClientboundSetTimePacket packet = new ClientboundSetTimePacket(level.getGameTime(), dayTime, level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT));

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(this) <= 67600.0) {
            player.connection.send(packet);
         }
      }
   }

   public void restoreTime(ServerLevel level) {
      if (!this.restoredTime) {
         this.restoredTime = true;
         long elapsed = Math.max(0L, level.getGameTime() - this.originalGameTime);
         long restored = this.originalDaylightRule ? this.originalDayTime + elapsed : this.originalDayTime;
         level.setDayTime(restored);
         ClientboundSetTimePacket packet = new ClientboundSetTimePacket(level.getGameTime(), restored, level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT));

         for (ServerPlayer player : level.players()) {
            player.connection.send(packet);
            player.connection.send(packet);
            player.connection.send(packet);
         }
      }
   }

   public void remove(RemovalReason reason) {
      if (!this.level().isClientSide() && this.level() instanceof ServerLevel level) {
         this.restoreTime(level);
      }

      super.remove(reason);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("AwakeningAge", this.getAwakeningAge());
      tag.putBoolean("AwakeningCapturedStart", this.capturedStart);
      tag.putDouble("AwakeningStartX", this.startX);
      tag.putDouble("AwakeningStartY", this.startY);
      tag.putDouble("AwakeningStartZ", this.startZ);
      tag.putLong("AwakeningOriginalDayTime", this.originalDayTime);
      tag.putLong("AwakeningOriginalGameTime", this.originalGameTime);
      tag.putBoolean("AwakeningOriginalDaylightRule", this.originalDaylightRule);
      tag.putDouble("AwakeningVisualDayTime", this.visualDayTime);
      tag.putBoolean("AwakeningRestoredTime", this.restoredTime);
      if (this.targetId != null) {
         tag.putUUID("AwakeningTarget", this.targetId);
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setAwakeningAge(tag.getInt("AwakeningAge"));
      this.capturedStart = tag.getBoolean("AwakeningCapturedStart");
      this.startX = tag.getDouble("AwakeningStartX");
      this.startY = tag.getDouble("AwakeningStartY");
      this.startZ = tag.getDouble("AwakeningStartZ");
      this.originalDayTime = tag.getLong("AwakeningOriginalDayTime");
      this.originalGameTime = tag.getLong("AwakeningOriginalGameTime");
      this.originalDaylightRule = !tag.contains("AwakeningOriginalDaylightRule") || tag.getBoolean("AwakeningOriginalDaylightRule");
      this.visualDayTime = tag.getDouble("AwakeningVisualDayTime");
      this.restoredTime = tag.getBoolean("AwakeningRestoredTime");
      if (tag.hasUUID("AwakeningTarget")) {
         this.targetId = tag.getUUID("AwakeningTarget");
      }
   }
}
