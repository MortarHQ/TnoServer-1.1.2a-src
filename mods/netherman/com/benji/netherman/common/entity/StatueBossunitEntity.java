package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModSounds;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class StatueBossunitEntity extends Monster implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private int attackTimer = 300;
   private final double ATTACK_RADIUS = 20.0;
   private static final List<Holder<MobEffect>> DEBUFFS = List.of(
      MobEffects.MOVEMENT_SLOWDOWN, MobEffects.POISON, MobEffects.UNLUCK, MobEffects.WITHER, MobEffects.DARKNESS
   );

   public StatueBossunitEntity(EntityType<? extends Monster> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 15.0).add(Attributes.MOVEMENT_SPEED, 0.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 20.0F));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void knockback(double strength, double x, double z) {
   }

   public void push(double x, double y, double z) {
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         this.attackTimer--;
         if (this.attackTimer <= 0) {
            List<Player> nearbyPlayers = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(20.0));
            if (!nearbyPlayers.isEmpty()) {
               this.playSound((SoundEvent)ModSounds.SPINNING_WHEEL.get(), 1.0F, this.getVoicePitch());
               if (this.level() instanceof ServerLevel serverLevel) {
                  serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getY() + 1.5, this.getZ(), 40, 1.0, 1.0, 1.0, 0.1);
               }

               for (Player player : nearbyPlayers) {
                  Holder<MobEffect> randomEffect = DEBUFFS.get(this.random.nextInt(DEBUFFS.size()));
                  int randomAmplifier = this.random.nextInt(3);
                  player.addEffect(new MobEffectInstance(randomEffect, 400, randomAmplifier));
               }

               this.attackTimer = 200;
            } else {
               this.attackTimer = 20;
            }
         }
      }
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSounds.UNIT_IDLE.get();
   }

   public float getVoicePitch() {
      return 0.8F + this.random.nextFloat() * 0.4F;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("AttackTimer", this.attackTimer);
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      int rand = this.random.nextInt(3);
      return rand == 0
         ? (SoundEvent)ModSounds.STATUE_HURT_1.get()
         : (rand == 1 ? (SoundEvent)ModSounds.STATUE_HURT_2.get() : (SoundEvent)ModSounds.STATUE_HURT_3.get());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.attackTimer = tag.getInt("AttackTimer");
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, event -> event.setAndContinue(RawAnimation.begin().thenLoop("idle"))));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
