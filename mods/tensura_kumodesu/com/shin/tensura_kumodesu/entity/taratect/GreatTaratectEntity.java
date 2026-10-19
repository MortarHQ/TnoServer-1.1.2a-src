package com.shin.tensura_kumodesu.entity.taratect;

import io.github.manasmods.tensura.entity.template.subclass.IClimbing;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
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
import software.bernie.geckolib.util.GeckoLibUtil;

public class GreatTaratectEntity extends TaratectEntity implements GeoEntity, IClimbing {
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   private float customHeadYaw;
   private float customHeadPitch;
   private boolean isMoving;
   private boolean isCrouching;
   private Player owner;

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 500.0)
         .add(Attributes.MOVEMENT_SPEED, 0.2)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.12)
         .add(Attributes.ATTACK_DAMAGE, 25.0)
         .add(Attributes.ATTACK_SPEED, 4.4)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 4.0)
         .add(Attributes.FOLLOW_RANGE, 16.0);
   }

   public GreatTaratectEntity(EntityType<? extends GreatTaratectEntity> type, Level level) {
      super(type, level);
   }

   @Override
   public Player getOwner() {
      return this.owner;
   }

   @Override
   public void setOwner(Player owner) {
      this.owner = owner;
   }

   @Override
   public void setPlayerState(boolean moving, boolean crouching) {
      this.isMoving = moving;
      this.isCrouching = crouching;
   }

   @Override
   public boolean isPlayerMoving() {
      return this.isMoving;
   }

   @Override
   public boolean isPlayerCrouching() {
      return this.isCrouching;
   }

   @Override
   public void setCustomHeadRotation(float yaw, float pitch) {
      this.customHeadYaw = yaw;
      this.customHeadPitch = pitch;
   }

   @Override
   public float getCustomHeadYaw() {
      return this.customHeadYaw;
   }

   @Override
   public float getCustomHeadPitch() {
      return this.customHeadPitch;
   }

   @Override
   public void tick() {
      super.tick();
   }

   @Override
   public boolean doHurtTarget(Entity target) {
      return super.doHurtTarget(target);
   }

   @Override
   protected @NotNull Provider<?> brainProvider() {
      return new SmartBrainProvider(this);
   }

   @Override
   protected void customServerAiStep() {
      this.tickBrain(this);
   }

   @Override
   public List<? extends ExtendedSensor<? extends GreatTaratectEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyPlayersSensor(), new HurtBySensor(), new NearbyLivingEntitySensor().setScanRate(entity -> 20)});
   }

   @Override
   public int getMaxSpawnClusterSize() {
      return 4;
   }

   @Override
   public void makeStuckInBlock(BlockState state, Vec3 vec3) {
      if (!state.is(Blocks.COBWEB)) {
         super.makeStuckInBlock(state, vec3);
      }
   }

   @Override
   public boolean isClimbing() {
      return false;
   }

   @Override
   public void setClimbing(boolean climbing) {
   }

   @Override
   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "movement_controller", 5, state -> {
         AnimationController<?> controller = state.getController();
         Player player = this.getOwner();
         Vec3 vel = ((LivingEntity)Objects.requireNonNullElse(player, this)).getDeltaMovement();
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

   @Override
   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }
}
