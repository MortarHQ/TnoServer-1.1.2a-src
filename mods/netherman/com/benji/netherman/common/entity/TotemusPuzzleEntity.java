package com.benji.netherman.common.entity;

import com.benji.netherman.common.block.entity.TotemusHoleBlockEntity;
import com.benji.netherman.init.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
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

public class TotemusPuzzleEntity extends Monster implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(TotemusPuzzleEntity.class, EntityDataSerializers.INT);

   public TotemusPuzzleEntity(EntityType<? extends Monster> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 1.0).add(Attributes.MOVEMENT_SPEED, 0.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(COLOR, 0);
   }

   public int getColor() {
      return (Integer)this.entityData.get(COLOR);
   }

   public void setColor(int color) {
      this.entityData.set(COLOR, color);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
   }

   public boolean isPushable() {
      return false;
   }

   public void tick() {
      super.tick();
      this.yBodyRot = this.yHeadRot;
      if (this.level().isClientSide() && this.tickCount < 20) {
         for (int i = 0; i < 3; i++) {
            this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.05, 0.0);
         }
      }
   }

   public void die(DamageSource cause) {
      if (!this.level().isClientSide()) {
         if (this.level().getBlockEntity(this.blockPosition().below()) instanceof TotemusHoleBlockEntity hole) {
            Player player = cause.getEntity() instanceof Player p ? p : null;
            hole.reportDeath(this.getColor(), player);
         }

         this.playSound(SoundEvents.WITHER_SKELETON_DEATH, 1.0F, 1.0F);
      }

      super.die(cause);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, event -> event.setAndContinue(RawAnimation.begin().thenPlay("spawn").thenLoop("idle"))));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.TOTEMUS_PUZZLE.get();
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("PuzzleColor", this.getColor());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setColor(compound.getInt("PuzzleColor"));
   }
}
