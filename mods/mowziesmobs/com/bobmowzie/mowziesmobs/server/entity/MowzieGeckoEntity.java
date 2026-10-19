package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.dynamics.GeckoDynamicChain;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.SimpleAnimationAbility;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class MowzieGeckoEntity extends MowzieEntity implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   protected MowzieAnimationController<MowzieGeckoEntity> controller = new MowzieAnimationController(this, "controller", 5, this::predicate, 0.0);
   public GeckoDynamicChain[] dynamicChains;
   public double moveDirForward;
   public double moveDirBackward;
   public double moveDirLeft;
   public double moveDirRight;
   public double prevMoveDirForward;
   public double prevMoveDirBackward;
   public double prevMoveDirLeft;
   public double prevMoveDirRight;
   public float lastRenderUpdateTime;
   private static RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

   public MowzieGeckoEntity(EntityType<? extends MowzieEntity> type, Level world) {
      super(type, world);
   }

   @Override
   protected int getDeathDuration() {
      Ability<?> deathAbility = this.getActiveAbility();
      return deathAbility instanceof SimpleAnimationAbility ? ((SimpleAnimationAbility)deathAbility).getDuration() : 20;
   }

   @Override
   public void writeSpawnData(@NotNull RegistryFriendlyByteBuf buffer) {
   }

   public abstract AbilityType getHurtAbility();

   public abstract AbilityType getDeathAbility();

   public boolean shouldPlayHurtAnimation(DamageSource source, float damage) {
      return this.playsHurtAnimation;
   }

   public boolean hurt(DamageSource source, float damage) {
      boolean attack = super.hurt(source, damage);
      if (attack) {
         if (this.getHealth() > 0.0F
            && (this.getActiveAbility() == null || this.getActiveAbility().damageInterrupts() && damage >= this.getActiveAbility().damageInterruptThreshold())
            && this.shouldPlayHurtAnimation(source, damage)) {
            this.sendAbilityMessage(this.getHurtAbility());
         } else if (this.getHealth() <= 0.0F) {
            this.sendAbilityMessage(this.getDeathAbility());
         }
      }

      return attack;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.doDirectionalWalk()) {
         this.prevMoveDirForward = this.moveDirForward;
         this.prevMoveDirBackward = this.moveDirBackward;
         this.prevMoveDirLeft = this.moveDirLeft;
         this.prevMoveDirRight = this.moveDirRight;
         Vec3 moveVec = this.getDeltaMovement().yRot((float)Math.toRadians(this.yBodyRot + 90.0F)).multiply(1.0, 0.0, 1.0);
         if (moveVec.lengthSqr() > 1.0E-4F) {
            moveVec = moveVec.normalize();
            this.moveDirForward = Math.max(0.0, new Vec3(1.0, 0.0, 0.0).dot(moveVec));
            this.moveDirBackward = Math.max(0.0, new Vec3(-1.0, 0.0, 0.0).dot(moveVec));
            this.moveDirLeft = Math.max(0.0, new Vec3(0.0, 0.0, -1.0).dot(moveVec));
            this.moveDirRight = Math.max(0.0, new Vec3(0.0, 0.0, 1.0).dot(moveVec));
         }
      }
   }

   protected boolean doDirectionalWalk() {
      return false;
   }

   protected <E extends GeoEntity> PlayState predicate(AnimationState<E> state) {
      AbilityData abilityData = this.getAbilityData();
      if (abilityData == null) {
         return PlayState.STOP;
      } else if (DataHandler.<FrozenData>getData(this, DataHandler.FROZEN_DATA).getFrozen()) {
         return PlayState.STOP;
      } else if (abilityData.getActiveAbility() != null) {
         this.getController().transitionLength(0);
         return abilityData.animationPredicate(state, null);
      } else {
         this.loopingAnimations(state);
         return PlayState.CONTINUE;
      }
   }

   protected <E extends GeoEntity> void loopingAnimations(AnimationState<E> event) {
      event.getController().setAnimation(IDLE_ANIM);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(this.controller);
   }

   public MowzieAnimationController<MowzieGeckoEntity> getController() {
      return this.controller;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public AbilityType<?, ?>[] getAbilities() {
      return new AbilityType[0];
   }

   public AbilityData getAbilityData() {
      return DataHandler.getData(this, DataHandler.ABILITY_DATA);
   }

   public Ability<?> getActiveAbility() {
      AbilityData data = this.getAbilityData();
      return data == null ? null : this.getAbilityData().getActiveAbility();
   }

   public AbilityType getActiveAbilityType() {
      Ability<?> ability = this.getActiveAbility();
      return ability == null ? null : ability.getAbilityType();
   }

   public Ability<?> getAbility(AbilityType abilityType) {
      AbilityData data = this.getAbilityData();
      return data == null ? null : this.getAbilityData().getAbilityMap().get(abilityType);
   }

   public void sendAbilityMessage(AbilityType abilityType) {
      AbilityHandler.INSTANCE.sendAbilityMessage(this, abilityType);
   }
}
