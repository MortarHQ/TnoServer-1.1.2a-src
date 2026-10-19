package com.shin.tensura_kumodesu.entity.taratect;

import io.github.manasmods.tensura.entity.template.subclass.IClimbing;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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

public class SmallTaratectEntity extends SmallLesserTaratectEntity implements GeoEntity, IClimbing {
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   private float customHeadYaw;
   private float customHeadPitch;
   private boolean isMoving;
   private boolean isCrouching;
   private Player owner;
   private float limbSwing;
   private float limbSwingAmount;
   private UUID ownerId;

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 16.0)
         .add(Attributes.MOVEMENT_SPEED, 0.12)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.0)
         .add(Attributes.ATTACK_DAMAGE, 3.2)
         .add(Attributes.ATTACK_SPEED, 4.4)
         .add(Attributes.FOLLOW_RANGE, 16.0);
   }

   public SmallTaratectEntity(EntityType<? extends SmallTaratectEntity> type, Level level) {
      super(type, level);
   }

   @Override
   public float getLimbSwingAmount() {
      return this.limbSwingAmount;
   }

   @Override
   public void setLimbSwingAmount(float limbSwingAmount) {
      this.limbSwingAmount = limbSwingAmount;
   }

   @Override
   public float getLimbSwing() {
      return this.limbSwing;
   }

   @Override
   public void setLimbSwing(float limbSwing) {
      this.limbSwing = limbSwing;
   }

   @Override
   public Player getOwner() {
      return this.level().isClientSide && this.level() instanceof ClientLevel clientLevel ? clientLevel.getPlayerByUUID(this.ownerId) : null;
   }

   @Override
   public void setOwner(Player owner) {
      this.ownerId = owner.getUUID();
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
   public List<? extends ExtendedSensor<? extends SmallTaratectEntity>> getSensors() {
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
      controllers.add(
         new AnimationController(
            this,
            "movement_controller",
            5,
            state -> state.setAndContinue(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 ? DefaultAnimations.WALK : DefaultAnimations.IDLE)
         )
      );
      controllers.add(
         new AnimationController(
            this,
            "pose_controller",
            0,
            state -> ((SmallTaratectEntity)state.getAnimatable()).isCrouching() ? state.setAndContinue(DefaultAnimations.SNEAK) : PlayState.STOP
         )
      );
   }

   @Override
   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }
}
