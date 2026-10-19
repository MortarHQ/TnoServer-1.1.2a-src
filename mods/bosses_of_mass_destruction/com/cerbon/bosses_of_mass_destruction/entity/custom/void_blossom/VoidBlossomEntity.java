package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.config.mob.VoidBlossomConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.TargetSwitcher;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.ActionGoal;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.CompositeGoal;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.FindTargetGoal;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.AnimationHolder;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.HitboxId;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.NetworkedHitboxManager;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.VoidBlossomHitboxes;
import com.cerbon.bosses_of_mass_destruction.entity.damage.CompositeDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.bosses_of_mass_destruction.entity.damage.StagedDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityTick;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.data.BooleanFlag;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import java.util.List;
import java.util.Map;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class VoidBlossomEntity extends BaseEntity implements MultipartAwareEntity {
   private final AnimationHolder animationHolder;
   private final NetworkedHitboxManager hitboxHelper;
   public final VoidBlossomClientSpikeHandler clientSpikeHandler;
   public static final List<Float> hpMilestones = List.of(0.0F, 0.25F, 0.5F, 0.75F, 1.0F);
   public static final EntityDataAccessor<Byte> hitbox = SynchedEntityData.defineId(VoidBlossomEntity.class, EntityDataSerializers.BYTE);

   public VoidBlossomEntity(EntityType<? extends PathfinderMob> entityType, Level level, VoidBlossomConfig mobConfig) {
      super(entityType, level);
      this.noCulling = true;
      this.animationHolder = new AnimationHolder(
         this,
         Map.of(
            (byte)4,
            new AnimationHolder.Animation("spike", "idle"),
            (byte)5,
            new AnimationHolder.Animation("spike_wave", "idle"),
            (byte)7,
            new AnimationHolder.Animation("spore", "idle"),
            (byte)8,
            new AnimationHolder.Animation("leaf_blade", "idle"),
            (byte)9,
            new AnimationHolder.Animation("blossom", "idle"),
            (byte)10,
            new AnimationHolder.Animation("spawn", "idle"),
            (byte)3,
            new AnimationHolder.Animation("death", "idle")
         ),
         (byte)6,
         0
      );
      VoidBlossomHitboxes hitboxes = new VoidBlossomHitboxes(this);
      this.hitboxHelper = new NetworkedHitboxManager(this, hitboxes.getMap(), hitbox);
      this.entityEventHandler = new CompositeEntityEventHandler(
         this.animationHolder, new ClientSporeEffectHandler(this, this.preTickEvents), new ClientDeathEffectHandler(this, this.preTickEvents)
      );
      DamageMemory damageMemory = new DamageMemory(10, this);
      TargetSwitcher targetSwitcher = new TargetSwitcher(this, damageMemory);
      BooleanFlag shouldSpawnBlossoms = new BooleanFlag();
      StagedDamageHandler hpDetector = new StagedDamageHandler(hpMilestones, shouldSpawnBlossoms::flag);
      this.bossBar = new ServerBossEvent(this.getDisplayName(), BossBarColor.GREEN, BossBarOverlay.NOTCHED_12);
      this.clientSpikeHandler = new VoidBlossomClientSpikeHandler();
      this.clientTick = this.clientSpikeHandler;
      this.serverTick = new CompositeEntityTick(
         new LightBlockPlacer(this), new VoidBlossomSpikeTick(this), hitboxes.getTickers(), new CappedHeal(this, hpMilestones, mobConfig.idleHealingPerTick)
      );
      this.deathServerTick = new CompositeEntityTick(
         new LightBlockRemover(this), new VoidBlossomDropExpDeathTick(this, this.preTickEvents, mobConfig.experienceDrop)
      );
      this.damageHandler = new CompositeDamageHandler(hpDetector, hitboxes.getDamageHandlers(), damageMemory);
      if (!level.isClientSide() && level instanceof ServerLevel) {
         VoidBlossomAttacks attackHandler = new VoidBlossomAttacks(this, this.preTickEvents, shouldSpawnBlossoms::getAndReset, targetSwitcher);
         this.goalSelector.addGoal(2, new CompositeGoal());
         this.goalSelector
            .addGoal(1, new CompositeGoal(attackHandler.buildAttackGoal(), new ActionGoal(this::canContinueAttack, null, this::lookAtTarget, null, null)));
         this.targetSelector.addGoal(2, new FindTargetGoal(this, Player.class, d -> this.getBoundingBox().inflate(d), 10, true, false, null));
         this.preTickEvents.addEvent(new TimedEvent(() -> this.playSound((SoundEvent)BMDSounds.SPIKE_WAVE_INDICATOR.get(), 2.0F, 0.7F), 1));
      } else if (this.level().isClientSide()) {
         this.animationHolder.handleEntityEvent((byte)10);
      }
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(hitbox, HitboxId.Idle.getId());
   }

   private void lookAtTarget() {
      LivingEntity target = this.getTarget();
      if (target != null) {
         this.lookControl.setLookAt(MobUtils.eyePos(target));
         this.lookAt(target, this.getMaxHeadYRot(), this.getMaxHeadXRot());
      }
   }

   public void registerControllers(ControllerRegistrar data) {
      this.animationHolder.registerControllers(data);
   }

   @Override
   public void move(@NotNull MoverType type, @NotNull Vec3 movement) {
      super.move(type, new Vec3(0.0, movement.y(), 0.0));
   }

   private boolean canContinueAttack() {
      return this.isAlive() && this.getTarget() != null;
   }

   public boolean isOnFire() {
      return false;
   }

   public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) {
      return this.hitboxHelper.getBounds().getBox(bounds);
   }

   public EntityBounds getBounds() {
      return this.hitboxHelper.getBounds();
   }

   public boolean isInWall() {
      return false;
   }

   @Nullable
   protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
      return (SoundEvent)BMDSounds.VOID_BLOSSOM_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return (SoundEvent)BMDSounds.VOID_BLOSSOM_HURT.get();
   }

   protected float getSoundVolume() {
      return 1.5F;
   }

   public void checkDespawn() {
      MobUtils.preventDespawnExceptPeaceful(this, this.level());
   }

   public int getArmorValue() {
      return this.getTarget() != null ? super.getArmorValue() : 20;
   }

   public void onSetPos(double x, double y, double z) {
      if (this.hitboxHelper != null) {
         this.hitboxHelper.updatePosition();
      }
   }

   public void setNextDamagedPart(@Nullable String part) {
      this.hitboxHelper.setNextDamagedPart(part);
   }
}
