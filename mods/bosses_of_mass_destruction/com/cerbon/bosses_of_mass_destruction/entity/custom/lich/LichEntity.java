package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.config.mob.LichConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.CompositeGoal;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.FindTargetGoal;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.AnimationHolder;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.CappedHeal;
import com.cerbon.bosses_of_mass_destruction.entity.damage.CompositeDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamagedAttackerNotSeen;
import com.cerbon.bosses_of_mass_destruction.entity.damage.StagedDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityTick;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.AnimationUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import com.cerbon.cerbons_api.api.general.data.HistoricalData;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class LichEntity extends BaseEntity {
   private final LichConfig mobConfig;
   private final AnimationHolder animationHolder;
   private final TeleportAction teleportAction;
   public final HistoricalData<Vec3> velocityHistory;
   public final boolean shouldSetToNighttime;
   public boolean collides;

   public LichEntity(EntityType<? extends LichEntity> entityType, Level level, LichConfig mobConfig) {
      super(entityType, level);
      this.mobConfig = mobConfig;
      this.noCulling = true;
      this.animationHolder = new AnimationHolder(
         this,
         Map.of(
            (byte)10,
            new AnimationHolder.Animation("unteleport", "idle"),
            (byte)12,
            new AnimationHolder.Animation("rage_mode", "idle"),
            (byte)11,
            new AnimationHolder.Animation("rage_mode", "idle"),
            (byte)5,
            new AnimationHolder.Animation("summon_fireball", "idle"),
            (byte)7,
            new AnimationHolder.Animation("summon_minions", "idle"),
            (byte)8,
            new AnimationHolder.Animation("rage_mode", "idle"),
            (byte)9,
            new AnimationHolder.Animation("teleport", "teleporting"),
            (byte)6,
            new AnimationHolder.Animation("summon_missiles", "idle"),
            (byte)3,
            new AnimationHolder.Animation("idle", "idle")
         ),
         (byte)4,
         0
      );
      MinionAction minionAction = new MinionAction(this, this.preTickEvents, this::cancelAttackAction);
      this.teleportAction = new TeleportAction(this, this.preTickEvents, this::cancelAttackAction);
      Map<Byte, IActionWithCooldown> statusRegistry = Map.of(
         (byte)5,
         new CometAction(this, this.preTickEvents, this::cancelAttackAction, mobConfig),
         (byte)6,
         new VolleyAction(this, mobConfig, this.preTickEvents, this::cancelAttackAction),
         (byte)7,
         minionAction,
         (byte)8,
         new MinionRageAction(this, this.preTickEvents, this::cancelAttackAction, minionAction),
         (byte)9,
         this.teleportAction,
         (byte)12,
         new CometRageAction(this, this.preTickEvents, this::cancelAttackAction, mobConfig),
         (byte)11,
         new VolleyRageAction(this, mobConfig, this.preTickEvents, this::cancelAttackAction)
      );
      DamageMemory damageMemory = new DamageMemory(5, this);
      LichMoveLogic moveLogic = new LichMoveLogic(statusRegistry, this, damageMemory);
      LichParticleHandler lichParticles = new LichParticleHandler(this, this.preTickEvents);
      this.shouldSetToNighttime = mobConfig.eternalNighttime;
      this.velocityHistory = new HistoricalData(Vec3.ZERO, 2);
      this.collides = true;
      CappedHeal cappedHeal = new CappedHeal(this, LichUtils.hpPercentRageModes, mobConfig.idleHealingPerTick);
      this.entityEventHandler = new CompositeEntityEventHandler(this.animationHolder, lichParticles);
      this.damageHandler = new CompositeDamageHandler(
         new StagedDamageHandler(LichUtils.hpPercentRageModes, () -> level.broadcastEntityEvent(this, (byte)13)),
         new DamagedAttackerNotSeen(this, livingEntity -> {
            if (livingEntity instanceof ServerPlayer) {
               this.teleportAction.performTeleport((ServerPlayer)livingEntity);
            }
         }),
         moveLogic,
         damageMemory
      );
      this.bossBar = new ServerBossEvent(this.getDisplayName(), BossBarColor.BLUE, BossBarOverlay.PROGRESS);
      this.serverTick = new CompositeEntityTick(cappedHeal, moveLogic);
      this.clientTick = lichParticles;
      if (!level.isClientSide) {
         LichActions attackHelper = new LichActions(this, moveLogic);
         LichMovement moveHelper = new LichMovement(this);
         this.goalSelector.addGoal(1, new FloatGoal(this));
         this.goalSelector.addGoal(3, new CompositeGoal(moveHelper.buildAttackMovement(), attackHelper.buildAttackGoal()));
         this.goalSelector.addGoal(4, moveHelper.buildWanderGoal());
         this.targetSelector.addGoal(2, new FindTargetGoal(this, Player.class, d -> this.getBoundingBox().inflate(d), 10, true, false, null));
      }
   }

   public void registerControllers(ControllerRegistrar data) {
      this.animationHolder.registerControllers(data);
      data.add(new AnimationController(this, "skull_float", 0, AnimationUtils.createIdlePredicate("skull_float")));
      data.add(new AnimationController(this, "float", 0, AnimationUtils.createIdlePredicate("float")));
      data.add(new AnimationController(this, "book_idle", 0, AnimationUtils.createIdlePredicate("book_idle")));
   }

   public boolean inLineOfSight(Entity target) {
      boolean hasDirectLineOfSight = VanillaCopiesServer.hasDirectLineOfSight(this.getEyePosition(), MobUtils.eyePos(target), this.level(), this);
      Vec3 directionToLich = MathUtils.unNormedDirection(MobUtils.eyePos(target), this.getEyePosition());
      boolean facingSameDirection = MathUtils.facingSameDirection(target.getLookAngle(), directionToLich);
      return hasDirectLineOfSight && facingSameDirection;
   }

   @Override
   public void clientTick() {
      this.velocityHistory.add(this.getDeltaMovement());
   }

   @Override
   public void serverTick(ServerLevel serverLevel) {
      if (this.shouldSetToNighttime) {
         serverLevel.setDayTime(LichUtils.timeToNighttime(serverLevel.dayTime()));
      }
   }

   public boolean canCollideWith(@NotNull Entity entity) {
      return this.collides;
   }

   public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
      return false;
   }

   public void checkDespawn() {
      MobUtils.preventDespawnExceptPeaceful(this, this.level());
   }

   @Nullable
   protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
      return (SoundEvent)BMDSounds.LICH_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return (SoundEvent)BMDSounds.LICH_DEATH.get();
   }

   protected float getSoundVolume() {
      return 5.0F;
   }

   public boolean onClimbable() {
      return false;
   }

   private boolean cancelAttackAction() {
      return this.isDeadOrDying() || this.getTarget() == null;
   }

   public void die(@NotNull DamageSource damageSource) {
      int expTicks = 18;
      int expPerTick = (int)((float)this.mobConfig.experienceDrop / expTicks);
      this.preTickEvents
         .addEvent(new TimedEvent(() -> VanillaCopiesServer.awardExperience(expPerTick, MobUtils.eyePos(this), this.level()), 0, expTicks, () -> false));
      this.level().getEntitiesOfClass(Phantom.class, new AABB(this.blockPosition()).inflate(100.0, 100.0, 100.0)).forEach(LivingEntity::kill);
      super.die(damageSource);
   }

   protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   public void travel(@NotNull Vec3 movementInput) {
      VanillaCopiesServer.travel(movementInput, this, 0.91F);
   }
}
