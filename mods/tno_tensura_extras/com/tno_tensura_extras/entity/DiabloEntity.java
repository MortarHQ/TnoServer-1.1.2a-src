package com.tno_tensura_extras.entity;

import com.tno_tensura_extras.event.DiabloCombatEvents;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.InvalidateNeutralAttackTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SubordinateFollowOwner;
import io.github.manasmods.tensura.entity.monster.ArchDaemonEntity;
import io.github.manasmods.tensura.entity.variant.DaemonVariant.Linage;
import io.github.manasmods.tensura.util.SubordinateHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.AnimationController.AnimationStateHandler;
import software.bernie.geckolib.animation.AnimationController.State;

public final class DiabloEntity extends ArchDaemonEntity {
   private static final ResourceLocation STRIKE_DAMAGE = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "diablo_strike_damage");
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.diablo.idle");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.diablo.walk");
   private static final RawAnimation RUN = RawAnimation.begin().thenLoop("animation.diablo.run");
   private static final RawAnimation FLY = RawAnimation.begin().thenLoop("animation.diablo.fly");
   private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("animation.diablo.attack");
   private static final RawAnimation WINGS_IDLE = RawAnimation.begin().thenLoop("animation.diablo.wings_idle");
   private static final RawAnimation WINGS_WALK = RawAnimation.begin().thenLoop("animation.diablo.wings_walk");
   private static final RawAnimation WINGS_FLY = RawAnimation.begin().thenLoop("animation.diablo.wings_fly");
   private final ServerBossEvent bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.RED, BossBarOverlay.PROGRESS);
   private final DiabloSkillController skillController = new DiabloSkillController(this);
   private final DiabloCombatController combatController = new DiabloCombatController(this);
   private final DiabloTargeting targeting = new DiabloTargeting(this);
   @Nullable
   private LivingEntity flightTarget;
   private int blockedGroundTicks;
   private int airborneTargetTicks;
   private int groundPreferenceTicks;
   private int rangedBlockedTicks;
   private double visualMovementSpeed;
   private boolean walkingAnimation;
   private boolean runningAnimation;

   public DiabloEntity(EntityType<? extends DiabloEntity> type, Level level) {
      super(type, level);
      this.setVariant(Linage.BLACK);
      this.setPersistenceRequired();
      this.setCanPickUpLoot(false);
      this.switchNavigator(this, true);
   }

   public static Builder createAttributes() {
      return ArchDaemonEntity.setAttributes()
         .add(Attributes.MAX_HEALTH, 9000.0)
         .add(Attributes.ATTACK_DAMAGE, 150.0)
         .add(Attributes.ARMOR, 60.0)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 1.6)
         .add(Attributes.FOLLOW_RANGE, 64.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return null;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource source) {
      return null;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return null;
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide() || !this.isNoAi() && this.isAlive()) {
         if (!this.level().isClientSide()) {
            this.skillController.updateLook();
         }
      } else {
         this.skillController.stop();
         this.combatController.stop();
      }

      if (this.level().isClientSide()) {
         double displacement = Math.hypot(this.getX() - this.xo, this.getZ() - this.zo);
         if (!(displacement > 2.0) && !this.isFlying() && !this.isInSittingPose() && !this.isPassenger() && this.isAlive()) {
            this.visualMovementSpeed = Mth.lerp(0.5, this.visualMovementSpeed, displacement);
         } else {
            this.visualMovementSpeed = 0.0;
         }

         this.walkingAnimation = this.visualMovementSpeed > (this.walkingAnimation ? 0.004 : 0.008);
         this.runningAnimation = this.walkingAnimation && this.visualMovementSpeed > (this.runningAnimation ? 0.1 : 0.14);
      }
   }

   public AABB getBoundingBoxForCulling() {
      return this.getBoundingBox().inflate(2.2, 0.5, 2.2);
   }

   public void travel(Vec3 input) {
      if (!this.level().isClientSide() && this.skillController.isControllingFlight()) {
         Vec3 motion = this.skillController.getCastingMotion();
         this.setDeltaMovement(motion);
         this.move(MoverType.SELF, motion);
         this.setDeltaMovement(Vec3.ZERO);
      } else if (!this.level().isClientSide() && this.combatController.isDodging()) {
         Vec3 motion = this.combatController.getDodgeMotion().add(0.0, this.isFlying() ? 0.0 : this.getDeltaMovement().y, 0.0);
         this.move(MoverType.SELF, motion);
         this.setDeltaMovement(0.0, !this.isFlying() && !this.onGround() ? (motion.y - 0.08) * 0.98 : 0.0, 0.0);
      } else {
         super.travel(input);
      }
   }

   public boolean isWithinMeleeAttackRange(LivingEntity target) {
      return target != null && DiabloCombatController.withinClawRange(this.getBoundingBox(), target.getBoundingBox());
   }

   public boolean doHurtTarget(Entity target) {
      return target instanceof LivingEntity living
         && this.isAllowedCombatTarget(living)
         && this.isWithinMeleeAttackRange(living)
         && this.hasLineOfSight(living)
         && super.doHurtTarget(target);
   }

   boolean strike(LivingEntity target, DiabloAttackAnimation attack) {
      AttributeInstance damage = this.getAttribute(Attributes.ATTACK_DAMAGE);
      if (damage == null) {
         return false;
      }

      damage.addTransientModifier(new AttributeModifier(STRIKE_DAMAGE, attack.damageMultiplier - 1.0, Operation.ADD_MULTIPLIED_TOTAL));

      try {
         return this.doHurtTarget(target);
      } finally {
         damage.removeModifier(STRIKE_DAMAGE);
      }
   }

   protected boolean canRandomizeSpawnData(MobSpawnType reason) {
      return false;
   }

   public boolean isFood(ItemStack stack) {
      return false;
   }

   public boolean canMate(Animal other) {
      return false;
   }

   public boolean isAlliedTo(Entity entity) {
      if (DiabloTargeting.isProtectedRimuru(entity)) {
         return true;
      } else {
         return !this.isTame() && DiabloTargeting.isRival(entity) && entity instanceof LivingEntity living && !SubordinateHelper.isAlly(this, living)
            ? false
            : super.isAlliedTo(entity);
      }
   }

   public boolean canAttack(LivingEntity target) {
      return !DiabloTargeting.isProtectedRimuru(target) && super.canAttack(target);
   }

   public boolean shouldAttack(TamableAnimal mob, LivingEntity target) {
      return this.isAllowedCombatTarget(target);
   }

   public boolean isAllowedCombatTarget(@Nullable LivingEntity target) {
      return this.targeting != null && this.targeting.canEngage(target);
   }

   public void respondToThreat(LivingEntity attacker, boolean protectRimuru) {
      this.targeting.rememberThreat(attacker, protectRimuru);
   }

   public void setTarget(@Nullable LivingEntity target) {
      if (target != null && !this.isAllowedCombatTarget(target)) {
         target = null;
      }

      super.setTarget(target);
   }

   public BrainActivityGroup<ArchDaemonEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(
         new Behavior[]{
            new LookAtTarget(),
            new MoveToWalkTarget()
               .startCondition(
                  entity -> !entity.isOrderedToSit() && !entity.isSleeping() && !this.skillController.isChanneling() && !this.combatController.isDodging()
               )
               .stopIf(entity -> entity.isOrderedToSit() || entity.isSleeping() || this.skillController.isChanneling() || this.combatController.isDodging())
         }
      );
   }

   public BrainActivityGroup<ArchDaemonEntity> getIdleTasks() {
      BrainActivityGroup<ArchDaemonEntity> tasks = super.getIdleTasks();
      tasks.getBehaviours()
         .replaceAll(
            behaviour -> {
               if (behaviour instanceof FirstApplicableBehaviour<?> selection) {
                  selection.getBehaviours().forEachRemaining(child -> {
                     if (child instanceof TargetOrRetaliate<?> targetSelection) {
                        targetSelection.attackablePredicate(this::isAllowedCombatTarget);
                     }

                     if (child instanceof SubordinateFollowOwner<?> follow) {
                        follow.canTeleportOffGroundWhen(entity -> false);
                     }
                  });
               }

               return (Behavior)(behaviour instanceof OneRandomBehaviour
                  ? new OneRandomBehaviour(new ExtendedBehaviour[]{new SetRandomWalkTarget().setRadius(10.0), new Idle().runForBetween(30, 60)})
                     .startCondition(entity -> !entity.isOrderedToSit() && !entity.isSleeping())
                  : behaviour);
            }
         );
      return tasks;
   }

   public BrainActivityGroup<ArchDaemonEntity> getFightTasks() {
      BrainActivityGroup<ArchDaemonEntity> tasks = super.getFightTasks();
      tasks.getBehaviours().removeIf(behaviour -> !(behaviour instanceof InvalidateNeutralAttackTarget));
      return tasks;
   }

   private void updateRangedAttackPreference() {
      LivingEntity target = this.getTarget();
      if (target != null && target.isAlive() && !this.isOrderedToSit() && !this.isSleeping() && !this.isPassenger() && !this.isWithinMeleeAttackRange(target)) {
         Path path = this.getNavigation().getPath();
         boolean pathToTarget = path != null && path.getTarget().distSqr(target.blockPosition()) <= 4.0;
         if (pathToTarget && path.canReach() && !this.getNavigation().isStuck()) {
            this.rangedBlockedTicks = 0;
         } else {
            boolean blocked = this.getNavigation().isStuck()
               || pathToTarget && !path.canReach()
               || this.getBrain().hasMemoryValue(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            this.rangedBlockedTicks = blocked ? Math.min(this.rangedBlockedTicks + 1, 60) : 0;
         }
      } else {
         this.rangedBlockedTicks = 0;
      }
   }

   private void updateCombatFlight() {
      if (!this.skillController.isChanneling()) {
         LivingEntity target = this.getTarget();
         if (target != this.flightTarget) {
            this.flightTarget = target;
            this.blockedGroundTicks = 0;
            this.airborneTargetTicks = 0;
            this.rangedBlockedTicks = 0;
         }

         if (this.groundPreferenceTicks > 0) {
            this.groundPreferenceTicks--;
         }

         if (target != null && target.isAlive() && !this.isOrderedToSit() && !this.isSleeping() && !this.isPassenger()) {
            boolean groundedTarget = target.onGround() || target.isInWaterOrBubble() || target.isInLava();
            if (groundedTarget
               && this.isWithinMeleeAttackRange(target)
               && this.getSensing().hasLineOfSight(target)
               && Math.abs(target.getY() - this.getY()) <= 1.5) {
               this.setCombatFlying(false);
               this.blockedGroundTicks = 0;
               this.airborneTargetTicks = 0;
            } else if (!this.isFlying() && this.groundPreferenceTicks <= 0 && !this.shouldStopFlying(this)) {
               boolean blocked = this.getBrain().hasMemoryValue(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE) || this.getNavigation().isStuck();
               this.blockedGroundTicks = blocked ? this.blockedGroundTicks + 1 : 0;
               this.airborneTargetTicks = !groundedTarget && target.getY() - this.getY() > 3.0 ? this.airborneTargetTicks + 1 : 0;
               if (this.canFly() && (this.airborneTargetTicks >= 20 || this.blockedGroundTicks >= 60)) {
                  this.setCombatFlying(true);
               }
            }
         } else {
            this.setCombatFlying(false);
            this.blockedGroundTicks = 0;
            this.airborneTargetTicks = 0;
         }
      }
   }

   void setCombatFlying(boolean flying) {
      if (this.isFlying() != flying) {
         this.getNavigation().stop();
         this.setFlying(flying);
         this.setFlyingTick(0);
         this.switchNavigator(this, !flying);
         this.setNoGravity(flying);
         this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
         this.getBrain().eraseMemory(MemoryModuleType.PATH);
         this.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
         this.blockedGroundTicks = 0;
         this.airborneTargetTicks = 0;
         if (!flying) {
            this.groundPreferenceTicks = 60;
         }
      }
   }

   protected void customServerAiStep() {
      this.targeting.tick();
      this.skillController.tick();
      this.updateCombatFlight();
      this.updateRangedAttackPreference();
      super.customServerAiStep();
      this.targeting.clearInvalidTarget();
      this.combatController.tick();
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
      this.bossEvent.setVisible(this.isAlive() && !this.isTame());
   }

   boolean isCombatPathBlocked() {
      return this.rangedBlockedTicks >= 60;
   }

   boolean isCastingMagic() {
      return this.combatController.isCastingMagic();
   }

   boolean hasCommittedCombatAction() {
      return this.combatController.isBusy();
   }

   boolean isSkillChanneling() {
      return this.skillController.isChanneling();
   }

   boolean isAnalyzingMagic() {
      return this.skillController.isAnalyzing();
   }

   boolean blinkBehind(LivingEntity target) {
      return this.skillController.blinkBehind(target);
   }

   void castThunderOrb() {
      super.thunderOrb();
   }

   void playCombatAnimation(String animation) {
      this.stopTriggeredAnim("bodyController", null);
      this.triggerAnim("bodyController", animation);
   }

   public boolean hurt(DamageSource source, float amount) {
      if (!this.level().isClientSide() && amount > 0.0F && this.isAlive()) {
         LivingEntity attacker = DiabloCombatEvents.findAttacker(source.getEntity());
         if (attacker == null) {
            attacker = DiabloCombatEvents.findAttacker(source.getDirectEntity());
         }

         if (attacker != null) {
            this.respondToThreat(attacker, false);
         }

         if (this.combatController.tryBlock(source)) {
            return false;
         }
      }

      return super.hurt(source, amount);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      this.skillController.save(tag);
      this.combatController.save(tag);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      AttributeInstance reach = this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
      if (reach != null && reach.getBaseValue() == 4.0) {
         reach.setBaseValue(1.6);
      }

      AttributeInstance armor = this.getAttribute(Attributes.ARMOR);
      if (armor != null && armor.getBaseValue() == 25.0) {
         armor.setBaseValue(60.0);
      }

      this.skillController.load(tag);
      this.combatController.load(tag);
   }

   public void setCustomName(@Nullable Component name) {
      super.setCustomName(name);
      if (this.bossEvent != null) {
         this.bossEvent.setName(this.getDisplayName());
      }
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   public void remove(RemovalReason reason) {
      if (!this.level().isClientSide()) {
         this.skillController.stop();
         this.combatController.stop();
      }

      this.bossEvent.removeAllPlayers();
      super.remove(reason);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<DiabloEntity> body = new DiabloEntity.MovementAnimationController(this, "bodyController", 2, this::bodyAnimation)
         .triggerableAnim("attack", ATTACK)
         .triggerableAnim("claw_block", RawAnimation.begin().thenPlay("animation.diablo.claw_block"));

      for (DiabloAttackAnimation attack : DiabloAttackAnimation.values()) {
         body.triggerableAnim(attack.animation, RawAnimation.begin().thenPlay("animation.diablo." + attack.animation));
      }

      controllers.add(body);
      controllers.add(new DiabloEntity.MovementAnimationController(this, "wingsController", 8, this::wingsAnimation));
   }

   public void triggerAnim(@Nullable String controllerName, String animationName) {
      if (!"attackController".equals(controllerName) && !"miscController".equals(controllerName)) {
         super.triggerAnim(controllerName, animationName);
      } else if (!this.combatController.isBusy()) {
         super.triggerAnim("bodyController", "attack");
      }
   }

   private PlayState bodyAnimation(AnimationState<DiabloEntity> state) {
      if (this.isFlying()) {
         return state.setAndContinue(FLY);
      } else {
         return this.walkingAnimation && !this.isInSittingPose() ? state.setAndContinue(this.runningAnimation ? RUN : WALK) : state.setAndContinue(IDLE);
      }
   }

   private PlayState wingsAnimation(AnimationState<DiabloEntity> state) {
      return this.isFlying()
         ? state.setAndContinue(WINGS_FLY)
         : state.setAndContinue(this.walkingAnimation && !this.isInSittingPose() ? WINGS_WALK : WINGS_IDLE);
   }

   private static final class MovementAnimationController extends AnimationController<DiabloEntity> {
      private static final double WALK_DISTANCE_PER_TICK = 3.0 * Math.sin(Math.toRadians(23.0)) / 24.0;
      private static final double RUN_DISTANCE_PER_TICK = 3.0 * Math.sin(Math.toRadians(39.0)) / 12.8;
      private double previousTick = Double.NaN;
      private double elapsedAnimationTicks;

      private MovementAnimationController(DiabloEntity entity, String name, int transitionTicks, AnimationStateHandler<DiabloEntity> handler) {
         super(entity, name, transitionTicks, handler);
         this.setAnimationSpeedHandler(animatable -> {
            if (this.getTriggeredAnimation() == null && this.getAnimationState() == State.RUNNING) {
               RawAnimation animation = this.getCurrentRawAnimation();
               if (animation != DiabloEntity.WALK && animation != DiabloEntity.RUN && animation != DiabloEntity.WINGS_WALK) {
                  return 1.0;
               }

               double strideSpeed = animation == DiabloEntity.RUN ? RUN_DISTANCE_PER_TICK : WALK_DISTANCE_PER_TICK;
               return Mth.clamp(animatable.visualMovementSpeed / strideSpeed, 0.05, 6.0);
            } else {
               return 1.0;
            }
         });
      }

      protected double adjustTick(double tick) {
         if (!this.shouldResetTick && !Double.isNaN(this.previousTick)) {
            this.elapsedAnimationTicks = this.elapsedAnimationTicks + Math.max(0.0, tick - this.previousTick) * this.getAnimationSpeed();
            this.previousTick = tick;
            return this.elapsedAnimationTicks;
         } else {
            this.previousTick = tick;
            this.elapsedAnimationTicks = 0.0;
            return super.adjustTick(tick);
         }
      }
   }
}
