package io.github.Memoires.mysticism.entity.monster;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.entity.magic.spike.DarknessSpikeEntity;
import io.github.Memoires.mysticism.entity.projectile.ShadowBallProjectile;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SubordinateFollowOwner;
import io.github.manasmods.tensura.entity.ai.sensor.SleepSensor;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.entity.template.subclass.IElementalSpirit;
import io.github.manasmods.tensura.entity.template.subclass.IFlying;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRetaliateTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShadowImpEntity extends TensuraTamableEntity implements IElementalSpirit, SmartBrainOwner<ShadowImpEntity>, ISubordinate, IFlying, GeoEntity {
   private static final EntityDataAccessor<Integer> DATA_MISC_ANIMATION = SynchedEntityData.defineId(ShadowImpEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(ShadowImpEntity.class, EntityDataSerializers.BOOLEAN);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private int miscAnimationTicks;
   public int attackCooldown = 0;
   private boolean wasFlying;
   private int flyingTick;

   public ShadowImpEntity(EntityType<? extends ShadowImpEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.xpReward = 5;
      this.initFlying(this);
   }

   public static Builder setAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.ATTACK_DAMAGE, 20.0)
         .add(Attributes.MAX_HEALTH, 50.0)
         .add(Attributes.MOVEMENT_SPEED, 0.2F)
         .add(Attributes.FLYING_SPEED, 0.3F)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.2F)
         .add(ManasCoreAttributes.SWIM_SPEED_MULTIPLIER, 1.0)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 2.0)
         .add(Attributes.STEP_HEIGHT, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_MISC_ANIMATION, 0);
      builder.define(FLYING, false);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("Flying", this.isFlying());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setFlying(compound.getBoolean("Flying"));
   }

   public Element getElemental() {
      return Element.DARKNESS;
   }

   public SpiritLevel getSpiritLevel() {
      return SpiritLevel.MEDIUM;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.WARDEN_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource pDamageSource) {
      return SoundEvents.WARDEN_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.WARDEN_DEATH;
   }

   @NotNull
   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   protected @NotNull Provider<?> brainProvider() {
      return new SmartBrainProvider(this, true);
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.tickBrain(this);
   }

   public List<ExtendedSensor<ShadowImpEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor(), new SleepSensor()});
   }

   public BrainActivityGroup<ShadowImpEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(new Behavior[]{new LookAtTarget(), new FloatToSurfaceOfFluid(), TensuraTamableEntity.getMoveToWalkTarget()});
   }

   public BrainActivityGroup<ShadowImpEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{new SetRetaliateTarget(), new SubordinateFollowOwner(), new SetPlayerLookTarget(), new SetRandomLookTarget()}
            ),
            new OneRandomBehaviour(new ExtendedBehaviour[]{new SetRandomWalkTarget(), new Idle().runFor(entity -> this.getRandom().nextInt(30, 60))})
         }
      );
   }

   public BrainActivityGroup<ShadowImpEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new ExtendedBehaviour[]{new InvalidateAttackTarget(), new SetWalkTargetToAttackTarget(), new ShadowImpEntity.ShadowImpAttackBehaviour()}
      );
   }

   public int getMiscAnimation() {
      return (Integer)this.entityData.get(DATA_MISC_ANIMATION);
   }

   public void setMiscAnimation(int animation) {
      this.entityData.set(DATA_MISC_ANIMATION, animation);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide() && this.attackCooldown > 0) {
         this.attackCooldown--;
      }

      if (this.getMiscAnimation() != 0) {
         this.miscAnimationTicks++;
         if (!this.isAlive()) {
            return;
         }

         if (!this.level().isClientSide()) {
            LivingEntity target = this.getTarget();
            if (this.getMiscAnimation() == 2 && this.miscAnimationTicks == 10) {
               if (target != null && target.onGround()) {
                  this.spawnEarthSpikeUnder(target);
               } else {
                  this.shadowBallAttack(0.1, false, target);
               }

               this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.STONE_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
         }

         if (this.miscAnimationTicks >= this.getAnimationTick(this.getMiscAnimation())) {
            this.setMiscAnimation(0);
            this.miscAnimationTicks = 0;
         }
      }

      if (!this.level().isClientSide()) {
         LivingEntity target = this.getTarget();
         if (target != null && this.isAlive()) {
            if (target.isAlive() && this.distanceToSqr(target) > 3.0) {
               this.setFlying(true);
            } else {
               this.setFlying(false);
            }
         } else {
            this.setFlying(false);
         }

         this.handleFlying(this);
      }
   }

   private int getAnimationTick(int miscAnimation) {
      return miscAnimation == 1 ? 30 : 20;
   }

   public void spawnEarthSpikeUnder(LivingEntity target) {
      DarknessSpikeEntity spike = new DarknessSpikeEntity(this.level(), this);
      spike.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
      spike.setPos(target.getX(), target.getY(), target.getZ());
      spike.setExtendingTick(10);
      this.level().addFreshEntity(spike);
   }

   public void shadowBallAttack(double multishot, boolean negativeAngle, @Nullable LivingEntity target) {
      ShadowBallProjectile shadowBall = new ShadowBallProjectile(this.level(), this);
      shadowBall.setSkill(SkillUtils.getSkillOrNull(this, (ManasSkill)ExtraMysticismSkills.DARKNESS_MANIPULATION.get()));
      shadowBall.setElement(Element.DARKNESS);
      float radius = 2.0F;
      float angle = (float) (Math.PI / 180.0) * this.yBodyRot;
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      shadowBall.setPos(this.getX() + extraX, this.getEyeY() - 0.5, this.getZ() + extraZ);
      if (multishot > 0.0) {
         int rot = negativeAngle ? -145 : 145;
         float yaw = this.getYRot() * (float) (Math.PI / 180.0);
         float f3 = Mth.sin((float)(yaw + Math.toRadians(rot)));
         float f = -Mth.cos((float)(yaw + Math.toRadians(rot)));
         shadowBall.setPos(shadowBall.getX() + f3 * multishot, shadowBall.getY(), shadowBall.getZ() + f * multishot);
      }

      shadowBall.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
      shadowBall.setNoGravity(true);
      shadowBall.setElementalAttack(true);
      Vec3 velocityVector;
      if (this.getFirstPassenger() != null) {
         velocityVector = this.getFirstPassenger().getViewVector(2.0F);
      } else if (target != null) {
         velocityVector = new Vec3(target.getX() - shadowBall.getX(), target.getEyeY() - 0.2 - shadowBall.getY(), target.getZ() - shadowBall.getZ());
      } else {
         velocityVector = this.getViewVector(2.0F);
      }

      shadowBall.shoot(velocityVector.x(), velocityVector.y(), velocityVector.z(), 1.0F, 0.0F);
      this.level().addFreshEntity(shadowBall);
   }

   public void switchMoveControl(MoveControl moveControl) {
      this.moveControl = moveControl;
   }

   public void switchNavigation(PathNavigation pathNavigation) {
      this.navigation = pathNavigation;
   }

   public void setFlying(boolean b) {
      this.entityData.set(FLYING, b);
   }

   public boolean isFlying() {
      return (Boolean)this.entityData.get(FLYING);
   }

   public void setWasFlying(boolean b) {
      this.wasFlying = b;
   }

   public boolean wasFlying() {
      return this.wasFlying;
   }

   public void setFlyingTick(int i) {
      this.flyingTick = i;
   }

   public int getFlyingTick() {
      return this.flyingTick;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "movement", 5, this::movementPredicate));
      controllers.add(new AnimationController(this, "attack", 0, this::attackPredicate));
   }

   private <E extends GeoEntity> PlayState movementPredicate(AnimationState<E> state) {
      if (this.isFlying()) {
         state.getController().setAnimation(RawAnimation.begin().thenLoop("animation.shadow_imp.fly"));
      } else if (state.isMoving()) {
         state.getController().setAnimation(RawAnimation.begin().thenLoop("animation.shadow_imp.fly"));
      } else {
         state.getController().setAnimation(RawAnimation.begin().thenLoop("animation.shadow_imp.idle"));
      }

      return PlayState.CONTINUE;
   }

   private <E extends GeoEntity> PlayState attackPredicate(AnimationState<E> state) {
      if (this.getMiscAnimation() != 2) {
         return PlayState.STOP;
      }

      LivingEntity target = this.getTarget();
      state.getController()
         .setAnimation(
            target != null && target.onGround()
               ? RawAnimation.begin().thenPlay("animation.shadow_imp.attack")
               : RawAnimation.begin().thenPlay("animation.model.beam_attack")
         );
      return PlayState.CONTINUE;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public static class ShadowImpAttackBehaviour extends ExtendedBehaviour<ShadowImpEntity> {
      private static final int COOLDOWN = 40;

      protected boolean checkExtraStartConditions(ServerLevel level, ShadowImpEntity entity) {
         return entity.getMiscAnimation() == 0 && entity.attackCooldown <= 0;
      }

      protected void start(ShadowImpEntity entity) {
         entity.setMiscAnimation(2);
         entity.attackCooldown = 40;
      }

      protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
         return List.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));
      }
   }
}
