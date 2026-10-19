package com.chronodawn.entities.mobs;

import com.chronodawn.registry.ModEntities;
import com.chronodawn.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SecondwingFowlEntity extends Animal {
   public SecondwingFowlEntity(EntityType<? extends SecondwingFowlEntity> entityType, Level level) {
      super(entityType, level);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
      this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
      this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, stack -> this.isFood(stack), false));
      this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0).add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   public static boolean checkSecondwingFowlSpawnRules(
      EntityType<SecondwingFowlEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return Animal.checkAnimalSpawnRules(entityType, level, spawnType, pos, random);
   }

   public boolean isFood(ItemStack stack) {
      return stack.is(Items.WHEAT_SEEDS)
         || stack.is(Items.BEETROOT_SEEDS)
         || stack.is(Items.MELON_SEEDS)
         || stack.is(Items.PUMPKIN_SEEDS)
         || stack.is(Items.TORCHFLOWER_SEEDS)
         || stack.is(Items.PITCHER_POD)
         || stack.is(ModItems.TIME_WHEAT_SEEDS)
         || stack.is(ModItems.CHRONO_MELON_SEEDS);
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
      return (AgeableMob)((EntityType)ModEntities.SECONDWING_FOWL.get()).create(level);
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.CHICKEN_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.CHICKEN_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.CHICKEN_DEATH;
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
   }
}
