package com.github.wal_bos.moreskills.world.entity;

import com.github.wal_bos.moreskills.net.msg.AvalonNovariPhaseVfxPayload;
import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.world.entity.avalon.AvalonNovariPhaseData;
import dev.architectury.networking.NetworkManager;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AvalonNovariTransitionEntity extends PathfinderMob {
   private static final EntityDataAccessor<Integer> DATA_AGE = SynchedEntityData.defineId(AvalonNovariTransitionEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_NEXT_PHASE = SynchedEntityData.defineId(AvalonNovariTransitionEntity.class, EntityDataSerializers.INT);
   private final ServerBossEvent bossEvent = new ServerBossEvent(Component.literal("Avalon Novari Transition"), BossBarColor.PURPLE, BossBarOverlay.PROGRESS);
   private UUID targetId;
   private UUID previousBossId;
   private double startX;
   private double startY;
   private double startZ;
   private boolean captured;
   private boolean sentVfx;

   public AvalonNovariTransitionEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
      this.bossEvent.setDarkenScreen(true);
      this.bossEvent.setPlayBossMusic(true);
      this.setPersistenceRequired();
      this.setNoGravity(true);
      this.xpReward = 0;
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 100000.0)
         .add(Attributes.ARMOR, 255.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.0)
         .add(Attributes.FOLLOW_RANGE, 192.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_AGE, 0);
      builder.define(DATA_NEXT_PHASE, 2);
   }

   public void setup(int nextPhase, UUID targetId, UUID previousBossId) {
      this.entityData.set(DATA_NEXT_PHASE, Mth.clamp(nextPhase, 2, AvalonNovariPhaseData.maxPhase()));
      this.targetId = targetId;
      this.previousBossId = previousBossId;
      this.bossEvent.setName(Component.literal("Seal Transition: ").append(AvalonNovariPhaseData.name(nextPhase)));
      this.bossEvent.setColor(AvalonNovariPhaseData.barColor(nextPhase));
   }

   public int getAge() {
      return (Integer)this.entityData.get(DATA_AGE);
   }

   public int getNextPhase() {
      return (Integer)this.entityData.get(DATA_NEXT_PHASE);
   }

   public int duration() {
      return switch (this.getNextPhase()) {
         case 2 -> 180;
         case 3 -> 260;
         case 4 -> 240;
         case 5 -> 300;
         default -> 180;
      };
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Age", this.getAge());
      compound.putInt("NextPhase", this.getNextPhase());
      compound.putBoolean("Captured", this.captured);
      compound.putDouble("StartX", this.startX);
      compound.putDouble("StartY", this.startY);
      compound.putDouble("StartZ", this.startZ);
      compound.putBoolean("SentVfx", this.sentVfx);
      if (this.targetId != null) {
         compound.putUUID("Target", this.targetId);
      }

      if (this.previousBossId != null) {
         compound.putUUID("PreviousBoss", this.previousBossId);
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.entityData.set(DATA_AGE, compound.getInt("Age"));
      this.entityData.set(DATA_NEXT_PHASE, compound.getInt("NextPhase") <= 0 ? 2 : compound.getInt("NextPhase"));
      this.captured = compound.getBoolean("Captured");
      this.startX = compound.getDouble("StartX");
      this.startY = compound.getDouble("StartY");
      this.startZ = compound.getDouble("StartZ");
      this.sentVfx = compound.getBoolean("SentVfx");
      if (compound.contains("Target")) {
         this.targetId = compound.getUUID("Target");
      }

      if (compound.contains("PreviousBoss")) {
         this.previousBossId = compound.getUUID("PreviousBoss");
      }

      this.bossEvent.setName(Component.literal("Seal Transition: ").append(AvalonNovariPhaseData.name(this.getNextPhase())));
      this.bossEvent.setColor(AvalonNovariPhaseData.barColor(this.getNextPhase()));
   }

   public boolean hurt(DamageSource source, float amount) {
      return false;
   }

   public boolean canBeAffected(MobEffectInstance effectInstance) {
      return false;
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return true;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   protected void registerGoals() {
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   public void tick() {
      super.tick();
      this.forcePose();
      if (this.level().isClientSide()) {
         this.setNoGravity(true);
         this.setDeltaMovement(Vec3.ZERO);
      } else {
         if (!this.captured) {
            this.captured = true;
            this.startX = this.getX();
            this.startY = this.getY();
            this.startZ = this.getZ();
         }

         int age = this.getAge() + 1;
         this.entityData.set(DATA_AGE, age);
         float progress = Mth.clamp((float)age / this.duration(), 0.0F, 1.0F);
         this.bossEvent.setProgress(progress);
         this.setHealth(this.getMaxHealth());
         this.setNoGravity(true);
         this.setDeltaMovement(Vec3.ZERO);
         this.navigation.stop();
         this.setPos(this.startX, this.startY + this.rise(progress), this.startZ);
         if (!this.sentVfx) {
            this.sentVfx = true;
            this.sendVfx((ServerLevel)this.level());
            this.speakStart((ServerLevel)this.level());
         }

         this.tickServerVfx((ServerLevel)this.level(), age, progress);
         if (age >= this.duration()) {
            this.spawnNext((ServerLevel)this.level());
         }
      }
   }

   private void forcePose() {
      if (this.getNextPhase() == 3) {
         this.setPose(Pose.SLEEPING);
         this.setSleepingPos(this.blockPosition());
      } else {
         this.setPose(Pose.STANDING);
      }

      this.setYHeadRot(this.getYRot());
      this.setYBodyRot(this.getYRot());
   }

   private double rise(float progress) {
      float curve = progress * progress * (3.0F - 2.0F * progress);

      return switch (this.getNextPhase()) {
         case 2 -> Math.sin(progress * Math.PI) * 2.0;
         case 3 -> curve * 1.5;
         case 4 -> curve * 5.0;
         case 5 -> curve * 8.0;
         default -> curve * 2.0;
      };
   }

   private void sendVfx(ServerLevel level) {
      AvalonNovariPhaseVfxPayload payload = new AvalonNovariPhaseVfxPayload(
         this.getId(), this.getNextPhase(), this.getX(), this.getY(), this.getZ(), this.duration(), this.getRandom().nextInt()
      );

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(this) <= 48400.0) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   private void speakStart(ServerLevel level) {
      Component line = switch (this.getNextPhase()) {
         case 2 -> Component.literal("<Avalon> The seal is slipping...").withStyle(ChatFormatting.DARK_PURPLE);
         case 3 -> Component.literal("<Novari> Avalon is no more.").withStyle(ChatFormatting.GOLD);
         case 4 -> Component.literal("<Novari> The crown opens. The phenomena kneel.").withStyle(ChatFormatting.YELLOW);
         case 5 -> Component.literal("<Novari> Endless Desire consumes the vessel.").withStyle(ChatFormatting.LIGHT_PURPLE);
         default -> Component.empty();
      };

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(this) <= 32400.0) {
            player.sendSystemMessage(line);
         }
      }
   }

   private void tickServerVfx(ServerLevel level, int age, float progress) {
      double x = this.getX();
      double y = this.getY() + this.getBbHeight() * 0.5;
      double z = this.getZ();
      if (age % 5 == 0) {
         level.sendParticles(
            ParticleTypes.REVERSE_PORTAL,
            x,
            y + progress * 3.0,
            z,
            18 + this.getNextPhase() * 8,
            1.2 + progress * 6.0,
            1.0 + progress * 4.0,
            1.2 + progress * 6.0,
            0.24 + progress * 0.32
         );
         level.sendParticles(
            ParticleTypes.WHITE_ASH,
            x,
            y + progress * 2.0,
            z,
            28 + this.getNextPhase() * 10,
            1.4 + progress * 8.0,
            1.0 + progress * 5.0,
            1.4 + progress * 8.0,
            0.12 + progress * 0.22
         );
      }

      if (age == 20 || age == this.duration() / 2 || age == this.duration() - 30) {
         level.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.HOSTILE, 5.0F, 0.18F + progress * 0.3F);
         level.sendParticles(ParticleTypes.FLASH, x, y + 1.0, z, 5, 0.4, 0.4, 0.4, 0.0);
      }
   }

   private void spawnNext(ServerLevel level) {
      AvalonNovariEntity boss = (AvalonNovariEntity)((EntityType)ModEntityTypes.AVALON_NOVARI.get()).create(level);
      if (boss != null) {
         boss.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
         boss.setupPhase(this.getNextPhase(), this.targetId);
         if (this.targetId != null && level.getEntity(this.targetId) instanceof LivingEntity living && living.isAlive()) {
            boss.setTarget(living);
         }

         level.addFreshEntity(boss);
         level.playSound(null, boss.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 7.0F, 0.24F);
      }

      this.bossEvent.removeAllPlayers();
      this.discard();
   }
}
