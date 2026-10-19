package com.benji.netherman.common.entity;

import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModSounds;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DoctorEntity extends PathfinderMob implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final EntityDataAccessor<Integer> HINT_STATE = SynchedEntityData.defineId(DoctorEntity.class, EntityDataSerializers.INT);
   public int hintTimer = 0;
   private int sicknessTimer = 1200;
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

   public DoctorEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(HINT_STATE, 0);
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
   }

   public void knockback(double strength, double x, double z) {
   }

   public void push(double x, double y, double z) {
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (this.level().isClientSide()) {
         return InteractionResult.sidedSuccess(true);
      }

      ItemStack stack = player.getItemInHand(hand);
      List<BelieverEntity> nearbyBelievers = this.level().getEntitiesOfClass(BelieverEntity.class, this.getBoundingBox().inflate(10.0));
      boolean hasSickBeliever = nearbyBelievers.stream().anyMatch(BelieverEntity::isSick);
      int currentState = (Integer)this.entityData.get(HINT_STATE);
      if (hasSickBeliever) {
         if (currentState == 1) {
            this.entityData.set(HINT_STATE, 2);
         } else {
            this.entityData.set(HINT_STATE, 1);
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.5, this.getZ(), 5, 0.3, 0.3, 0.3, 0.0);
         }

         this.hintTimer = 80;
         this.playSound(SoundEvents.VILLAGER_NO, 1.0F, this.getVoicePitch());
      } else if (stack.is(Items.GOLDEN_APPLE)) {
         if (!player.isCreative()) {
            stack.shrink(1);
         }

         this.playSound(SoundEvents.VILLAGER_TRADE, 1.0F, this.getVoicePitch());
         this.giveRandomPotion(player);
         this.entityData.set(HINT_STATE, 3);
         this.hintTimer = 60;
      } else {
         this.entityData.set(HINT_STATE, 3);
         this.hintTimer = 80;
         this.playSound(SoundEvents.VILLAGER_TRADE, 1.0F, this.getVoicePitch());
      }

      return InteractionResult.sidedSuccess(false);
   }

   private void giveRandomPotion(Player player) {
      ItemStack potion = new ItemStack(Items.POTION);
      boolean restrictMods = (Boolean)AzazelConfig.DOCTOR_VANILLA_POTIONS_ONLY.get();
      List<? extends String> allowedNamespaces = (List<? extends String>)AzazelConfig.DOCTOR_ALLOWED_MOD_NAMESPACES.get();
      List<Reference<MobEffect>> availableEffects = this.level().registryAccess().registryOrThrow(Registries.MOB_EFFECT).holders().filter(holder -> {
         if (!restrictMods) {
            return true;
         }

         if (holder.unwrapKey().isEmpty()) {
            return false;
         }

         String namespace = ((ResourceKey)holder.unwrapKey().get()).location().getNamespace();
         return allowedNamespaces.contains(namespace);
      }).toList();
      if (!availableEffects.isEmpty()) {
         Reference<MobEffect> randomEffect = availableEffects.get(this.random.nextInt(availableEffects.size()));
         int durationTicks = 200 + this.random.nextInt(1600);
         int amplifier = this.random.nextInt(4);
         potion.set(
            DataComponents.POTION_CONTENTS,
            new PotionContents(Optional.empty(), Optional.empty(), List.of(new MobEffectInstance(randomEffect, durationTicks, amplifier)))
         );
         Vec3 dir = player.position().subtract(this.position()).normalize().scale(0.3);
         ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1.0, this.getZ(), potion);
         itemEntity.setDeltaMovement(dir.x, 0.3, dir.z);
         this.level().addFreshEntity(itemEntity);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         if (this.hintTimer > 0) {
            this.hintTimer--;
            if (this.hintTimer <= 0) {
               this.entityData.set(HINT_STATE, 0);
            }
         }

         if (this.sicknessTimer > 0) {
            this.sicknessTimer--;
         } else {
            this.sicknessTimer = 2400;
            List<BelieverEntity> nearbyBelievers = this.level().getEntitiesOfClass(BelieverEntity.class, this.getBoundingBox().inflate(15.0));
            List<BelieverEntity> healthyBelievers = nearbyBelievers.stream().filter(b -> !b.isSick()).toList();
            if (!healthyBelievers.isEmpty()) {
               BelieverEntity victim = healthyBelievers.get(this.random.nextInt(healthyBelievers.size()));
               victim.setSick(true);
               if (this.level() instanceof ServerLevel serverLevel) {
                  serverLevel.sendParticles(ParticleTypes.SNEEZE, victim.getX(), victim.getY() + 1.0, victim.getZ(), 15, 0.3, 0.3, 0.3, 0.05);
               }
            }
         }
      }
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("SicknessTimer", this.sicknessTimer);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("SicknessTimer")) {
         this.sicknessTimer = tag.getInt("SicknessTimer");
      }
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSounds.DOCTOR.get();
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.PILLAGER_HURT;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.PILLAGER_DEATH;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, event -> event.setAndContinue(IDLE_ANIM)));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
