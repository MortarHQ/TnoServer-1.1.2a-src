package com.mortal_cultivation.entity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class AzureMossQilinEntity extends PathfinderMob implements GeoEntity {
   public static final double STOMP_RADIUS = 4.5;
   private static final double MELEE_REACH = 1.9;
   private static final AttributeModifier ATTACK_DAMAGE_BOOST = new AttributeModifier(
      ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "qilin_attack_damage"), 1.0, Operation.ADD_MULTIPLIED_TOTAL
   );
   private static final EntityDataAccessor<Boolean> SECOND_PHASE = SynchedEntityData.defineId(AzureMossQilinEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> ATTACK = SynchedEntityData.defineId(AzureMossQilinEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(AzureMossQilinEntity.class, EntityDataSerializers.INT);
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.azure_moss_qilin.idle");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.azure_moss_qilin.walk");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final QilinHitboxes hitboxes = new QilinHitboxes(this);
   private final ServerBossEvent bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.GREEN, BossBarOverlay.PROGRESS);
   private final int[] cooldowns = new int[AzureMossQilinEntity.Attack.values().length];
   private final Set<UUID> chargeVictims = new HashSet<>();
   private final Set<UUID> stompVictims = new HashSet<>();
   private final List<AzureMossQilinEntity.RootMark> rootMarks = new ArrayList<>();
   private static final double CHARGE_DISTANCE = 20.0;
   private static final double CHARGE_SPEED = 0.8;
   private Vec3 rootTarget = Vec3.ZERO;
   private Vec3 stompCenter = Vec3.ZERO;
   private int rootVolleyTicks;
   private int rootMarkDelay;
   @Nullable
   private LivingEntity rootVolleyTarget;
   @Nullable
   private LivingEntity chargeTarget;
   private int chargePass;
   private Vec3 chargeDirection = Vec3.ZERO;
   private double chargeDistance;
   private float attackYaw;
   private int recoveryTicks;
   private int moveCycle;
   @Nullable
   private LivingEntity meleeTarget;

   public AzureMossQilinEntity(EntityType<? extends AzureMossQilinEntity> type, Level level) {
      super(type, level);
      this.setId(ENTITY_COUNTER.getAndAdd(this.hitboxes.parts().length + 1) + 1);
      this.refreshDimensions();
      this.applyAttackDamageBoost();
      this.hitboxes.update();
      this.setPersistenceRequired();
      this.setPathfindingMalus(PathType.WATER, -1.0F);
   }

   private void applyAttackDamageBoost() {
      this.getAttribute(Attributes.ATTACK_DAMAGE).addOrUpdateTransientModifier(ATTACK_DAMAGE_BOOST);
   }

   public boolean isMultipartEntity() {
      return true;
   }

   public AzureMossQilinPart[] getParts() {
      return this.hitboxes == null ? new AzureMossQilinPart[0] : this.hitboxes.parts();
   }

   public void setId(int id) {
      super.setId(id);
      if (this.hitboxes != null) {
         this.hitboxes.setId(id);
      }
   }

   public boolean isPickable() {
      return false;
   }

   protected AABB makeBoundingBox() {
      return QilinHitboxes.physicalBounds(this);
   }

   public void setPos(double x, double y, double z) {
      super.setPos(x, y, z);
      if (this.hitboxes != null) {
         this.hitboxes.update();
      }
   }

   public AABB getBoundingBoxForCulling() {
      return this.hitboxes == null ? super.getBoundingBoxForCulling() : this.hitboxes.fullBounds();
   }

   private void updateHitboxes() {
      this.setBoundingBox(this.makeBoundingBox());
      this.hitboxes.update();
   }

   public static Builder createAttributes() {
      return createMobAttributes()
         .add(Attributes.MAX_HEALTH, 200.0)
         .add(Attributes.ATTACK_DAMAGE, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.34)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.7)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.SCALE, 1.5);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SECOND_PHASE, false);
      builder.define(ATTACK, AzureMossQilinEntity.Attack.NONE.ordinal());
      builder.define(ATTACK_TICKS, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new AzureMossQilinEntity.CombatGoal());
      this.goalSelector.addGoal(2, new MoveTowardsRestrictionGoal(this, 0.9));
      this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.65));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 12.0F));
      this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector
         .addGoal(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, target -> this.isWithinRestriction(target.blockPosition())));
   }

   public boolean isSecondPhase() {
      return (Boolean)this.entityData.get(SECOND_PHASE);
   }

   public AzureMossQilinEntity.Attack getAttack() {
      return AzureMossQilinEntity.Attack.values()[this.entityData.get(ATTACK)];
   }

   public int getAttackTicks() {
      return (Integer)this.entityData.get(ATTACK_TICKS);
   }

   public Vec3 getRootTarget() {
      return this.rootTarget;
   }

   public Vec3 getStompOrigin() {
      return this.position().add(Vec3.directionFromRotation(0.0F, this.attackYaw).scale(1.1));
   }

   public ServerBossEvent getBossEvent() {
      return this.bossEvent;
   }

   public int getAttackCooldown(AzureMossQilinEntity.Attack attack) {
      return this.cooldowns[attack.ordinal()];
   }

   public boolean beginAttack(AzureMossQilinEntity.Attack attack, LivingEntity target) {
      if (!this.level().isClientSide
         && this.isAlive()
         && this.getAttack() == AzureMossQilinEntity.Attack.NONE
         && this.recoveryTicks <= 0
         && attack != AzureMossQilinEntity.Attack.NONE
         && attack != AzureMossQilinEntity.Attack.ENRAGE
         && (attack != AzureMossQilinEntity.Attack.ROOTS || this.rootVolleyTicks <= 0)
         && this.getAttackCooldown(attack) <= 0
         && this.validVictim(target)
         && this.hasLineOfSight(target)) {
         Vec3 towards = target.position().subtract(this.position()).multiply(1.0, 0.0, 1.0);
         if (towards.lengthSqr() > 1.0E-6) {
            this.setYRot((float)(Mth.atan2(towards.z, towards.x) * 180.0F / (float)Math.PI) - 90.0F);
         }

         if (attack == AzureMossQilinEntity.Attack.ROOTS) {
            Vec3 ground = this.findRootGround(target);
            if (ground == null) {
               return false;
            }

            this.rootTarget = ground;
            this.rootVolleyTarget = target;
            this.rootVolleyTicks = this.isSecondPhase() ? 200 : 120;
            this.rootMarkDelay = 0;
         }

         if (attack == AzureMossQilinEntity.Attack.CHARGE) {
            this.chargeTarget = target;
            this.chargePass = 1;
            this.aimCharge(target);
         }

         if (attack == AzureMossQilinEntity.Attack.STOMP) {
            this.stompVictims.clear();
         }

         this.meleeTarget = target;
         this.cooldowns[attack.ordinal()] = this.adjustedCooldown(attack.cooldown);
         this.startAction(attack);
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   private Vec3 findRootGround(LivingEntity target) {
      BlockPos start = target.blockPosition();

      for (int down = 0; down <= 6; down++) {
         BlockPos ground = start.below(down + 1);
         BlockState state = this.level().getBlockState(ground);
         VoxelShape shape = state.getCollisionShape(this.level(), ground);
         if (!shape.isEmpty() && state.getFluidState().isEmpty()) {
            double top = ground.getY() + shape.max(Axis.Y);
            return new Vec3(target.getX(), top, target.getZ());
         }
      }

      return null;
   }

   private int adjustedCooldown(int ticks) {
      return this.isSecondPhase() ? Math.max(1, ticks * 3 / 5) : ticks;
   }

   private void startAction(AzureMossQilinEntity.Attack attack) {
      this.navigation.stop();
      this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0.0);
      this.setXxa(0.0F);
      this.setZza(0.0F);
      this.setSpeed(0.0F);
      this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      this.attackYaw = this.getYRot();
      this.setYBodyRot(this.attackYaw);
      this.setYHeadRot(this.attackYaw);
      this.entityData.set(ATTACK, attack.ordinal());
      this.entityData.set(ATTACK_TICKS, 0);
      this.stopTriggeredAnim("action", null);
      this.triggerAnim("action", attack.name().toLowerCase(Locale.ROOT));
      this.updateHitboxes();
   }

   public void tick() {
      super.tick();
      this.updateHitboxes();
      if (!this.level().isClientSide) {
         this.bossEvent.setName(this.getDisplayName());
         this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
         this.bossEvent.setVisible(this.isAlive());
         if (!this.isAlive()) {
            this.rootMarks.clear();
            this.rootVolleyTarget = null;
            this.chargeTarget = null;
         } else {
            for (int i = 0; i < this.cooldowns.length; i++) {
               if (this.cooldowns[i] > 0) {
                  this.cooldowns[i]--;
               }
            }

            if (this.recoveryTicks > 0) {
               this.recoveryTicks--;
            }

            if (!this.isSecondPhase() && this.getHealth() <= this.getMaxHealth() * 0.5F) {
               this.enterSecondPhase();
            }

            if (this.getAttack() != AzureMossQilinEntity.Attack.NONE) {
               this.tickAction();
            }

            this.tickRootVolley();
            this.updateHitboxes();
         }
      }
   }

   private void tickAction() {
      AzureMossQilinEntity.Attack attack = this.getAttack();
      int age = this.getAttackTicks() + 1;
      this.entityData.set(ATTACK_TICKS, age);
      if (attack == AzureMossQilinEntity.Attack.MELEE && age <= 10 && this.validVictim(this.meleeTarget)) {
         Vec3 towards = this.meleeTarget.position().subtract(this.position());
         this.attackYaw = (float)(Mth.atan2(towards.z, towards.x) * 180.0F / (float)Math.PI) - 90.0F;
      }

      this.setYRot(this.attackYaw);
      this.setYBodyRot(this.attackYaw);
      this.setYHeadRot(this.attackYaw);
      this.setXxa(0.0F);
      this.setZza(0.0F);
      this.setSpeed(0.0F);
      this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      switch (attack) {
         case SWEEP:
            if (age == 12) {
               QilinEffects.sweep(this);
               this.areaAttack(this.position(), 2.5, 12.0F, true, 0.35);
            }
            break;
         case STOMP:
            if (age == 16) {
               this.stompCenter = this.getStompOrigin();
               QilinEffects.stomp(this);
            }

            if (age >= 16 && age <= 24) {
               this.tickStompImpact();
            }
         case ROOTS:
         default:
            break;
         case CHARGE:
            if (age <= 24) {
               QilinEffects.chargeWarning(this, age);
            }

            if (age > 24 && age <= 49 && this.chargeDistance < 20.0) {
               this.tickCharge();
            }
            break;
         case ENRAGE:
            QilinEffects.enrage(this, age);
            break;
         case MELEE:
            if (age == 12 && this.validVictim(this.meleeTarget) && this.inMeleeRange(this.meleeTarget) && this.hasLineOfSight(this.meleeTarget)) {
               this.doHurtTarget(this.meleeTarget);
            }
      }

      if (age >= attack.duration) {
         if (attack == AzureMossQilinEntity.Attack.CHARGE) {
            if (this.chargePass < 4 && this.validCombatTarget(this.chargeTarget)) {
               this.chargePass++;
               this.aimCharge(this.chargeTarget);
               this.startAction(AzureMossQilinEntity.Attack.CHARGE);
               return;
            }

            this.chargeTarget = null;
            this.chargePass = 0;
            this.cooldowns[AzureMossQilinEntity.Attack.CHARGE.ordinal()] = this.adjustedCooldown(AzureMossQilinEntity.Attack.CHARGE.cooldown);
         }

         this.entityData.set(ATTACK, AzureMossQilinEntity.Attack.NONE.ordinal());
         this.entityData.set(ATTACK_TICKS, 0);
         this.recoveryTicks = attack == AzureMossQilinEntity.Attack.MELEE ? (this.isSecondPhase() ? 3 : 5) : (this.isSecondPhase() ? 6 : 10);
         this.meleeTarget = null;
      }
   }

   private void tickRootVolley() {
      if (this.rootVolleyTicks > 0) {
         if (!this.validCombatTarget(this.rootVolleyTarget)) {
            this.rootVolleyTicks = 0;
         } else {
            if (this.rootMarkDelay-- <= 0) {
               Vec3 ground = this.findRootGround(this.rootVolleyTarget);
               if (ground != null && this.hasLineOfSight(this.rootVolleyTarget)) {
                  this.rootTarget = ground;
                  this.rootMarks.add(new AzureMossQilinEntity.RootMark(ground));
                  QilinEffects.rootWarning(this, ground, 1);
               }

               this.rootMarkDelay = 19;
            }

            this.rootVolleyTicks--;
         }

         if (this.rootVolleyTicks == 0) {
            this.rootVolleyTarget = null;
            this.cooldowns[AzureMossQilinEntity.Attack.ROOTS.ordinal()] = Math.max(this.cooldowns[AzureMossQilinEntity.Attack.ROOTS.ordinal()], 40);
         }
      }

      Iterator<AzureMossQilinEntity.RootMark> iterator = this.rootMarks.iterator();

      while (iterator.hasNext()) {
         AzureMossQilinEntity.RootMark mark = iterator.next();
         mark.age++;
         if (mark.age == 20) {
            QilinEffects.roots(this, mark.center);
         }

         if (mark.age >= 20 && mark.age < 60) {
            this.tickRootArea(mark);
         }

         if (mark.age >= 60) {
            iterator.remove();
         }
      }
   }

   private void tickRootArea(AzureMossQilinEntity.RootMark mark) {
      Vec3 center = mark.center;

      for (LivingEntity victim : this.level()
         .getEntitiesOfClass(LivingEntity.class, new AABB(center.add(-1.15, -0.1, -1.15), center.add(1.15, 2.5, 1.15)), this::validVictim)) {
         if (!mark.victims.contains(victim.getUUID())
            && horizontalDistanceSqr(victim, center) <= 1.3224999999999998
            && this.clearTo(center.add(0.0, 0.3, 0.0), victim)
            && victim.hurt(this.damageSources().mobAttack(this), 8.0F)) {
            mark.victims.add(victim.getUUID());
            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0), this);
         }
      }
   }

   private void aimCharge(LivingEntity target) {
      Vec3 towards = target.position().subtract(this.position()).multiply(1.0, 0.0, 1.0);
      this.chargeDirection = towards.lengthSqr() < 1.0E-6 ? Vec3.directionFromRotation(0.0F, this.getYRot()) : towards.normalize();
      this.setYRot((float)(Mth.atan2(this.chargeDirection.z, this.chargeDirection.x) * 180.0F / (float)Math.PI) - 90.0F);
      this.chargeDistance = 0.0;
      this.chargeVictims.clear();
   }

   private void tickCharge() {
      Vec3 before = this.position();
      AABB oldBounds = this.getBoundingBox();
      double step = Math.min(0.8, 20.0 - this.chargeDistance);
      Vec3 movement = this.chargeDirection.scale(step);
      this.breakBambooInCharge(oldBounds.expandTowards(movement).inflate(0.05, 0.0, 0.05));
      this.move(MoverType.SELF, movement);
      double moved = this.position().subtract(before).horizontalDistance();
      this.chargeDistance += moved;
      QilinEffects.chargeTrail(this);
      AABB sweptBounds = oldBounds.minmax(this.getBoundingBox()).inflate(0.15, 0.0, 0.15);

      for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, sweptBounds, this::validVictim)) {
         if (!this.chargeVictims.contains(victim.getUUID()) && this.hasLineOfSight(victim)) {
            this.chargeVictims.add(victim.getUUID());
            this.hitAndPush(victim, 20.0F, 1.0);
         }
      }

      if (moved < step * 0.6 || this.horizontalCollision) {
         this.chargeDistance = 20.0;
      }
   }

   private void breakBambooInCharge(AABB sweptBounds) {
      if (EventHooks.canEntityGrief(this.level(), this)) {
         for (BlockPos pos : BlockPos.betweenClosed(
            Mth.floor(sweptBounds.minX),
            Mth.floor(sweptBounds.minY),
            Mth.floor(sweptBounds.minZ),
            Mth.floor(sweptBounds.maxX - 1.0E-5),
            Mth.floor(sweptBounds.maxY - 1.0E-5),
            Mth.floor(sweptBounds.maxZ - 1.0E-5)
         )) {
            BlockState state = this.level().getBlockState(pos);
            if ((state.is(Blocks.BAMBOO) || state.is(Blocks.BAMBOO_SAPLING))
               && state.canEntityDestroy(this.level(), pos, this)
               && EventHooks.onEntityDestroyBlock(this, pos, state)) {
               this.level().destroyBlock(pos, true, this);
            }
         }
      }
   }

   private void areaAttack(Vec3 center, double radius, float damage, boolean frontOnly, double knockback) {
      Vec3 forward = Vec3.directionFromRotation(0.0F, this.attackYaw);

      for (LivingEntity victim : this.level()
         .getEntitiesOfClass(LivingEntity.class, new AABB(center.add(-radius, -0.5, -radius), center.add(radius, 3.5, radius)), this::validVictim)) {
         Vec3 delta = victim.position().subtract(center).multiply(1.0, 0.0, 1.0);
         if (!(horizontalDistanceSqr(victim, center) > radius * radius) && (!frontOnly || !(delta.dot(forward) < 0.0)) && this.hasLineOfSight(victim)) {
            this.hitAndPush(victim, damage, knockback);
         }
      }
   }

   private void tickStompImpact() {
      Vec3 center = this.stompCenter;

      for (LivingEntity victim : this.level()
         .getEntitiesOfClass(LivingEntity.class, new AABB(center.add(-4.5, -0.75, -4.5), center.add(4.5, 3.5, 4.5)), this::validVictim)) {
         if (!this.stompVictims.contains(victim.getUUID())
            && !(horizontalDistanceSqr(victim, center) > 20.25)
            && this.clearTo(center.add(0.0, 0.5, 0.0), victim)
            && this.hitAndPush(victim, 16.0F, 0.8)) {
            this.stompVictims.add(victim.getUUID());
         }
      }
   }

   private boolean hitAndPush(LivingEntity victim, float damage, double strength) {
      if (victim.hurt(this.damageSources().mobAttack(this), damage)) {
         victim.knockback(strength, this.getX() - victim.getX(), this.getZ() - victim.getZ());
         victim.hurtMarked = true;
         return true;
      } else {
         return false;
      }
   }

   private static double horizontalDistanceSqr(LivingEntity target, Vec3 origin) {
      AABB box = target.getBoundingBox();
      double dx = Mth.clamp(origin.x, box.minX, box.maxX) - origin.x;
      double dz = Mth.clamp(origin.z, box.minZ, box.maxZ) - origin.z;
      return dx * dx + dz * dz;
   }

   private boolean clearTo(Vec3 start, LivingEntity target) {
      return this.level().clip(new ClipContext(start, target.position().add(0.0, 0.5, 0.0), Block.COLLIDER, Fluid.NONE, this)).getType() == Type.MISS;
   }

   private boolean validVictim(@Nullable LivingEntity target) {
      return target != null
         && target != this
         && target.isAlive()
         && !this.isAlliedTo(target)
         && !(target instanceof AzureMossQilinEntity)
         && !target.isSpectator()
         && !(target instanceof Player player && player.isCreative());
   }

   private boolean validCombatTarget(@Nullable LivingEntity target) {
      return this.validVictim(target)
         && target.level() == this.level()
         && this.distanceToSqr(target) <= 1600.0
         && this.isWithinRestriction(target.blockPosition());
   }

   private boolean inMeleeRange(LivingEntity target) {
      AABB body = this.getBoundingBox();
      AABB victim = target.getBoundingBox();
      double dx = Math.max(0.0, Math.max(body.minX - victim.maxX, victim.minX - body.maxX));
      double dz = Math.max(0.0, Math.max(body.minZ - victim.maxZ, victim.minZ - body.maxZ));
      return dx * dx + dz * dz <= 3.61 && victim.minY < body.maxY + 0.5 && victim.maxY > body.minY - 0.5;
   }

   public boolean hurt(DamageSource source, float amount) {
      boolean damaged = super.hurt(source, amount);
      if (damaged && !this.level().isClientSide && this.isAlive() && !this.isSecondPhase() && this.getHealth() <= this.getMaxHealth() * 0.5F) {
         this.enterSecondPhase();
      }

      return damaged;
   }

   private void enterSecondPhase() {
      this.entityData.set(SECOND_PHASE, true);
      this.setHealth(this.getMaxHealth());
      this.bossEvent.setColor(BossBarColor.RED);
      this.bossEvent.setProgress(1.0F);

      for (int i = 0; i < this.cooldowns.length; i++) {
         this.cooldowns[i] = Math.min(this.cooldowns[i], 20);
      }

      this.chargeVictims.clear();
      this.chargePass = 0;
      this.chargeTarget = null;
      this.rootVolleyTicks = 0;
      this.rootVolleyTarget = null;
      this.meleeTarget = null;
      this.startAction(AzureMossQilinEntity.Attack.ENRAGE);
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   public boolean removeWhenFarAway(double distance) {
      return false;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putBoolean("QilinSecondPhase", this.isSecondPhase());
      tag.putIntArray("QilinCooldowns", this.cooldowns);
      if (this.hasRestriction()) {
         tag.putLong("QilinHome", this.getRestrictCenter().asLong());
         tag.putInt("QilinHomeRadius", (int)this.getRestrictRadius());
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.applyAttackDamageBoost();
      this.entityData.set(SECOND_PHASE, tag.getBoolean("QilinSecondPhase"));
      this.bossEvent.setColor(this.isSecondPhase() ? BossBarColor.RED : BossBarColor.GREEN);
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
      int[] savedCooldowns = tag.getIntArray("QilinCooldowns");

      for (int i = 0; i < this.cooldowns.length && i < savedCooldowns.length; i++) {
         this.cooldowns[i] = Mth.clamp(savedCooldowns[i], 0, AzureMossQilinEntity.Attack.values()[i].cooldown);
      }

      if (tag.contains("QilinHome")) {
         this.restrictTo(BlockPos.of(tag.getLong("QilinHome")), Mth.clamp(tag.getInt("QilinHomeRadius"), 8, 64));
      }

      this.entityData.set(ATTACK, AzureMossQilinEntity.Attack.NONE.ordinal());
      this.entityData.set(ATTACK_TICKS, 0);
      this.rootVolleyTicks = 0;
      this.rootVolleyTarget = null;
      this.rootMarks.clear();
      this.chargePass = 0;
      this.chargeTarget = null;
      this.recoveryTicks = 20;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.RAVAGER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.RAVAGER_DEATH;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(
            this,
            "movement",
            4,
            state -> this.getAttack() != AzureMossQilinEntity.Attack.NONE ? PlayState.STOP : state.setAndContinue(state.isMoving() ? WALK : IDLE)
         )
      );
      AnimationController<AzureMossQilinEntity> action = new AnimationController(this, "action", 0, state -> PlayState.STOP);

      for (AzureMossQilinEntity.Attack attack : AzureMossQilinEntity.Attack.values()) {
         if (attack != AzureMossQilinEntity.Attack.NONE) {
            action.triggerableAnim(attack.name().toLowerCase(Locale.ROOT), RawAnimation.begin().thenPlay("animation.azure_moss_qilin." + attack.animation));
         }
      }

      controllers.add(action);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public enum Attack {
      NONE(0, 0, ""),
      SWEEP(26, 60, "horn_sweep"),
      STOMP(44, 160, "ground_slam"),
      ROOTS(40, 240, "root_cast"),
      CHARGE(64, 240, "charge"),
      ENRAGE(56, 0, "roar"),
      MELEE(25, 30, "attack");

      final int duration;
      final int cooldown;
      final String animation;

      Attack(int duration, int cooldown, String animation) {
         this.duration = duration;
         this.cooldown = cooldown;
         this.animation = animation;
      }
   }

   private final class CombatGoal extends Goal {
      private int navigationTicks;

      CombatGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         return AzureMossQilinEntity.this.getAttack() != AzureMossQilinEntity.Attack.NONE
            || AzureMossQilinEntity.this.validVictim(AzureMossQilinEntity.this.getTarget());
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void stop() {
         AzureMossQilinEntity.this.navigation.stop();
      }

      public void tick() {
         LivingEntity target = AzureMossQilinEntity.this.getTarget();
         if (AzureMossQilinEntity.this.getAttack() != AzureMossQilinEntity.Attack.NONE) {
            if (AzureMossQilinEntity.this.getAttack() == AzureMossQilinEntity.Attack.ROOTS
               && AzureMossQilinEntity.this.getAttackTicks() >= 12
               && AzureMossQilinEntity.this.validCombatTarget(target)
               && AzureMossQilinEntity.this.inMeleeRange(target)
               && AzureMossQilinEntity.this.hasLineOfSight(target)
               && AzureMossQilinEntity.this.getAttackCooldown(AzureMossQilinEntity.Attack.MELEE) == 0) {
               AzureMossQilinEntity.this.meleeTarget = target;
               AzureMossQilinEntity.this.cooldowns[AzureMossQilinEntity.Attack.MELEE.ordinal()] = AzureMossQilinEntity.this.adjustedCooldown(
                  AzureMossQilinEntity.Attack.MELEE.cooldown
               );
               AzureMossQilinEntity.this.startAction(AzureMossQilinEntity.Attack.MELEE);
            }

            AzureMossQilinEntity.this.navigation.stop();
         } else if (AzureMossQilinEntity.this.validVictim(target)) {
            if (!AzureMossQilinEntity.this.validCombatTarget(target)) {
               AzureMossQilinEntity.this.setTarget(null);
               AzureMossQilinEntity.this.navigation.stop();
            } else {
               AzureMossQilinEntity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
               double distance = Math.sqrt(AzureMossQilinEntity.horizontalDistanceSqr(target, AzureMossQilinEntity.this.position()));
               boolean close = AzureMossQilinEntity.this.inMeleeRange(target);
               if (AzureMossQilinEntity.this.recoveryTicks == 0 && AzureMossQilinEntity.this.hasLineOfSight(target) && close) {
                  if (AzureMossQilinEntity.this.beginAttack(AzureMossQilinEntity.Attack.MELEE, target)) {
                     return;
                  }
               } else if (AzureMossQilinEntity.this.recoveryTicks == 0
                  && AzureMossQilinEntity.this.hasLineOfSight(target)
                  && AzureMossQilinEntity.this.rootVolleyTicks == 0) {
                  AzureMossQilinEntity.Attack[] moves = new AzureMossQilinEntity.Attack[]{
                     AzureMossQilinEntity.Attack.SWEEP,
                     AzureMossQilinEntity.Attack.STOMP,
                     AzureMossQilinEntity.Attack.ROOTS,
                     AzureMossQilinEntity.Attack.CHARGE
                  };

                  for (int i = 0; i < moves.length; i++) {
                     int index = (AzureMossQilinEntity.this.moveCycle + i) % moves.length;
                     AzureMossQilinEntity.Attack candidate = moves[index];

                     boolean inRange = switch (candidate) {
                        case SWEEP -> distance <= 2.5;
                        case STOMP -> distance <= 5.6;
                        case ROOTS -> distance <= 24.0;
                        case CHARGE -> distance >= 4.0 && distance <= 24.0;
                        default -> false;
                     };
                     if (inRange && AzureMossQilinEntity.this.beginAttack(candidate, target)) {
                        AzureMossQilinEntity.this.moveCycle = (index + 1) % moves.length;
                        return;
                     }
                  }
               }

               if (!close) {
                  if (--this.navigationTicks <= 0) {
                     AzureMossQilinEntity.this.navigation.moveTo(target, AzureMossQilinEntity.this.isSecondPhase() ? 1.4 : 1.2);
                     this.navigationTicks = AzureMossQilinEntity.this.isSecondPhase() ? 4 : 5;
                  }
               } else {
                  AzureMossQilinEntity.this.navigation.stop();
               }
            }
         }
      }
   }

   private static final class RootMark {
      private final Vec3 center;
      private final Set<UUID> victims = new HashSet<>();
      private int age;

      private RootMark(Vec3 center) {
         this.center = center;
      }
   }
}
