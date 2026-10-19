package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.config.mob.ObsidilithConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.CooldownAction;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.ActionGoal;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.FindTargetGoal;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.CappedHeal;
import com.cerbon.bosses_of_mass_destruction.entity.damage.CompositeDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.EffectsImmunity;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.ParticleUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class ObsidilithEntity extends BaseEntity {
   private final ObsidilithConfig mobConfig;
   private final Map<Byte, IActionWithCooldown> statusRegistry;
   private final ObsidilithMoveLogic moveLogic;
   private final ObsidilithEffectHandler effectHandler;
   private final List<BlockPos> activePillars = new ArrayList<>();
   public byte currentAttack = 0;
   public static final EntityDataAccessor<Boolean> isShielded = SynchedEntityData.defineId(ObsidilithEntity.class, EntityDataSerializers.BOOLEAN);

   public ObsidilithEntity(EntityType<? extends PathfinderMob> entityType, Level level, ObsidilithConfig mobConfig) {
      super(entityType, level);
      this.mobConfig = mobConfig;
      this.noCulling = true;
      this.statusRegistry = Map.of(
         (byte)5,
         new BurstAction(this),
         (byte)6,
         new WaveAction(this),
         (byte)7,
         new SpikeAction(this),
         (byte)8,
         new AnvilAction(this, mobConfig.anvilAttackExplosionStrength),
         (byte)9,
         new PillarAction(this)
      );
      DamageMemory damageMemory = new DamageMemory(10, this);
      this.moveLogic = new ObsidilithMoveLogic(this.statusRegistry, this, damageMemory);
      this.effectHandler = new ObsidilithEffectHandler(this, CapabilityUtils.getLevelEventScheduler(level));
      this.bossBar = new ServerBossEvent(this.getDisplayName(), BossBarColor.PURPLE, BossBarOverlay.NOTCHED_12);
      this.damageHandler = new CompositeDamageHandler(this.moveLogic, new ShieldDamageHandler(this::isShielded), damageMemory);
      this.mobEffectHandler = new EffectsImmunity((MobEffect)MobEffects.WITHER.value(), (MobEffect)MobEffects.POISON.value());
      this.serverTick = new CappedHeal(this, ObsidilithUtils.hpPillarShieldMilestones, mobConfig.idleHealingPerTick);
      if (!level.isClientSide()) {
         this.goalSelector.addGoal(1, this.buildAttackGoal());
         this.targetSelector.addGoal(2, new FindTargetGoal(this, Player.class, d -> this.getBoundingBox().inflate(d), 10, true, false, null));
         this.preTickEvents
            .addEvent(
               new TimedEvent(
                  () -> SoundUtils.playSound(
                     (ServerLevel)level, this.position(), (SoundEvent)BMDSounds.WAVE_INDICATOR.get(), SoundSource.HOSTILE, 1.5F, 0.7F, 24.0, null
                  ),
                  1
               )
            );
      }
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(isShielded, false);
   }

   private ActionGoal buildAttackGoal() {
      CooldownAction attackAction = new CooldownAction(this.moveLogic, 80);
      return new ActionGoal(this::canContinueAttack, null, attackAction, null, attackAction);
   }

   @Override
   public void serverTick(ServerLevel serverLevel) {
      super.serverTick(serverLevel);
      this.activePillars
         .removeIf(posx -> this.level().getBlockState(posx).getBlock() != BMDBlocks.OBSIDILITH_RUNE.get() || !posx.closerThan(this.blockPosition(), 64.0));
      this.getEntityData().set(isShielded, !this.activePillars.isEmpty());
      if (this.tickCount % 40 == 0 && !this.activePillars.isEmpty()) {
         BlockPos pos = this.activePillars.get(new Random().nextInt(this.activePillars.size()));
         MathUtils.lineCallback(
            VecUtils.asVec3(pos).add(0.5, 0.5, 0.5),
            MobUtils.eyePos(this),
            15,
            (vec3, i) -> this.preTickEvents
               .addEvent(
                  new TimedEvent(() -> ParticleUtils.spawnParticle(serverLevel, (ParticleOptions)BMDParticles.PILLAR_RUNE.get(), vec3, Vec3.ZERO, 0, 0.0), i)
               )
         );
      }
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public void push(@NotNull Entity entity) {
   }

   public void die(@NotNull DamageSource damageSource) {
      if (this.mobConfig.spawnPillarOnDeath) {
         ObsidilithUtils.onDeath(this, this.mobConfig.experienceDrop);
         if (this.level().isClientSide) {
            this.effectHandler.handleStatus((byte)3);
         }
      }

      super.die(damageSource);
   }

   public boolean canContinueAttack() {
      return this.isAlive() && this.getTarget() != null;
   }

   @Override
   public void handleEntityEvent(byte status) {
      IActionWithCooldown attackStatus = this.statusRegistry.get(status);
      if (attackStatus != null) {
         this.effectHandler.handleStatus(status);
         this.currentAttack = status;
         this.preTickEvents.addEvent(new TimedEvent(() -> this.currentAttack = 0, 40));
      }

      super.handleEntityEvent(status);
   }

   @Nullable
   protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
      return (SoundEvent)BMDSounds.OBSIDILITH_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return (SoundEvent)BMDSounds.OBSIDILITH_DEATH.get();
   }

   public void registerControllers(ControllerRegistrar data) {
      data.add(new AnimationController(this, "summon", 0, animationState -> {
         animationState.getController().setAnimation(RawAnimation.begin().thenPlay("summon"));
         return PlayState.CONTINUE;
      }));
   }

   @Override
   public void move(@NotNull MoverType type, @NotNull Vec3 movement) {
      super.move(type, new Vec3(0.0, movement.y, 0.0));
   }

   public boolean isOnFire() {
      return false;
   }

   public int getArmorValue() {
      return this.getTarget() != null ? super.getArmorValue() : 24;
   }

   public void checkDespawn() {
      MobUtils.preventDespawnExceptPeaceful(this, this.level());
   }

   public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
      return false;
   }

   public boolean isShielded() {
      return (Boolean)this.getEntityData().get(isShielded);
   }

   public void addActivePillar(BlockPos pos) {
      this.activePillars.add(pos);
   }

   @NotNull
   @Override
   public CompoundTag saveWithoutId(@NotNull CompoundTag compound) {
      int[] activePillarsArray = this.activePillars.stream().flatMapToInt(p -> IntStream.of(p.getX(), p.getY(), p.getZ())).toArray();
      compound.putIntArray("activePillars", activePillarsArray);
      return super.saveWithoutId(compound);
   }

   @Override
   public void load(@NotNull CompoundTag compound) {
      super.load(compound);
      if (compound.contains("activePillars")) {
         int[] activePillarsArray = compound.getIntArray("activePillars");

         for (int i = 0; i < activePillarsArray.length; i += 3) {
            BlockPos pos = new BlockPos(activePillarsArray[i], activePillarsArray[i + 1], activePillarsArray[i + 2]);
            this.activePillars.add(pos);
         }
      }
   }
}
