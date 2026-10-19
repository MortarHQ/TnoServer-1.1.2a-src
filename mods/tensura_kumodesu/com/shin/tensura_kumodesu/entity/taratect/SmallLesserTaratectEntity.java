package com.shin.tensura_kumodesu.entity.taratect;

import io.github.manasmods.tensura.entity.template.subclass.IClimbing;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SmallLesserTaratectEntity extends Monster implements GeoEntity, SmartBrainOwner<SmallLesserTaratectEntity>, IClimbing {
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   public static final DataTicket<Float> SWING_AMOUNT = new DataTicket("swing_amount", Float.class);
   private float customHeadYaw;
   private float customHeadPitch;
   private boolean isMoving;
   private boolean isCrouching;
   private float limbSwing;
   private float limbSwingAmount;
   private Player owner;

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 8.0)
         .add(Attributes.MOVEMENT_SPEED, 0.28)
         .add(Attributes.ATTACK_DAMAGE, 4.0)
         .add(Attributes.FOLLOW_RANGE, 12.0);
   }

   public SmallLesserTaratectEntity(EntityType<? extends SmallLesserTaratectEntity> type, Level level) {
      super(type, level);
   }

   public float getLimbSwingAmount() {
      return this.limbSwingAmount;
   }

   public void setLimbSwingAmount(float limbSwingAmount) {
      this.limbSwingAmount = limbSwingAmount;
   }

   public float getLimbSwing() {
      return this.limbSwing;
   }

   public void setLimbSwing(float limbSwing) {
      this.limbSwing = limbSwing;
   }

   public Player getOwner() {
      return this.owner;
   }

   public void setOwner(Player owner) {
      this.owner = owner;
   }

   public void setPlayerState(boolean moving, boolean crouching) {
      this.isMoving = moving;
      this.isCrouching = crouching;
   }

   public boolean isPlayerMoving() {
      return this.isMoving;
   }

   public boolean isPlayerCrouching() {
      return this.isCrouching;
   }

   public void setCustomHeadRotation(float yaw, float pitch) {
      this.customHeadYaw = yaw;
      this.customHeadPitch = pitch;
   }

   public float getCustomHeadYaw() {
      return this.customHeadYaw;
   }

   public float getCustomHeadPitch() {
      return this.customHeadPitch;
   }

   public void tick() {
      super.tick();
   }

   public boolean doHurtTarget(Entity target) {
      return super.doHurtTarget(target);
   }

   protected @NotNull Provider<?> brainProvider() {
      return new SmartBrainProvider(this);
   }

   protected void customServerAiStep() {
      this.tickBrain(this);
   }

   public List<? extends ExtendedSensor<? extends SmallLesserTaratectEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyPlayersSensor(), new HurtBySensor(), new NearbyLivingEntitySensor().setScanRate(entity -> 20)});
   }

   public BrainActivityGroup<SmallLesserTaratectEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(new Behavior[]{new LookAtTarget(), new MoveToWalkTarget()});
   }

   public BrainActivityGroup<SmallLesserTaratectEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  new TargetOrRetaliate()
                     .attackablePredicate(
                        target -> target instanceof Player
                           || target instanceof IronGolem
                           || target instanceof Wolf
                           || target instanceof Turtle turtle && turtle.isBaby() && !turtle.isInWater()
                     ),
                  new SetPlayerLookTarget(),
                  new SetRandomLookTarget()
               }
            ),
            new OneRandomBehaviour(
               new ExtendedBehaviour[]{new SetRandomWalkTarget().cooldownFor(entity -> 80), new Idle().runFor(entity -> entity.getRandom().nextInt(40, 80))}
            )
         }
      );
   }

   public BrainActivityGroup<SmallLesserTaratectEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{new InvalidateAttackTarget(), new SetWalkTargetToAttackTarget().speedMod((entity, target) -> 1.2F), new AnimatableMeleeAttack(10)}
      );
   }

   public int getMaxSpawnClusterSize() {
      return 4;
   }

   public void makeStuckInBlock(BlockState state, Vec3 vec3) {
      if (!state.is(Blocks.COBWEB)) {
         super.makeStuckInBlock(state, vec3);
      }
   }

   public boolean isClimbing() {
      return false;
   }

   public void setClimbing(boolean climbing) {
   }

   public float getClimbSpeedMultiplier() {
      return super.getClimbSpeedMultiplier();
   }

   public boolean collidingWall(Entity entity) {
      return super.collidingWall(entity);
   }

   public void handleClimbing(LivingEntity entity) {
      super.handleClimbing(entity);
   }

   public void handleRideableClimbing(LivingEntity entity, Vec3 vec3) {
      super.handleRideableClimbing(entity, vec3);
   }

   public Vec3 rotateVector(Vec3 vec, double degrees) {
      return super.rotateVector(vec, degrees);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "movement_controller", 5, state -> {
         AnimationController<?> controller = state.getController();
         Player player = this.getOwner();
         Vec3 vel;
         if (player != null) {
            vel = player.getDeltaMovement();
         } else {
            vel = this.getDeltaMovement();
         }

         double horizontalSpeed = Math.sqrt(vel.x * vel.x + vel.z * vel.z);
         if (horizontalSpeed > 0.01) {
            float targetSpeed = (float)Mth.clamp(horizontalSpeed * 4.0, 0.6F, 2.5);
            float smoothSpeed = (float)Mth.lerp(0.2F, controller.getAnimationSpeed(), targetSpeed);
            controller.setAnimationSpeed(smoothSpeed);
            controller.setAnimation(DefaultAnimations.WALK);
         } else {
            controller.setAnimationSpeed(1.0);
            controller.setAnimation(DefaultAnimations.IDLE);
         }

         return PlayState.CONTINUE;
      }));
      controllers.add(
         new AnimationController(
            this,
            "pose_controller",
            0,
            state -> !this.isCrouching() && !this.isPlayerCrouching() ? PlayState.STOP : state.setAndContinue(DefaultAnimations.SNEAK)
         )
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }

   public double getBoneResetTime() {
      return 1.0;
   }
}
