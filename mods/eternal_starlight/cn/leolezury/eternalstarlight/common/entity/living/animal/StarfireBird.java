package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.block.StarfireBirdNestBlock;
import cn.leolezury.eternalstarlight.common.block.entity.StarfireBirdNestBlockEntity;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESPoiTypes;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiManager.Occupancy;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class StarfireBird extends Animal implements FlyingAnimal {
   public static final String TAG_SPECIAL_VARIANT = "special_variant";
   private static final String TAG_NEST_POS = "nest_pos";
   private static final String TAG_STAY_OUT_OF_NEST_TICKS = "stay_out_of_nest_ticks";
   private static final String TAG_HAS_EGG = "has_egg";
   private static final String TAG_TRUSTED_PLAYERS = "trusted_players";
   private static final String TAG_GIFT_COUNT = "gift_count";
   private static final String TAG_GIFT_COOLDOWN = "gift_cooldown";
   protected static final EntityDataAccessor<Boolean> SPECIAL_VARIANT = SynchedEntityData.defineId(StarfireBird.class, EntityDataSerializers.BOOLEAN);
   private BlockPos nestPos = null;
   private int nestDestroyedTicks;
   private int stayOutOfNestTicks;
   private boolean hasEgg = false;
   private final List<UUID> trustedPlayers = Lists.newArrayList();
   private int giftCount;
   private int giftCooldown;
   private float oldFlapScale;
   private float flapScale;
   private boolean flying = true;
   private int flyingTicks;
   private int walkingTicks;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState nestIdleAnimationState = new AnimationState();
   public AnimationState flapAnimationState = new AnimationState();

   public boolean isSpecialVariant() {
      return (Boolean)this.getEntityData().get(SPECIAL_VARIANT);
   }

   public void setSpecialVariant(boolean specialVariant) {
      this.getEntityData().set(SPECIAL_VARIANT, specialVariant);
   }

   public void setNestPos(BlockPos nestPos) {
      this.nestPos = nestPos;
   }

   public boolean hasNest() {
      return this.nestPos != null;
   }

   public void setStayOutOfNestTicks(int stayOutOfNestTicks) {
      this.stayOutOfNestTicks = stayOutOfNestTicks;
   }

   public void setHasEgg(boolean hasEgg) {
      this.hasEgg = hasEgg;
   }

   public void addTrustedPlayer(UUID uuid) {
      if (uuid != null) {
         this.trustedPlayers.add(uuid);
      }
   }

   public void addGiftCount() {
      this.giftCount++;
   }

   public float getFlapScale(float partialTick) {
      return Mth.lerp(partialTick, this.oldFlapScale, this.flapScale);
   }

   public StarfireBird(EntityType<? extends StarfireBird> entityType, Level level) {
      super(entityType, level);
      this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.WATER_BORDER, -1.0F);
      this.switchMoveType(true);
   }

   protected PathNavigation createNavigation(Level level) {
      FlyingPathNavigation pathNavigation = new FlyingPathNavigation(this, level) {
         public boolean isStableDestination(BlockPos blockPos) {
            return this.level.getBlockState(blockPos).isAir();
         }
      };
      pathNavigation.setCanOpenDoors(false);
      pathNavigation.setCanFloat(true);
      pathNavigation.setCanPassDoors(true);
      return pathNavigation;
   }

   private void switchMoveType(boolean fly) {
      this.flyingTicks = 0;
      this.walkingTicks = 0;
      this.flying = fly;
      this.setNoGravity(fly);
      this.stopInPlace();
      if (fly) {
         this.moveControl = new FlyingMoveControl(this, 20, true);
         this.navigation = this.createNavigation(this.level());
      } else {
         this.moveControl = new MoveControl(this);
         this.navigation = super.createNavigation(this.level());
      }
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SPECIAL_VARIANT, false);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new StarfireBird.StarfireBirdBreedGoal());
      this.goalSelector.addGoal(2, new TemptGoal(this, 1.0, stack -> stack.is(ESTags.Items.STARFIRE_BIRD_FOOD), false));
      this.goalSelector.addGoal(3, new StarfireBird.StarfireBirdEnterNestGoal());
      this.goalSelector.addGoal(4, new StarfireBird.StarfireBirdGoToNestGoal());
      this.goalSelector.addGoal(5, new StarfireBird.StarfireBirdLandGoal());
      this.goalSelector.addGoal(6, new StarfireBird.StarfireBirdTakeOffGoal());
      this.goalSelector.addGoal(7, new StarfireBird.StarfireBirdGiftGoal());
      this.goalSelector.addGoal(8, new PanicGoal(this, 1.25));
      this.goalSelector.addGoal(9, new RandomFlyGoal(this) {
         @Override
         public boolean canUse() {
            return super.canUse() && StarfireBird.this.flying;
         }

         @Override
         public boolean canContinueToUse() {
            return super.canContinueToUse() && StarfireBird.this.flying;
         }
      });
      this.goalSelector.addGoal(10, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F) {
         public boolean canUse() {
            return super.canUse() && !StarfireBird.this.flying;
         }

         public boolean canContinueToUse() {
            return super.canContinueToUse() && !StarfireBird.this.flying;
         }
      });
      this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.starfireBird.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.starfireBird.armor())
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.FLYING_SPEED, 0.75);
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide) {
         if (this.flying) {
            this.flyingTicks++;
            this.walkingTicks = 0;
         } else {
            this.walkingTicks++;
            this.flyingTicks = 0;
         }

         this.giftCooldown--;
         if (this.giftCooldown < 0) {
            this.giftCooldown = 0;
         }

         if (!this.isNestValid()) {
            this.nestDestroyedTicks++;
         } else {
            this.nestDestroyedTicks = 0;
         }

         if (this.nestDestroyedTicks > 600) {
            this.nestPos = null;
         }

         this.stayOutOfNestTicks--;
         if (this.stayOutOfNestTicks < 0) {
            this.stayOutOfNestTicks = 0;
         }

         if (this.nestPos == null && this.tickCount % 20 == 0 && !this.isBaby() && this.level() instanceof ServerLevel serverLevel) {
            PoiManager poiManager = serverLevel.getPoiManager();
            List<BlockPos> availableNests = poiManager.findAllClosestFirstWithType(
                  poi -> poi.is(ESPoiTypes.STARFIRE_BIRD_NEST.getResourceKey()), this::canBeSeenAsNewNest, this.blockPosition(), 48, Occupancy.ANY
               )
               .limit(5L)
               .<BlockPos>map(Pair::getSecond)
               .toList();
            if (!availableNests.isEmpty()) {
               this.nestPos = availableNests.get(this.getRandom().nextInt(availableNests.size()));
               this.nestDestroyedTicks = 0;

               for (int i = 0; i < 5; i++) {
                  double d = this.getRandom().nextGaussian() * 0.02;
                  double e = this.getRandom().nextGaussian() * 0.02;
                  double f = this.getRandom().nextGaussian() * 0.02;
                  ESPlatform.INSTANCE
                     .sendToAllClients(
                        serverLevel,
                        new ParticlePacket(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d, e, f)
                     );
               }
            }
         }
      } else {
         this.idleAnimationState.startIfStopped(this.tickCount);
         this.flapAnimationState.startIfStopped(this.tickCount);
         this.oldFlapScale = this.flapScale;
         if (this.onGround()) {
            this.flapScale -= 0.1F;
         } else {
            this.flapScale += 0.3F;
         }

         this.flapScale = Mth.clamp(this.flapScale, 0.0F, 1.0F);
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      boolean success = super.hurt(source, amount);
      if (success && source.getEntity() instanceof Player player) {
         this.trustedPlayers.remove(player.getUUID());
      }

      return success;
   }

   private boolean wantsToEnterNest() {
      return this.stayOutOfNestTicks <= 0 && !this.isInLove();
   }

   private boolean isNestValid() {
      return this.nestPos != null
         && this.level().getBlockState(this.nestPos).is(ESTags.Blocks.STARFIRE_BIRD_NESTS)
         && this.level().getBlockEntity(this.nestPos) instanceof StarfireBirdNestBlockEntity;
   }

   private boolean canBeSeenAsNewNest(BlockPos pos) {
      BlockState state = this.level().getBlockState(pos);
      return state.is(ESTags.Blocks.STARFIRE_BIRD_NESTS)
         && this.level().getBlockEntity(pos) instanceof StarfireBirdNestBlockEntity entity
         && !entity.isFullForAdults()
         && entity.getItems().stream().anyMatch(stack -> !stack.isEmpty());
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
      if (this.random.nextInt(20) == 0) {
         this.setSpecialVariant(true);
      }

      return super.finalizeSpawn(level, instance, spawnType, data);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setSpecialVariant(compoundTag.getBoolean("special_variant"));
      NbtUtils.readBlockPos(compoundTag, "nest_pos").ifPresent(pos -> this.nestPos = pos);
      this.stayOutOfNestTicks = compoundTag.getInt("stay_out_of_nest_ticks");
      this.hasEgg = compoundTag.getBoolean("has_egg");
      this.trustedPlayers.clear();
      if (compoundTag.contains("trusted_players", 9)) {
         for (Tag tag : compoundTag.getList("trusted_players", 11)) {
            if (tag != null && tag.getType() == IntArrayTag.TYPE && ((IntArrayTag)tag).getAsIntArray().length == 4) {
               this.trustedPlayers.add(NbtUtils.loadUUID(tag));
            }
         }
      }

      this.giftCount = compoundTag.getInt("gift_count");
      this.giftCooldown = compoundTag.getInt("gift_cooldown");
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("special_variant", this.isSpecialVariant());
      if (this.hasNest()) {
         compoundTag.put("nest_pos", NbtUtils.writeBlockPos(this.nestPos));
      }

      compoundTag.putInt("stay_out_of_nest_ticks", this.stayOutOfNestTicks);
      compoundTag.putBoolean("has_egg", this.hasEgg);
      ListTag listTag = new ListTag();

      for (UUID uuid : this.trustedPlayers) {
         if (uuid != null) {
            listTag.add(NbtUtils.createUUID(uuid));
         }
      }

      compoundTag.put("trusted_players", listTag);
      compoundTag.putInt("gift_count", this.giftCount);
      compoundTag.putInt("gift_cooldown", this.giftCooldown);
   }

   public boolean isFood(ItemStack stack) {
      return stack.is(ESTags.Items.STARFIRE_BIRD_FOOD);
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
      return (AgeableMob)ESEntities.STARFIRE_BIRD.get().create(level);
   }

   public boolean isFlying() {
      return !this.onGround();
   }

   protected void ageBoundaryReached() {
      super.ageBoundaryReached();
      this.refreshDimensions();
   }

   public void setAge(int age) {
      super.setAge(age);
      if (this.isBaby()) {
         this.switchMoveType(false);
      }
   }

   public EntityDimensions getDefaultDimensions(Pose pose) {
      return this.isBaby() ? super.getDefaultDimensions(pose).scale(0.75F) : super.getDefaultDimensions(pose);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return ESSoundEvents.STARFIRE_BIRD_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return ESSoundEvents.STARFIRE_BIRD_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return ESSoundEvents.STARFIRE_BIRD_DEATH.get();
   }

   public static boolean checkStarfireBirdSpawnRules(
      EntityType<? extends StarfireBird> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return level.getBlockState(pos.below()).is(BlockTags.DIRT) && ESConfig.INSTANCE.mobsConfig.starfireBird.canSpawn();
   }

   private class StarfireBirdBreedGoal extends BreedGoal {
      public StarfireBirdBreedGoal() {
         super(StarfireBird.this, 1.0);
      }

      protected void breed() {
         ServerPlayer loveCause = this.animal.getLoveCause();
         if (loveCause == null && this.partner != null && this.partner.getLoveCause() != null) {
            loveCause = this.partner.getLoveCause();
         }

         if (loveCause != null && this.partner != null) {
            loveCause.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(loveCause, this.animal, this.partner, null);
            StarfireBird.this.addTrustedPlayer(loveCause.getUUID());
            if (this.partner instanceof StarfireBird bird) {
               bird.addTrustedPlayer(loveCause.getUUID());
            }
         }

         StarfireBird.this.setHasEgg(true);
         this.animal.setAge(6000);
         this.partner.setAge(6000);
         this.animal.resetLove();
         this.partner.resetLove();
         RandomSource random = this.animal.getRandom();
         if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
         }

         StarfireBird.this.stayOutOfNestTicks = 0;
      }
   }

   private class StarfireBirdEnterNestGoal extends Goal {
      public boolean canUse() {
         return StarfireBird.this.hasNest()
               && StarfireBird.this.wantsToEnterNest()
               && StarfireBird.this.nestPos.closerToCenterThan(StarfireBird.this.position(), 2.0)
               && StarfireBird.this.level().getBlockEntity(StarfireBird.this.nestPos) instanceof StarfireBirdNestBlockEntity nest
            ? !nest.isFullForAdults()
            : false;
      }

      public boolean canContinueToUse() {
         return false;
      }

      public void start() {
         if (StarfireBird.this.hasNest() && StarfireBird.this.level().getBlockEntity(StarfireBird.this.nestPos) instanceof StarfireBirdNestBlockEntity nest) {
            if (StarfireBird.this.hasEgg) {
               BlockState nestState = StarfireBird.this.level().getBlockState(StarfireBird.this.nestPos);
               int eggs = (Integer)nestState.getValue(StarfireBirdNestBlock.EGGS);
               if (eggs < 3) {
                  StarfireBird.this.level().setBlockAndUpdate(StarfireBird.this.nestPos, (BlockState)nestState.setValue(StarfireBirdNestBlock.EGGS, eggs + 1));
                  StarfireBird.this.setHasEgg(false);
               }
            }

            nest.addAdultOccupant(StarfireBird.this);
         }
      }
   }

   private class StarfireBirdGiftGoal extends Goal {
      private Player giftTarget = null;
      private int tryTicks = 0;

      public StarfireBirdGiftGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public boolean canUse() {
         return StarfireBird.this.getRandom().nextInt(reducedTickDelay(120)) == 0 && this.canUseGoal();
      }

      public boolean canContinueToUse() {
         return this.canUseGoal();
      }

      private boolean canUseGoal() {
         if (StarfireBird.this.giftCooldown <= 0 && StarfireBird.this.giftCount > 0) {
            if ((this.giftTarget == null || !this.giftTarget.isAlive()) && !StarfireBird.this.trustedPlayers.isEmpty()) {
               UUID playerId = StarfireBird.this.trustedPlayers.get(StarfireBird.this.getRandom().nextInt(StarfireBird.this.trustedPlayers.size()));
               this.giftTarget = StarfireBird.this.level().getPlayerByUUID(playerId);
            }

            return this.tryTicks < 300 && this.giftTarget != null && this.giftTarget.isAlive() && !StarfireBird.this.isBaby();
         } else {
            return false;
         }
      }

      public void start() {
         this.tryTicks = 0;
      }

      public void stop() {
         this.tryTicks = 0;
         StarfireBird.this.navigation.stop();
      }

      public void tick() {
         if (this.giftTarget != null) {
            StarfireBird.this.getLookControl().setLookAt(this.giftTarget, 10.0F, StarfireBird.this.getMaxHeadXRot());
            StarfireBird.this.getNavigation().moveTo(this.giftTarget, 1.0);
            if (StarfireBird.this.distanceTo(this.giftTarget) < 3.0F && StarfireBird.this.level() instanceof ServerLevel serverLevel) {
               LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(ESLootTables.GAMEPLAY_STARFIRE_BIRD_GIFT);

               for (ItemStack item : lootTable.getRandomItems(
                  new net.minecraft.world.level.storage.loot.LootParams.Builder(serverLevel)
                     .withParameter(LootContextParams.ORIGIN, StarfireBird.this.position())
                     .withParameter(LootContextParams.THIS_ENTITY, StarfireBird.this)
                     .create(LootContextParamSets.GIFT)
               )) {
                  BehaviorUtils.throwItem(StarfireBird.this, item, this.giftTarget.position().add(0.0, this.giftTarget.getBbHeight() / 2.0F, 0.0));
               }

               this.giftTarget = null;
               StarfireBird.this.giftCooldown = 600;
               StarfireBird.this.giftCount--;
            }
         }

         this.tryTicks++;
      }
   }

   private class StarfireBirdGoToNestGoal extends Goal {
      private int tryTicks = 0;

      public StarfireBirdGoToNestGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public boolean canUse() {
         return StarfireBird.this.getRandom().nextInt(reducedTickDelay(120)) == 0 && this.canUseGoal();
      }

      public boolean canContinueToUse() {
         return this.canUseGoal();
      }

      private boolean canUseGoal() {
         return StarfireBird.this.nestPos != null
            && StarfireBird.this.wantsToEnterNest()
            && this.tryTicks < 300
            && !StarfireBird.this.nestPos.closerToCenterThan(StarfireBird.this.position(), 2.0)
            && StarfireBird.this.isNestValid()
            && StarfireBird.this.level().getBlockEntity(StarfireBird.this.nestPos) instanceof StarfireBirdNestBlockEntity nest
            && !nest.isFullForAdults();
      }

      public void start() {
         this.tryTicks = 0;
      }

      public void stop() {
         this.tryTicks = 0;
         StarfireBird.this.navigation.stop();
      }

      public void tick() {
         BlockPos target = StarfireBird.this.nestPos;
         if (target != null) {
            StarfireBird.this.getLookControl()
               .setLookAt(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 10.0F, StarfireBird.this.getMaxHeadXRot());
            StarfireBird.this.getNavigation().moveTo(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 1.0);
         }

         this.tryTicks++;
      }
   }

   private class StarfireBirdLandGoal extends Goal {
      private int tryTicks = 0;
      private Vec3 target;

      public StarfireBirdLandGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public boolean canUse() {
         if (this.canUseGoal()) {
            this.target = LandRandomPos.getPos(StarfireBird.this, 15, 20);
            return this.validTarget();
         } else {
            return false;
         }
      }

      public boolean canContinueToUse() {
         return this.canUseGoal() && this.validTarget();
      }

      private boolean canUseGoal() {
         return StarfireBird.this.flying && StarfireBird.this.flyingTicks >= 300 && this.tryTicks < 300;
      }

      private boolean validTarget() {
         return this.target != null
            && StarfireBird.this.level().getBlockState(BlockPos.containing(this.target)).isAir()
            && StarfireBird.this.level()
               .getBlockState(BlockPos.containing(this.target).below())
               .entityCanStandOnFace(StarfireBird.this.level(), BlockPos.containing(this.target).below(), StarfireBird.this, Direction.UP)
            && StarfireBird.this.getPathfindingMalus(WalkNodeEvaluator.getPathTypeStatic(StarfireBird.this, BlockPos.containing(this.target))) == 0.0F;
      }

      public boolean isInterruptable() {
         return false;
      }

      public void start() {
         this.tryTicks = 0;
      }

      public void stop() {
         this.tryTicks = 0;
         StarfireBird.this.navigation.stop();
      }

      public void tick() {
         if (this.target != null) {
            StarfireBird.this.getLookControl().setLookAt(this.target.x, this.target.y, this.target.z, 10.0F, StarfireBird.this.getMaxHeadXRot());
            StarfireBird.this.getNavigation().moveTo(this.target.x, this.target.y, this.target.z, 1.0);
            if (StarfireBird.this.onGround()
               || BlockPos.containing(StarfireBird.this.position()).equals(BlockPos.containing(this.target))
               || BlockPos.containing(StarfireBird.this.position()).below().equals(BlockPos.containing(this.target))) {
               StarfireBird.this.switchMoveType(false);
               StarfireBird.this.navigation.stop();
               this.target = null;
            }
         }

         this.tryTicks++;
      }
   }

   private class StarfireBirdTakeOffGoal extends Goal {
      public boolean canUse() {
         if (!StarfireBird.this.flying && !StarfireBird.this.isBaby()) {
            List<LivingEntity> entities = StarfireBird.this.level()
               .getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), StarfireBird.this, StarfireBird.this.getBoundingBox().inflate(5.0));
            return StarfireBird.this.walkingTicks > 300
               || StarfireBird.this.getLastHurtByMob() != null
               || StarfireBird.this.wantsToEnterNest()
                  && StarfireBird.this.isNestValid()
                  && StarfireBird.this.level().getBlockEntity(StarfireBird.this.nestPos) instanceof StarfireBirdNestBlockEntity nest
                  && !nest.isFullForAdults()
               || entities.stream()
                  .anyMatch(e -> e.getType().is(ESTags.EntityTypes.STARFIRE_BIRD_AFRAID_OF) && !StarfireBird.this.trustedPlayers.contains(e.getUUID()));
         } else {
            return false;
         }
      }

      public boolean canContinueToUse() {
         return false;
      }

      public boolean isInterruptable() {
         return false;
      }

      public void start() {
         StarfireBird.this.switchMoveType(true);
         Vec3 pos = StarfireBird.this.position();
         StarfireBird.this.getNavigation().moveTo(pos.x, pos.y + 3.0, pos.z, 1.0);
      }
   }
}
